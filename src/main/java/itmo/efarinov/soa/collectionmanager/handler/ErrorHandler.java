package itmo.efarinov.soa.collectionmanager.handler;

import itmo.efarinov.soa.collectionmanager.dto.ErrorDto;
import itmo.efarinov.soa.collectionmanager.error.BadRequestException;
import lombok.SneakyThrows;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/error")
public class ErrorHandler extends HttpServlet {
    private final Map<Class<?>, Integer> handledErrors = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        handledErrors.put(NumberFormatException.class, HttpServletResponse.SC_BAD_REQUEST);
        handledErrors.put(NoResultException.class, HttpServletResponse.SC_NOT_FOUND);
        handledErrors.put(BadRequestException.class, HttpServletResponse.SC_BAD_REQUEST);
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
        System.out.println("HANDLER INVOKED");
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = resp.getWriter()) {
            Throwable exception = (Throwable) req.getAttribute("javax.servlet.error.exception");
            System.out.println(String.format("GOT ERROR TO HANDLE %s", exception));
            if (handledErrors.containsKey(exception.getClass())) {
                resp.setStatus(handledErrors.get(exception.getClass()));
            }

            writer.write(ErrorDto
                    .builder()
                    .Error(exception.getClass().getSimpleName())
                    .Message(exception.getMessage())
                    .build().toJson());
        }
    }


}
