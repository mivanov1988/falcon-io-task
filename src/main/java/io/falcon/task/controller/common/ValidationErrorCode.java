package io.falcon.task.controller.common;

/**
 * Helper class for mapping of validation errors and messages
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
public enum ValidationErrorCode {
	UNEXPECTED_ERROR("UnexpectedError"),
	REQUIRED_FIELD("NotNull"),
	INVALID_FIELD_SIZE("Size");

	private String value;

	ValidationErrorCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static ValidationErrorCode findByValue(String val) {
		for (ValidationErrorCode error : ValidationErrorCode.values()) {
			if (error.getValue().equals(val)) {
				return error;
			}
		}
		return UNEXPECTED_ERROR;
	}
}
