package com.sipgate.udpproxy.udp.proxy.processors;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a payload of a UDP packet. The payload byte array is the same raw memory for each object instance.
 * The windowStartIndex and windowEndIndex are used per object instance to define the window of the payload byte array
 * that is visible to the object instance.
 * <p>
 * It is possible for windows to overlap but only if the overlapping object is a child of the overlapped object.
 * For instance, the bearer context IE contains multiple other IEs inside it and the window of the bearer context IE
 * overlaps with the windows of the IEs inside it.
 * <p>
 * It is possible to insert raw bytes into the payload byte array. This will shift the windowEndIndex of the object
 * that put the bytes there as necessary and move the windowStartIndex and windowEndIndex of all other objects that
 * come after the object that put the bytes there.
 */
public class Payload {

	private final Lock lock = new ReentrantLock();
	private final Map<Message, Window> windowIndex = new IdentityHashMap<>();
	private final Message rootMessage;
	private byte[] payloadBytes;

	/**
	 * Creates a new instance of this payload and initializes the rootMessage.
	 *
	 * @param payloadBytes The raw payload bytes
	 * @param rootMessage  The root message of this payload
	 */
	public Payload(final byte[] payloadBytes, final Message rootMessage) {
		this.payloadBytes = payloadBytes;
		this.rootMessage = rootMessage;
		windowIndex.put(rootMessage, new Window(0, payloadBytes.length));
	}

	/**
	 * Returns a single byte from the payload byte array through the window of the given message.
	 *
	 * @param index   The index of the byte to return
	 * @param message The message that defines the window
	 * @return The byte at the given index
	 */
	public byte getPayloadByte(final int index, final Message message) {
		lock.lock();
		try {
			assertElementIsKnown(message);
			final var window = windowIndex.get(message);
			assertValidIndex(index, window);

			return payloadBytes[window.start + index];
		} finally {
			lock.unlock();
		}

	}

	/**
	 * Sets a single byte in the payload byte array through the window of the given message.
	 *
	 * @param index   The index of the byte to set
	 * @param value   The value to set the byte to
	 * @param message The message that defines the window
	 */
	public void setPayloadByte(final int index, final byte value, final Message message) {
		lock.lock();
		try {
			assertElementIsKnown(message);
			final var window = windowIndex.get(message);
			assertValidIndex(index, window);

			payloadBytes[window.start + index] = value;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Replaces the payload byte array with the given bytes and updates the windows of all accessors accordingly.
	 *
	 * @param bytes   The new payload bytes
	 * @param message The message that defines the window
	 */
	public void setPayloadBytes(final byte[] bytes, final Message message) {
		lock.lock();
		try {
			assertElementIsKnown(message);
			final var window = windowIndex.get(message);

			// Simple case: Payload lenght has not changed
			if (bytes.length == window.length()) {
				System.arraycopy(bytes, 0, payloadBytes, window.start, bytes.length);
				lock.unlock();
				return;
			}

			// Complex case: Payload length has changed
			final var delta = window.length() - bytes.length;
			final byte[] newPayload = new byte[payloadBytes.length - delta];

			System.arraycopy(payloadBytes, 0, newPayload, 0, window.start);
			System.arraycopy(bytes, 0, newPayload, window.start, bytes.length);
			System.arraycopy(payloadBytes, window.end, newPayload, window.start + bytes.length, payloadBytes.length - window.end);

			// Update window indices
			final var windows = windowIndex.entrySet().stream()
					.filter(e -> e.getValue().start > window.start)
					.toList();

			for (final var entry : windows) {
				final var w = entry.getValue();
				windowIndex.put(entry.getKey(), new Window(w.start - delta, w.end - delta));
			}

			payloadBytes = newPayload;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns the length of the payload byte array through the window of the given message.
	 *
	 * @param message The message that defines the window
	 * @return The length of the payload byte array
	 */
	public int getPayloadLength(final Message message) {
		lock.lock();
		try {
			assertElementIsKnown(message);
			return windowIndex.get(message).length();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns a copy of the payload bytes of the given message.
	 *
	 * @param message The message to get the payload bytes from
	 * @return A copy of the payload bytes
	 */
	public byte[] finalizeForNetwork(final int offset, final Message message) {
		lock.lock();
		try {
			if (offset < 0) {
				throw new IllegalArgumentException("Offset must be greater than or equal to 0");
			}
			assertElementIsKnown(message);
			final var window = windowIndex.get(message);

			if (offset >= window.length()) {
				throw new IllegalArgumentException("Offset must be less than the window length");
			}

			final var copyOfBytes = new byte[window.length() - offset];
			System.arraycopy(payloadBytes, window.start + offset, copyOfBytes, 0, window.length() - offset);
			return copyOfBytes;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Registers a new message with this payload. The window of the message must be within the window of its parent
	 * message.
	 *
	 * @param message              The message to register
	 * @param parent               The parent message of the message to register
	 * @param startIndexFromParent The start index of the window relative to the start of the parent message window
	 * @param endIndexFromParent   The end index of the window relative to the start of the parent message window
	 */
	public void addMessage(final Message message, final Message parent, final int startIndexFromParent, final int endIndexFromParent) {
		lock.lock();
		try {
			if (windowIndex.containsKey(message)) {
				throw new IllegalArgumentException("message is already registered with this payload");
			}

			if (parent == null) {
				throw new IllegalArgumentException("parent is null");
			}

			if (windowIndex.containsKey(parent)) {
				throw new IllegalArgumentException("parent is not registered with this payload");
			}

			if (startIndexFromParent < 0 || startIndexFromParent >= payloadBytes.length) {
				throw new ArrayIndexOutOfBoundsException("Window start index is out of bounds");
			}

			if (endIndexFromParent < 0 || endIndexFromParent > payloadBytes.length) {
				throw new ArrayIndexOutOfBoundsException("Window end index is out of bounds");
			}

			final var parentWindow = windowIndex.get(parent);
			final var offset = parentWindow.start;
			final var startIndex = offset + startIndexFromParent;
			final var endIndex = offset + endIndexFromParent;

			if (startIndex < parentWindow.start || endIndex > parentWindow.end) {
				throw new IllegalArgumentException("Window is outside of parent window");
			}

			final var window = new Window(startIndex, endIndex);
			windowIndex.put(message, window);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Removes an element window from this payload. Nulls all bytes in the window.
	 *
	 * @param message The message to remove
	 */
	public void removeMessage(final Message message) {
		lock.lock();
		try {
			assertElementIsKnown(message);
			if (message == rootMessage) {
				throw new IllegalArgumentException("Cannot remove root element");
			}
			final var window = windowIndex.get(message);
			for (int i = window.start; i < window.end; i++) {
				payloadBytes[i] = 0;
			}
			windowIndex.remove(message);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Instructs the payload root message to pack its bytes. This will cause all child messages to pack their
	 * bytes as well. A copy of the payload byte array is returned that can be sent over the network.
	 *
	 * @return A copy of the payload byte array
	 */
	public byte[] finalizeForNetwork() {
		lock.lock();
		try {
			rootMessage.packBytes();
			final var copyOfBytes = new byte[payloadBytes.length];
			System.arraycopy(payloadBytes, 0, copyOfBytes, 0, payloadBytes.length);
			return copyOfBytes;
		} finally {
			lock.unlock();
		}
	}

	private void assertElementIsKnown(final Message message) {
		if (!windowIndex.containsKey(message)) {
			throw new IllegalArgumentException("message is not registered with this payload");
		}
	}

	private void assertValidIndex(final int index, final Window window) {
		if (index < 0 || index >= window.length()) {
			throw new ArrayIndexOutOfBoundsException("Window index is out of bounds");
		}
	}


	private record Window(int start, int end) {
		public int length() {
			return end - start;
		}
	}

}
