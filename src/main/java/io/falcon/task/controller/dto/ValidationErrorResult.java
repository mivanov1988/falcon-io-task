package io.falcon.task.controller.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents validation error
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
public class ValidationErrorResult extends ErrorResult {
    private List<FieldError> errors = new ArrayList<>();

    public static class FieldError {
        private String field;
        private Object value;
        private String code;
        private String message;

        public FieldError(String field, Object value, String code, String message) {
            this.field = field;
            this.value = value;
            this.code = code;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public void addError(String field, Object value, String code, String message) {
        FieldError error = new FieldError(field, value, code, message);
        this.errors.add(error);
    }
}
