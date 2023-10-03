package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public abstract class InformationElement extends Message {

	protected static final int IE_HEADER_SIZE = 4;

	private final int type;
	private short length;

	protected InformationElement(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		type = getPayloadByte(0);
		length = (short) BitHelper.int16ToInt32(getPayloadByte(1), getPayloadByte(2));
		if (getPayloadLength() != length + IE_HEADER_SIZE) {
			throw new IllegalArgumentException("Length field of IE is invalid! Indicated length: " + length + ", actual length: " + (getPayloadLength() - IE_HEADER_SIZE));
		}
	}

	public int getType() {
		return type;
	}

	public short getLength() {
		return length;
	}

	public void setLength(final short length) {
		this.length = length;
	}

	protected byte[] packHeaderWithEmptyPayload(final int payloadLength) {
		final byte[] bytes = new byte[IE_HEADER_SIZE + payloadLength];
		bytes[0] = (byte) type;
		bytes[1] = (byte) ((payloadLength >> 8) & 0xff);
		bytes[2] = (byte) (payloadLength & 0xff);
		bytes[3] = getPayloadByte(3);

		return bytes;
	}

	public static InformationElement fromPayload(final int type, final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		return switch (type) {
			case 0x01 -> new Imsi(payload, parent, windowOffset, windowSize);
			case 0x02 -> new Cause(payload, parent, windowOffset, windowSize);
			case 0x03 -> new RestartCounter(payload, parent, windowOffset, windowSize);
			case 0x47 -> new AccessPointName(payload, parent, windowOffset, windowSize);
			case 0x48 -> new AggregateMaximumBitRate(payload, parent, windowOffset, windowSize);
			case 0x49 -> new EpsBearerId(payload, parent, windowOffset, windowSize);
			case 0x4b -> new MobileEquipmentIdentity(payload, parent, windowOffset, windowSize);
			case 0x4c -> new Msisdn(payload, parent, windowOffset, windowSize);
			case 0x4d -> new Indication(payload, parent, windowOffset, windowSize);
			case 0x4f -> new PdnAddressAllocation(payload, parent, windowOffset, windowSize);
			case 0x52 -> new RatType(payload, parent, windowOffset, windowSize);
			case 0x53 -> new ServingNetwork(payload, parent, windowOffset, windowSize);
			case 0x54 -> new EpsBearerLevelTrafficFlowTemplate(payload, parent, windowOffset, windowSize);
			case 0x56 -> new UserLocationInformation(payload, parent, windowOffset, windowSize);
			case 0x57 -> new FullyQualifiedTunnelEndpointIdentifier(payload, parent, windowOffset, windowSize);
			case 0x5d -> new BearerContext(payload, parent, windowOffset, windowSize);
			case 0x63 -> new PdnType(payload, parent, windowOffset, windowSize);
			case 0x7f -> new ApnRestriction(payload, parent, windowOffset, windowSize);
			case (byte) 0x80 -> new SelectionMode(payload, parent, windowOffset, windowSize);
			default -> new GenericInformationElement(payload, parent, windowOffset, windowSize);
		};
	}

}
