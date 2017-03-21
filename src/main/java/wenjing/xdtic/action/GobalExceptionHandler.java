package wenjing.xdtic.action;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@ControllerAdvice
public class GobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace(System.err);
        return "error";
    }

}
