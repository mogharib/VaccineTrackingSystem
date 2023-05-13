package com.System.VaccineTracking.exceptions;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private String errorMessageKey = "error.general.message";
    private String errorMessage = "";

    @ExceptionHandler(value = VaccineTrackingAPIException.class)
    public ResponseEntity<Object> handleBusinessException(VaccineTrackingAPIException businessEx,
        WebRequest request) {
        ErrorMessageDto errorMessage = buildResponse(businessEx, request);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(),
            HttpStatus.valueOf(businessEx.getErrorMessageCode().getHttpStatus()));
    }

    private ErrorMessageDto buildResponse(VaccineTrackingAPIException businessEx, WebRequest request) {
        String customMessage = messageSource.getMessage(
            businessEx.getErrorMessageCode().getMessageKey(), businessEx.getParams(),
            Locale.ENGLISH);
        return ErrorMessageDto.builder()
            .errorCode(businessEx.getErrorMessageCode().getErrorCode())
            .status(businessEx.getErrorMessageCode().getHttpStatus())
            .messageKey(businessEx.getErrorMessageCode().getMessageKey())
            .message(customMessage)
            .serviceUri(request.getDescription(false))
            .exception(businessEx.getClass().getSimpleName())
            .additionalMessages(businessEx.getAdditionalMessages())
            .build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAny(Exception ex, WebRequest request) {
        errorMessage = messageSource.getMessage(errorMessageKey, null, Locale.ENGLISH);
        ErrorMessageDto errorMessageDTO = ErrorMessageDto.builder()
            .errorCode(500)
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .messageKey(errorMessageKey)
            .message(errorMessage)
            .serviceUri(request.getDescription(false))
            .exception(ex.getClass().getSimpleName())
            .build();
        log.error("Error: {} Exception: {}. Reference Number: {}. Response code: {} ", errorMessage,
            ex.getClass().getSimpleName(), errorMessageDTO.getErrorReference(),
            HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorMessageDTO, new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private String getExceptionName(Exception ex) {
        if (ex.getLocalizedMessage() != null) {
            return ex.getLocalizedMessage();
        } else if (ex.getMessage() != null) {
            return ex.getMessage();
        } else if (ex.getCause() != null && ex.getCause().getLocalizedMessage() != null) {
            return ex.getCause().getLocalizedMessage();
        } else if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            return ex.getCause().getMessage();
        } else {
            return "Unknown Error!";
        }
    }
}
