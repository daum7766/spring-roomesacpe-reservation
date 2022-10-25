package nextstep.controller;

import nextstep.exception.ReservationDeleteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationControllerAdvice {

    @ExceptionHandler({ReservationDeleteException.class, ReservationDeleteException.class})
    ResponseEntity<String> handleIllegalArgumentException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
