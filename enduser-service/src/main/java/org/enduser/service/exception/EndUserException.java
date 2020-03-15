package org.enduser.service.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

public class EndUserException extends WebApplicationException {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EndUserException.class);
    private static final int HTTP_ERROR_CODE = 450;

    // default message and HTTP Status code
    private String message;
    private int errorCode;

    public EndUserException() {
        this(HTTP_ERROR_CODE, "Error Happend.");
    }

    public EndUserException(Exception exception) {
        super(exception);
        logger.error("Application Exception: ", exception);
    }

    public EndUserException(String message) {
        this(HTTP_ERROR_CODE, message);
    }

    public EndUserException(int code, String message) {
       
        super(Response.status(code).entity(message).type(MediaType.APPLICATION_JSON).build());
        this.message = message;
        this.errorCode = code;
    }

    public String getMessage() {
        return message;
    }
    public int getErrorCode(){
        return errorCode;
    }
}
