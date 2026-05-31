package org.openjfxroot.base;

public enum OEnum {
		LIVRAISON("LI"),
		RETRAIT_AUTO("AU"),
		RETIRE_MAG("MA");
		
		private final String code;
		
		OEnum(final String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}
		
		public static OEnum parse(final String code) {
			OEnum result = null;
			for (final OEnum type : values()) {
				if (type.code.equals(code)) {
					result = type;
				}
			}
			if (result == null) {
				throw new IllegalArgumentException(code);
			}
			return result;
		}
}
