package com.sipgate.udpproxy.protocol.gtpv2.ie;

import com.sipgate.udpproxy.protocol.gtpv2.ie.decoder.BitHelper;
import com.sipgate.udpproxy.protocol.gtpv2.ie.decoder.MncMcc;

import java.util.Optional;

/**
 * User Location Information
 * <p>
 *
 * <a href="https://www.etsi.org/deliver/etsi_ts/129200_129299/129274/17.08.00_60/ts_129274v170800p.pdf">
 * See ETSI TS 129 274 V17.8.0 (2023-04) - Section 8.21 (Page 299)
 * </a>
 *
 * @author Lennart Rosam <rosam@sipgate.de>
 */
public class UserLocationInformation extends InformationElement {

	private final UliFlags flags;

	// Ordered the way it shoud be parsed according to the flags if more than one identity is present at the same time
	private Optional<Cgi> cgi = Optional.empty();
	private Optional<Sai> sai = Optional.empty();
	private Optional<Rai> rai = Optional.empty();
	private Optional<Tai> tai = Optional.empty();
	private Optional<Ecgi> ecgi = Optional.empty();
	private Optional<Lai> lai = Optional.empty();
	private Optional<MacroEnbId> macroEnbId = Optional.empty();
	private Optional<ExtendedMacroEnbId> extendedMacroEnbId = Optional.empty();
	private byte[] payload;

	UserLocationInformation(final byte type, final byte spare, final byte instance, final byte[] payload) {
		super(type, spare, instance, payload);
		flags = new UliFlags(payload[0]);
		this.payload = payload;

		if (flags.isExtendedMacroEnbIdPresent() && flags.isMacroEnbIdPresent()) {
			throw new IllegalArgumentException("MacroEnbId and ExtendedMacroEnbId are mutually exclusive!");
		}

		int currentOffset = 1;
		final UliFlags flagsToParse = new UliFlags(payload[0]);
		do {
			if (flagsToParse.isCgi()) {
				final byte[] cgiBytes = new byte[7];
				System.arraycopy(payload, currentOffset, cgiBytes, 0, cgiBytes.length);
				cgi = Optional.of(new Cgi(cgiBytes));
				currentOffset += cgiBytes.length;
				flagsToParse.setCgi(false);
				continue;
			}

			if (flagsToParse.isSai()) {
				final byte[] saiBytes = new byte[7];
				System.arraycopy(payload, currentOffset, saiBytes, 0, saiBytes.length);
				sai = Optional.of(new Sai(saiBytes));
				currentOffset += saiBytes.length;
				flagsToParse.setSai(false);
				continue;
			}

			if (flagsToParse.isRai()) {
				final byte[] raiBytes = new byte[7];
				System.arraycopy(payload, currentOffset, raiBytes, 0, raiBytes.length);
				rai = Optional.of(new Rai(raiBytes));
				currentOffset += raiBytes.length;
				flagsToParse.setRai(false);
				continue;
			}

			if (flagsToParse.isTai()) {
				final byte[] taiBytes = new byte[5];
				System.arraycopy(payload, currentOffset, taiBytes, 0, taiBytes.length);
				tai = Optional.of(new Tai(taiBytes));
				currentOffset += taiBytes.length;
				flagsToParse.setTai(false);
				continue;
			}

			if (flagsToParse.isEcgi()) {
				final byte[] ecgiBytes = new byte[7];
				System.arraycopy(payload, currentOffset, ecgiBytes, 0, ecgiBytes.length);
				ecgi = Optional.of(new Ecgi(ecgiBytes));
				currentOffset += ecgiBytes.length;
				flagsToParse.setEcgi(false);
				continue;
			}

			if (flagsToParse.isLai()) {
				final byte[] laiBytes = new byte[5];
				System.arraycopy(payload, currentOffset, laiBytes, 0, laiBytes.length);
				lai = Optional.of(new Lai(laiBytes));
				currentOffset += laiBytes.length;
				flagsToParse.setLai(false);
				continue;
			}

			if (flagsToParse.isMacroEnbIdPresent()) {
				final byte[] macroEnbIdBytes = new byte[6];
				System.arraycopy(payload, currentOffset, macroEnbIdBytes, 0, macroEnbIdBytes.length);
				macroEnbId = Optional.of(new MacroEnbId(macroEnbIdBytes));
				currentOffset += macroEnbIdBytes.length;
				flagsToParse.setMacroEnbIdPresent(false);
				continue;
			}

			if (flagsToParse.isExtendedMacroEnbIdPresent()) {
				final byte[] extendedMacroEnbIdBytes = new byte[6];
				System.arraycopy(payload, currentOffset, extendedMacroEnbIdBytes, 0, extendedMacroEnbIdBytes.length);
				extendedMacroEnbId = Optional.of(new ExtendedMacroEnbId(extendedMacroEnbIdBytes));
				currentOffset += extendedMacroEnbIdBytes.length;
				flagsToParse.setExtendedMacroEnbId(false);
			}

		} while (currentOffset < payload.length);

	}

	public UliFlags getFlags() {
		return flags;
	}

	public Optional<Cgi> getCgi() {
		return cgi;
	}

	public Optional<Sai> getSai() {
		return sai;
	}

	public Optional<Rai> getRai() {
		return rai;
	}

	public Optional<Tai> getTai() {
		return tai;
	}

	public Optional<Ecgi> getEcgi() {
		return ecgi;
	}

	public Optional<Lai> getLai() {
		return lai;
	}

	public Optional<MacroEnbId> getMacroEnbId() {
		return macroEnbId;
	}

	public Optional<ExtendedMacroEnbId> getExtendedMacroEnbId() {
		return extendedMacroEnbId;
	}

	public byte[] getPayload() {
		return payload;
	}

	@Override
	public String toString() {
		return "UserLocationInformation{" +
				"flags=" + flags +
				", cgi=" + cgi +
				", sai=" + sai +
				", rai=" + rai +
				", tai=" + tai +
				", ecgi=" + ecgi +
				", lai=" + lai +
				", macroEnbId=" + macroEnbId +
				", extendedMacroEnbId=" + extendedMacroEnbId +
				'}';
	}

	/**
	 * User Location Information Flags
	 */
	public class UliFlags {

		private byte flags;

		UliFlags(final byte flags) {
			this.flags = flags;
		}


		public boolean isExtendedMacroEnbIdPresent() {
			return BitHelper.isBitSet(flags, 8);
		}

		public UliFlags setExtendedMacroEnbId(final boolean extendedMacroEnbId) {
			flags = BitHelper.setBitTo(flags, 8, extendedMacroEnbId);
			return this;
		}

		public boolean isMacroEnbIdPresent() {
			return BitHelper.isBitSet(flags, 7);
		}

		public UliFlags setMacroEnbIdPresent(final boolean macroEnbId) {
			flags = BitHelper.setBitTo(flags, 7, macroEnbId);
			return this;
		}

		public boolean isLai() {
			return BitHelper.isBitSet(flags, 6);
		}

		public UliFlags setLai(final boolean lai) {
			flags = BitHelper.setBitTo(flags, 6, lai);
			return this;
		}

		public boolean isEcgi() {
			return BitHelper.isBitSet(flags, 5);
		}

		public UliFlags setEcgi(final boolean ecgi) {
			flags = BitHelper.setBitTo(flags, 5, ecgi);
			return this;
		}

		public boolean isTai() {
			return BitHelper.isBitSet(flags, 4);
		}

		public UliFlags setTai(final boolean tai) {
			flags = BitHelper.setBitTo(flags, 4, tai);
			return this;
		}

		public boolean isRai() {
			return BitHelper.isBitSet(flags, 3);
		}

		public UliFlags setRai(final boolean rai) {
			flags = BitHelper.setBitTo(flags, 3, rai);
			return this;
		}

		public boolean isSai() {
			return BitHelper.isBitSet(flags, 2);
		}

		public UliFlags setSai(final boolean sai) {
			flags = BitHelper.setBitTo(flags, 2, sai);
			return this;
		}

		public boolean isCgi() {
			return BitHelper.isBitSet(flags, 1);
		}

		public UliFlags setCgi(final boolean cgi) {
			flags = BitHelper.setBitTo(flags, 1, cgi);
			return this;
		}

		public byte getFlags() {
			return flags;
		}

		public UliFlags setFlags(final byte flags) {
			this.flags = flags;
			return this;
		}

		public String toString() {
			return "UliFlags{" +
					"isExtendedMacroEnbId=" + isExtendedMacroEnbIdPresent() +
					", isMacroEnbIdPresent=" + isMacroEnbIdPresent() +
					", isLai=" + isLai() +
					", isEcgi=" + isEcgi() +
					", isTai=" + isTai() +
					", isRai=" + isRai() +
					", isSai=" + isSai() +
					", isCgi=" + isCgi() +
					'}';
		}
	}

	/**
	 * Base class for all location information. Those all contain MNC and MCC.
	 */
	public abstract static class Location {
		private byte[] bytes;

		Location(final byte[] bytes) {
			this.bytes = bytes;
		}

		/**
		 * Returns the raw bytes of the location information.
		 * @return The raw bytes
		 */
		public byte[] getBytes() {
			return bytes;
		}

		/**
		 * Sets the raw bytes of the location information.
		 * @param bytes The raw bytes
		 * @return The location information itself
		 */
		public Location setBytes(final byte[] bytes) {
			this.bytes = bytes;
			return this;
		}

		/**
		 * Returns the Mobile Country Code (MCC) of the location information.
		 * @return The MCC
		 */
		public int getMcc() {
			return MncMcc.decodeMcc(bytes[0], bytes[1]);
		}

		/**
		 * Returns the Mobile Network Code (MNC) of the location information.
		 * @return The MNC
		 */
		public int getMnc() {
			return MncMcc.decodeMnc(bytes[1], bytes[2]);
		}
	}

	/**
	 * Cell Global Identifier (CGI) location information. Only zero or one time present.
	 */
	public static class Cgi extends Lai {
		Cgi(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Cell Global Identifier (CGI) of the location information.
		 * @return The CGI
		 */
		public int getCgi() {
			return BitHelper.int16ToInt32(getBytes()[5], getBytes()[6]);
		}

		public String toString() {
			return "Cgi{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", lac=" + getLac() +
					", cgi=" + getCgi() +
					'}';
		}
	}


	/**
	 * Service Area Identifier (SAI) location information. Only zero or one time present.
	 */
	public static class Sai extends Lai {
		Sai(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Service Area Identifier (SAI) of the location information.
		 * @return The SAI
		 */
		public int getSai() {
			return BitHelper.int16ToInt32(getBytes()[5], getBytes()[6]);
		}

		public String toString() {
			return "Sai{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", lac=" + getLac() +
					", sai=" + getSai() +
					'}';
		}
	}

	/**
	 * Routing Area Identifier (RAI) location information. Only zero or one time present.
	 */
	public static class Rai extends Lai {

		Rai(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Routing Area Identifier (RAI) of the location information.
		 * @return The RAI
		 */
		public int getRai() {
			return BitHelper.int16ToInt32(getBytes()[5], getBytes()[6]);
		}
		public String toString() {
			return "Rai{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", lac=" + getLac() +
					", rai=" + getRai() +
					'}';
		}

	}

	/**
	 * Tracking Area Identifier (TAI) location information. Only zero or one time present.
	 */
	public static class Tai extends Location {
		Tai(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Tracking Area Identifier (TAI) of the location information.
		 * @return The TAI
		 */
		public int getTai() {
			return BitHelper.int16ToInt32(getBytes()[3], getBytes()[4]);
		}

		public String toString() {
			return "Tai{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", tai=" + getTai() +
					'}';
		}
	}

	/**
	 * E-UTRAN Cell Global Identifier (ECGI) location information. Only zero or one time present.
	 */
	public static class Ecgi extends Location {
		Ecgi(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the E-UTRAN Cell Identifier (ECI) of the location information.
		 * @return The ECI
		 */
		public int getEci() {
			final byte lowerNibble = BitHelper.lowerNibble(getBytes()[3]);
			return BitHelper.toInt32(lowerNibble, getBytes()[4], getBytes()[5], getBytes()[6]);
		}

		public String toString() {
			return "Ecgi{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", eci=" + getEci() +
					'}';
		}
	}

	/**
	 * Location Area Identifier (LAI) location information. Only zero or one time present.
	 */
	public static class Lai extends Location {
		Lai(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Location Area Cdentifier (LAC) of the location information.
		 * @return The LAC
		 */
		public int getLac() {
			return BitHelper.int16ToInt32(getBytes()[3], getBytes()[4]);
		}

	}

	/**
	 * Macro eNodeB ID
	 */
	public static class MacroEnbId extends Location {

		MacroEnbId(final byte[] bytes) {
			super(bytes);
		}

		/**
		 * Returns the Macro eNodeB ID of the macro eNodeB ID.
		 * @return The Macro eNodeB ID
		 */
		public int getMacroEnbId() {
			// Upper nibble is marked as spare and currently unused
			final byte lowerNibble = BitHelper.lowerNibble(getBytes()[3]);
			return BitHelper.int24ToInt32(lowerNibble, getBytes()[4], getBytes()[5]);
		}

		public String toString() {
			return "MacroEnbId{" +
					"mcc=" + getMcc() +
					", mnc=" + getMnc() +
					", macroEnbId=" + getMacroEnbId() +
					'}';
		}
	}

	/**
	 * Extended Macro eNodeB ID
	 */
	public static class ExtendedMacroEnbId extends Location {

		ExtendedMacroEnbId(final byte[] bytes) {
			super(bytes);
		}



		public boolean isSMeNBFlagSet() {
			return BitHelper.isBitSet(getBytes()[3], 8);
		}

		public ExtendedMacroEnbId setSMeNBFlag(final boolean sMeNBFlag) {
			getBytes()[2] = BitHelper.setBitTo(getBytes()[3], 8, sMeNBFlag);
			return this;
		}

		/**
		 * Returns the Extended Macro eNodeB ID of the extended macro eNodeB ID.
		 * @return The Extended Macro eNodeB ID
		 */
		public int getMacroEnbId() {
			// if the sMeNB flag is set, the ID is 21 bits long, starting at bit 5 of the 4th byte and extends
			// over the 5th and 6th byte
			if (isSMeNBFlagSet()) {
				final int mask = 0b00011111;
				final byte partialByteThree = (byte) ((getBytes()[3] & mask) << 3);
				return BitHelper.int24ToInt32(partialByteThree, getBytes()[4], getBytes()[5]);
			}

			// if the sNeNB flag is not set, the ID is 18 bits long, starting at bit 2 of the 4th byte and extend
			// over the 5th and 6th byte
			final int mask = 0b00000011;
			final byte partialByteThree = (byte) ((getBytes()[3] & mask) << 5);
			return BitHelper.int24ToInt32(partialByteThree, getBytes()[4], getBytes()[5]);
		}
	}
}
