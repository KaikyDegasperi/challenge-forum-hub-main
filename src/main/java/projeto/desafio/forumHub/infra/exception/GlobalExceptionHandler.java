package projeto.desafio.forumHub.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RegisterException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> registerExceptionHandler(RuntimeException exc) {
        DefaultExceptionResponse response = new DefaultExceptionResponse(LocalDateTime.now(), 400, exc.getMessage());
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exc) {
        List<FieldError> erros = exc.getFieldErrors();

        return ResponseEntity.badRequest().body(
                erros.stream().map(FieldExceptionResponse::new).toList());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> notFoundExceptionHandler(NotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({TokenException.class})
    public ResponseEntity<?> tokenExceptionHandler(TokenException exc) {
        DefaultExceptionResponse response = new DefaultExceptionResponse(LocalDateTime.now(), 403, exc.getMessage());
        return ResponseEntity.status(403).body(response);
    }
}


record DefaultExceptionResponse(LocalDateTime timestamp,
                                Integer status,
                                String error){}
record FieldExceptionResponse(String field, String message) {
    public FieldExceptionResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
