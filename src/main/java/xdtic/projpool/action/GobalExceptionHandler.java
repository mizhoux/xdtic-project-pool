package xdtic.projpool.action;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xdtic.projpool.model.RespCode;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@ControllerAdvice
public class GobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespCode handleBindException(BindException ex) {
        return RespCode.errorOf(ex.getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 处理 @RequestBody, see https://jira.spring.io/browse/SPR-10157
    public RespCode handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return RespCode.errorOf(ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespCode handleValidationException(ConstraintViolationException ex) {
        return RespCode.errorOf(
                ex.getConstraintViolations().stream()
                        .map(c -> c.getMessage()).findFirst().get());
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespCode handleRuntimeException(RuntimeException ex) {
        ex.printStackTrace(System.err);
        return RespCode.errorOf(ex.getMessage());
    }

}
