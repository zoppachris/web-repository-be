package id.msams.webrepo.cfg;

import com.toedter.spring.hateoas.jsonapi.JsonApiError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import id.msams.webrepo.ext.minio.err.MinioServiceException;
import id.msams.webrepo.srv.abs.EntityExistingException;
import id.msams.webrepo.srv.abs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseEntityExceptionConfig extends ResponseEntityExceptionHandler {

  private static String getStatusString(HttpStatus status) {
    return status.series().name() + ":" + status.value() + ":" + status.name();
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    super.handleExceptionInternal(ex, body, headers, status, request);
    return ResponseEntity
        .status(status)
        .headers(headers)
        .body(body != null ? body
            : JsonApiError.create()
                .withStatus(getStatusString(status))
                .withTitle(status.getReasonPhrase())
                .withDetail(ex.getLocalizedMessage()));
  }

  @ExceptionHandler({
      Exception.class
  })
  protected ResponseEntity<Object> handleAnyOtherException(Exception ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    Object body = null;

    if (ex instanceof UsernameNotFoundException) {
      status = HttpStatus.UNAUTHORIZED;
    }
    if (ex instanceof InsufficientAuthenticationException) {
      status = HttpStatus.FORBIDDEN;
    }
    if (ex instanceof EntityNotFoundException) {
      status = HttpStatus.NOT_FOUND;
    }
    if (ex instanceof EntityExistingException) {
      status = HttpStatus.BAD_REQUEST;
    }
    if (ex instanceof MinioServiceException) {
      body = JsonApiError.create()
          .withStatus(getStatusString(status))
          .withTitle(ex.getLocalizedMessage())
          .withDetail(ex.getCause().getLocalizedMessage());
    }

    return this.handleExceptionInternal(ex, body, headers, status, request);
  }

}
