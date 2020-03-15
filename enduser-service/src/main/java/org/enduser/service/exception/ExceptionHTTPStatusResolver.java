package org.enduser.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHTTPStatusResolver implements ExceptionMapper {

    public Response toResponse(EndUserException exception) {
        Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof EndUserException) {
            // return
            // Response.status(exception.getHttpErrorCode()).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
        return null;
    }

    public Response toResponse(Throwable exception) {
        // TODO Auto-generated method stub
        return null;
    }

}
