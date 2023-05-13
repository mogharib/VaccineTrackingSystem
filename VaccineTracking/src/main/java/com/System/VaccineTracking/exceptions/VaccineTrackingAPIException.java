package com.System.VaccineTracking.exceptions;

import java.util.List;
import lombok.Getter;

@Getter
public class VaccineTrackingAPIException extends RuntimeException {

    private ErrorMessageCode errorMessageCode;
    private Object[] params;

    private List<AdditionalMessage> additionalMessages;

    public VaccineTrackingAPIException(ErrorMessageCode errorMessageCode) {
        this.errorMessageCode = errorMessageCode;
    }

    public VaccineTrackingAPIException(ErrorMessageCode errorMessageCode, Object[] params) {
        this(errorMessageCode);
        this.params = params;
    }

    public VaccineTrackingAPIException(ErrorMessageCode errorMessageCode,
        List<AdditionalMessage> additionalMessages) {
        this(errorMessageCode);
        this.additionalMessages = additionalMessages;
    }
}
