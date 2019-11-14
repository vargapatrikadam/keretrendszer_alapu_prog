package filmespelda.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public void testIllegalArgument(Exception e){
        System.out.println(e.getMessage());
    }
}
