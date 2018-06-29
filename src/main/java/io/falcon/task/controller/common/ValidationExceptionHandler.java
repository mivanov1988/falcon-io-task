package io.falcon.task.controller.common;

import io.falcon.task.controller.dto.ValidationErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

/**
 * That's the "Spring" way of doing it. Having a try/catch block in the filter
 * doesn't do the trick, as spring catches the exception before us.
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
@ControllerAdvice
public class ValidationExceptionHandler {
    private static final String ERROR_CODE = "VALIDATION_FAILED";
    private static final String ERROR_MESSAGE = "Validation error";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private MessageSource messageSource;

    public ValidationExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ValidationErrorResult handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        log.error(ERROR_MESSAGE, ex);
        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorResult processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorResult result = new ValidationErrorResult();
        result.setCode(ERROR_CODE);
        result.setMessage(ERROR_MESSAGE);
        result.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            String fieldName = fieldError.getField();
            Object fieldValue = fieldError.getRejectedValue();
            String code = getRestErrorCode(fieldError);
            String message = localizedErrorMessage;

            result.addError(fieldName, fieldValue, code, message);
        }

        return result;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, currentLocale);
    }

    private String getRestErrorCode(FieldError fieldError) {
        String codeName = fieldError.getCode();
        return ValidationErrorCode.findByValue(codeName).name();
    }
}