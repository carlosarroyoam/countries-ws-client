package com.carlosarroyoam.ws.client.core.exception;

import com.carlosarroyoam.ws.client.core.exception.dto.AppExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  private final ApiExceptionResponseFactory apiExceptionResponseFactory;

  public GlobalExceptionHandler(ApiExceptionResponseFactory apiExceptionResponseFactory) {
    this.apiExceptionResponseFactory = apiExceptionResponseFactory;
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<AppExceptionDto> handleResponseStatus(
      ResponseStatusException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getReason(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<AppExceptionDto> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getMessage(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<AppExceptionDto> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getMessage(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  public ResponseEntity<AppExceptionDto> handleNoHandlerFound(
      NoHandlerFoundException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getMessage(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<AppExceptionDto> handleNoResourceFound(
      NoResourceFoundException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getMessage(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<AppExceptionDto> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, ex.getMessage(), request);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<AppExceptionDto> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    Map<String, String> details =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing));

    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, "Invalid request data", request, details);

    return ResponseEntity.status(status).body(appExceptionDto);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<AppExceptionDto> handleException(Exception ex, HttpServletRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    AppExceptionDto appExceptionDto =
        apiExceptionResponseFactory.build(status, "Whoops! Something went wrong", request);

    log.error("Whoops! Something went wrong: ", ex);

    return ResponseEntity.status(status).body(appExceptionDto);
  }
}
