package com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie;

import com.sipgate.udpproxy.udp.proxy.processors.Message;
import com.sipgate.udpproxy.udp.proxy.processors.Payload;
import com.sipgate.udpproxy.udp.proxy.processors.gtpv2.ie.decoder.BitHelper;

public class Cause extends GenericInformationElement {
	private final CauseType causeType;
	private final boolean isCauseSource;
	private final boolean isBearerContextIeError;
	private final boolean isPdnConnectionIeError;

	public Cause(final Payload payload, final Message parent, final int windowOffset, final int windowSize) {
		super(payload, parent, windowOffset, windowSize);
		this.causeType = CauseType.fromValue(BitHelper.toInt(getPayloadByte(IE_HEADER_SIZE)));
		this.isCauseSource = BitHelper.isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 1);
		this.isBearerContextIeError = BitHelper.isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 2);
		this.isPdnConnectionIeError = BitHelper.isBitSet(getPayloadByte(IE_HEADER_SIZE + 1), 3);
	}

	public CauseType getCauseType() {
		return causeType;
	}

	public boolean isCauseSource() {
		return isCauseSource;
	}

	public boolean isBearerContextIeError() {
		return this.isBearerContextIeError;
	}

	public boolean isPdnConnectionIeError() {
		return this.isPdnConnectionIeError;
	}

	@Override
	public String toString() {
		return "Cause{" +
				"causeType=" + getCauseType() +
				", isCauseSource=" + isCauseSource() +
				", isBearerContextIeError=" + isBearerContextIeError() +
				", isPdnConnectionIeError=" + isPdnConnectionIeError() +
				'}';
	}

	public enum CauseType {
		RESERVED_0(0),
		RESERVED_1(1),
		LOCAL_DETACH(2),
		COMPLETE_DETACH(3),
		RAT_CHANGED_FROM_3GPP_TO_NON_3GPP(4),
		ISR_DEACTIVATION(5),
		ERROR_INDICATION_RECEIVED_FROM_RNC_ENB_SGSN_OR_MME(6),
		IMSI_DETACH_ONLY(7),
		REACTIVATION_REQUESTED(8),
		PDN_RECONNECTION_TO_THIS_APN_DISALLOWED(9),
		ACCESS_CHANGED_FROM_NON_3GPP_TO_3GPP(10),
		PDN_CONNECTION_INACTIVITY_TIMER_EXPIRED(11),
		PGW_NOT_RESPONDING(12),
		NETWORK_FAILURE(13),
		QOS_PARAMETERS_REJECTED(14),
		EPS_TO_5GS_MOBILITY(15),
		REQUEST_ACCEPTED(16),
		REQUEST_ACCEPTED_PARTIALLY(17),
		NEW_PDN_TYPE_NETWORK_PREFERENCE(18),
		NEW_PDN_TYPE_SINGLE_ADDRESS_BEARER_ONLY(19),
		// 23 - 63 spare
		CONTEXT_NOT_FOUND(64),
		INVALID_MESSAGE_FORMAT(65),
		VERSION_NOT_SUPPORTED_BY_NEXT_PEER(66),
		INVALID_LENGTH(67),
		SERVICE_NOT_SUPPORTED(68),
		MANDATORY_IE_INCORRECT(69),
		MANDATORY_IE_MISSING(70),
		// 71: Shall not be used
		SYSTEM_FAILURE(72),
		NO_RESOURCES_AVAILABLE(73),
		SEMANTIC_ERROR_IN_THE_TFT_OPERATION(74),
		SYNTACTIC_ERROR_IN_THE_TFT_OPERATION(75),
		SEMANTIC_ERRORS_IN_PACKET_FILTER(76),
		SYNTACTIC_ERRORS_IN_PACKET_FILTER(77),
		MISSING_OR_UNKNOWN_APN(78),
		// 79: Shall not be used
		GRE_KEY_NOT_FOUND(80),
		RELOCATION_FAILURE(81),
		DENIED_IN_RAT(82),
		PREFERRED_PDN_TYPE_NOT_SUPPORTED(83),
		ALL_DYNAMIC_ADDRESSES_ARE_OCCUPIED(84),
		UE_CONTEXT_WITHOUT_TFT_ALREADY_ACTIVATED(85),
		PROTOCOL_TYPE_NOT_SUPPORTED(86),
		UE_NOT_RESPONDING(87),
		UE_REFUSES(88),
		SERVICE_DENIED(89),
		UNABLE_TO_PAGE_UE(90),
		NO_MEMORY_AVAILABLE(91),
		USER_AUTHENTICATION_FAILED(92),
		APN_ACCESS_DENIED_NO_SUBSCRIPTION(93),
		REQUEST_REJECTED(94),
		P_TMSI_SIGNATURE_MISMATCH(95),
		IMSI_IMEI_NOT_KNOWN(96),
		SEMANCTIC_ERROR_IN_THE_TAD_OPERATION(97),
		SYNTACTIC_ERROR_IN_THE_TAD_OPERATION(98),
		// 99: Shall not be used
		REMOTE_PEER_NOT_RESPONDING(100),
		COLLISION_WITH_NETWORK_INITIATED_REQUEST(101),
		UNABLE_TO_PAGE_UE_DUE_TO_SUSPENSION(102),
		CONDITIONAL_IE_MISSING(103),
		APN_RESTRICTION_TYPE_INCOMPATIBLE_WITH_CURRENTLY_ACTIVE_PDN_CONNECTION(104),
		INVALID_OVERALL_LENGTH_OF_THE_TRIGGERED_RESPONSE_MESSAGE(105),
		DATA_FORWARDING_NOT_SUPPORTED(106),
		INVALID_REPLY_FROM_REMOTE_PEER(107),
		FALLBACK_TO_GTPV1(108),
		INVALID_PEER(109),
		TEMPORARILY_REJECTED_DUE_TO_HO_PROCEDURE(110),
		MODIFICATIONS_NOT_LIMITED_TO_S1_U_BEARERS(111),
		REQUEST_REJECTED_FOR_PMIPV6_REASON(112),
		APN_CONGESTION(113),
		BEARER_HANDLING_NOT_SUPPORTED(114),
		UE_ALREADY_RE_ATTACHED(115),
		MULTIPLE_PDN_CONNECTION_FOR_A_GIVEN_APN_NOT_ALLOWED(116),
		TARGET_ACCESS_RESTRICTED_FOR_THE_SUBSCRIBER(117),
		// 118: Shall not be used
		MME_SGSN_REFUSES_DUE_TO_VPLMN_POLICY(119),
		GTP_C_ENTITY_CONGESTION(120),
		LATE_OVERLAPPING_REQUEST(121),
		TIMED_OUT_REQUEST(122),
		UE_IS_TEMPORARILY_NOT_REACHABLE_DUE_TO_POWER_SAVING(123),
		RELOCATION_FAILURE_DUE_TO_NAS_MESSAGE_REDIRECTION(124),
		UE_NOT_AUTHORIZED_BY_OCS_OR_EXTERNAL_AAA_SERVER(125),
		MULTIPLE_ACCESS_TO_A_PDN_CONNECTION_NOT_ALLOWED(126),
		REQUEST_REJECTED_DUE_TO_UE_CAPABILITY(127),
		S1_U_PATH_FAILURE(128),
		NOT_ALLOWED_5GC(129),
		PGW_MISMATCH_WITH_NETWORK_SLICE_SUBSCRIBED_BY_THE_UE(130),
		REJECTION_DUE_TO_PAGING_RESTRICTION(131),
		// 132 - 255: Spare
		;


		private final int value;

		CauseType(final int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static CauseType fromValue(final int value) {
			for (final var causeType : values()) {
				if (causeType.getValue() == value) {
					return causeType;
				}
			}
			return null;
		}
	}

}