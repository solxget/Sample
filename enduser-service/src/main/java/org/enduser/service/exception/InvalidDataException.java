package org.enduser.service.exception;

public class InvalidDataException extends EndUserException {

    private static final long serialVersionUID = 1L;
    private static final int HTTP_ERROR_CODE = 400;
    private String message;

    public InvalidDataException() {
        this("Invalid data request. Please try again with valid data.");
    }

    public InvalidDataException(String message) {
        this(message, HTTP_ERROR_CODE);
    }

    public InvalidDataException(String message, int code) {

        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
