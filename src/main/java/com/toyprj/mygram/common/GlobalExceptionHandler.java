package com.toyprj.mygram.common;

import com.toyprj.mygram.response.Response;
import com.toyprj.mygram.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Response handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new Response<>(ResponseCode.ILLEGAL_ARGUMENT, illegalArgumentException.getMessage(),
                illegalArgumentException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public Response handleIllegalStateException(IllegalStateException illegalStateException) {
        return new Response<>(ResponseCode.ILLEGAL_STATE, illegalStateException.getMessage(), illegalStateException);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception exception) {
        return new Response<>(ResponseCode.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

}
