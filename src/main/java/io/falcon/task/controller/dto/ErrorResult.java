package io.falcon.task.controller.dto;

/**
 * This class represents base error
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
public class ErrorResult extends BaseDTO {
    private int status;
    private String code;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
