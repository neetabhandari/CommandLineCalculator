/**
 * 
 */
package com.synopsys.enums;

/**
 * OperatorEnum defines the list of operations available in the expression language.
 * 
 * @author Neeta
 *
 */
public enum OperatorEnum {
	ADD("add"), SUB("sub"), MULT("mult"), DIV("div"), LET("let");

	private String value;

	OperatorEnum(final String value) {
		this.value = value;
	}

	/**
	 * Gets the text value for an Operator Enum.
	 * 
	 * @return text value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the OperatorEnum from the corresponding text value passed.
	 * @param value
	 * @return {@link OperatorEnum}
	 */
	public static OperatorEnum fromString(String value) {
		for (OperatorEnum b : OperatorEnum.values()) {
			if (b.value.equalsIgnoreCase(value)) {
				return b;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}