package com.increff.pos.controller;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.MessageData;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class AppRestControllerAdvice {

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handleConnectionError(ResourceAccessException e) {
        MessageData data = new MessageData();
        data.setMessage("Connection to Invoice App refused");
        return data;
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handleBadRequest(ApiException e) {
        MessageData data = new MessageData();
        data.setMessage(e.getMessage());
        return data;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageData handleInternalError(Throwable e) {
        MessageData data = new MessageData();
        data.setMessage("An unknown error has occurred - " + e.getMessage());
        return data;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageData handleBadRequest(HttpMessageNotReadableException e) {
        MessageData data = new MessageData();
        data.setMessage("Invalid request body, pls check input formats");
        return data;
    }
}