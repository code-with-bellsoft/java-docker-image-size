package dev.cyberjar.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class QuestAlreadyAssignedExceptionMapper implements ExceptionMapper<QuestAlreadyAssignedException> {

    @Override
    public Response toResponse(QuestAlreadyAssignedException exception) {
        return Response
                .status(Response.Status.CONFLICT)
                .type(MediaType.TEXT_PLAIN)
                .entity(exception.getMessage())
                .build();
    }
}
