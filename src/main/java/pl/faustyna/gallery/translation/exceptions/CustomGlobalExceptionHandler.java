package pl.faustyna.gallery.translation.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    // Let Spring BasicErrorController handle the exception, we just override the status code
    @ExceptionHandler(ResourceNotFoundException.class)
    public void springHandleNotFound(final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public void springHandleSQLError(final HttpServletResponse response, final SQLSyntaxErrorException e) throws IOException {

        ExceptionUtils.logException(e);
        response.getWriter().print(ExceptionUtils.getExceptionJSON(e));
        response.setStatus(HttpStatus.NOT_FOUND.value());


    }


    // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                 final HttpHeaders headers,
                                 final HttpStatus status, final WebRequest request) {

        final Map<String, List<String>> fieldErrors = new HashMap<>();

        for (final FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (fieldErrors.containsKey(fieldError.getField())) {
                final List<String> currenList = fieldErrors.get(fieldError.getField());
                currenList.add(fieldError.getDefaultMessage());
                fieldErrors.replace(fieldError.getField(), currenList);
            } else {
                final List<String> currentList = new ArrayList<>();
                currentList.add(fieldError.getDefaultMessage());
                fieldErrors.put(fieldError.getField(), currentList);
            }
        }

        return new ResponseEntity(fieldErrors, headers, status);

    }

}