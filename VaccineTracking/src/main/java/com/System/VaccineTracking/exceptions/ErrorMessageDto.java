package com.System.VaccineTracking.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageDto {

    private int status;
    private int errorCode;
    @Builder.Default
    private Long errorReference = (long) (Math.floor(Math.random() * 900000L) + 100000L);
    private String message;
    private String messageKey;
    private String serviceUri;
    private String exception;
    private Long timestamp;
    private String dateTime;
    @Builder.Default
    private Date currentDate = new Date();

    private List<AdditionalMessage> additionalMessages;

    public Long getTimestamp() {
        timestamp = currentDate.getTime();
        return timestamp;
    }

    public String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        dateTime = simpleDateFormat.format(currentDate);
        return dateTime;
    }
}
