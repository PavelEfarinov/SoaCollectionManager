package itmo.efarinov.soa.crudservice.handler;

import itmo.efarinov.soa.crudservice.error.BadFilterException;
import itmo.efarinov.soa.crudservice.error.BadRequestException;
import itmo.efarinov.soa.crudservice.error.MaxPageNumberExceededException;
import itmo.efarinov.soa.dto.ErrorDto;
import lombok.SneakyThrows;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/error")
public class ErrorHandler extends HttpServlet {
    private final Map<Class<?>, Integer> handledErrors = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        handledErrors.put(BadFilterException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoResultException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(MaxPageNumberExceededException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(BadRequestException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoSuchFieldException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(ConstraintViolationException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(IllegalArgumentException.class, HttpServletResponse.SC_BAD_REQUEST);
    }

    @SneakyThrows
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) {
        processError(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) {
        processError(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp) {
        processError(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp) {
        processError(req, resp);
    }

    @SneakyThrows
    protected void processError(
            HttpServletRequest req,
            HttpServletResponse resp) {
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            Throwable exception = (Throwable) req.getAttribute("javax.servlet.error.exception");
            System.out.println(String.format("GOT ERROR TO HANDLE %s", exception));
            if (handledErrors.containsKey(exception.getClass())) {
                resp.setStatus(handledErrors.get(exception.getClass()));
            }
            if(exception.getClass().equals(PersistenceException.class))
            {
                PersistenceException ex = (PersistenceException) exception;
                if(ex.getMessage().contains("ConstraintViolationException"))
                {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    if(ex.getCause().getCause().getMessage().contains(" is still referenced"))
                    writer.write(ErrorDto
                            .builder()
                            .error(ex.getClass().getSimpleName())
                            .message(ex.getCause().getCause().getMessage() + "You should consider removing dependent objects first.")
                            .build().toJson());
                }
            }
            else if (exception.getClass().equals(ConstraintViolationException.class)) {
                ConstraintViolationException ex = (ConstraintViolationException) exception;

                StringBuilder commonErrorMessage = new StringBuilder();
                for (ConstraintViolation<?> constraint : ex.getConstraintViolations()) {
                    commonErrorMessage.append(constraint.getPropertyPath()).append(" ").append(constraint.getMessage()).append("\n");
                }

                writer.write(ErrorDto
                        .builder()
                        .error(ex.getClass().getSimpleName())
                        .message(commonErrorMessage.toString())
                        .build().toJson());

            } else {
                writer.write(ErrorDto
                        .builder()
                        .error(exception.getClass().getSimpleName())
                        .message(exception.getMessage())
                        .build().toJson());
            }
        }
    }
}