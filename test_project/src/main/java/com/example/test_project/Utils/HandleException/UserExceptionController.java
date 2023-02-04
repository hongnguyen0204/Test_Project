package com.example.test_project.Utils.HandleException;

import com.example.test_project.Constant.Errors;
import com.example.test_project.Utils.HandleException.Exceptions.EmailExistedException;
import com.example.test_project.Utils.HandleException.Exceptions.EmailNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle user exception
 */
@ControllerAdvice
public class UserExceptionController {
    /**
     * Email not existed exception handler
     *
     * @param exception EmailNotExistException
     * @return message
     */
    @ResponseBody
    @ExceptionHandler(value = EmailNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emailNotExistedException(EmailNotExistException exception) {
        return exception.getMessage();
    }

    /**
     * Email existed exception handler
     *
     * @param exception EmailNotExistException
     * @return message
     */
    @ResponseBody
    @ExceptionHandler(value = EmailExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String emailExistedException(EmailExistedException exception) {
        return exception.getMessage();
    }

    /**
     * Email existed exception handler
     *
     * @return ResponseEntity<?>
     */
    @ResponseBody
    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String wrongEmailOrPassword() {
        return Errors.WRONG_EMAIL_OR_PASSWORD;
    }
}
