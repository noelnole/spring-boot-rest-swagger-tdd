package com.sample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not found Exception of an Account
 * @author Noel Rodriguez
 * @Date 23/03/2017
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountId) {
        super(String.format("Account %s not found", accountId));
    }
}
