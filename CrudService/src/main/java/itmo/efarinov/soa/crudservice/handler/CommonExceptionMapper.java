package itmo.efarinov.soa.crudservice.handler;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import itmo.efarinov.soa.crudservice.error.BadRequestException;
import itmo.efarinov.soa.crudservice.filter.error.BadFilterException;
import itmo.efarinov.soa.crudservice.filter.error.MaxPageNumberExceededException;
import itmo.efarinov.soa.crudservice.mapper.error.DtoMappingException;
import itmo.efarinov.soa.dto.ErrorDto;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Exception> {
    private final Map<Class<?>, Integer> handledErrors = new HashMap<>();

    public CommonExceptionMapper() {
        handledErrors.put(BadFilterException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoResultException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(MaxPageNumberExceededException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(BadRequestException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(DtoMappingException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoSuchFieldException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(ConstraintViolationException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(IllegalArgumentException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(InvalidFormatException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NotFoundException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(JsonEOFException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(UnrecognizedPropertyException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(JsonMappingException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(jakarta.ws.rs.BadRequestException.class, HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Exception exception) {
        Response.ResponseBuilder resp = Response.serverError().type(MediaType.APPLICATION_JSON);
        System.out.printf("GOT ERROR TO HANDLE %s%n", exception);
        if (handledErrors.containsKey(exception.getClass())) {
            resp = resp.status(handledErrors.get(exception.getClass()));
        } else if (exception.getClass().equals(PersistenceException.class)) {
            PersistenceException ex = (PersistenceException) exception;
            if (ex.getMessage().contains("ConstraintViolationException")) {
                resp = resp.status(HttpServletResponse.SC_BAD_REQUEST);
                if (ex.getCause().getCause().getMessage().contains(" is still referenced"))
                    return resp.entity(ErrorDto.builder()
                                    .error(exception.getClass().getSimpleName())
                                    .message(ex.getCause().getCause().getMessage() + "You should consider removing dependent objects first.")
                                    .build())
                            .build();
            }
        }

        if (exception.getClass().equals(ConstraintViolationException.class)) {
            ConstraintViolationException ex = (ConstraintViolationException) exception;

            StringBuilder commonErrorMessage = new StringBuilder();
            for (ConstraintViolation<?> constraint : ex.getConstraintViolations()) {
                commonErrorMessage.append(constraint.getPropertyPath()).append(" ").append(constraint.getMessage()).append("\n");
            }

            return resp.entity(ErrorDto.builder()
                            .error(exception.getClass().getSimpleName())
                            .message(commonErrorMessage.toString())
                            .build())
                    .build();
        }

        resp.entity(ErrorDto.builder().error(exception.getClass().getSimpleName()).message(exception.getMessage()).build());

        return resp.build();
    }
}
