package com.netcracker.edu.inventory.exception;

/**
 * Created by makovetskyi on 17.11.16.
 */
public class WrongPKMethodException extends RuntimeException {

    public WrongPKMethodException() {
        super();
    }

    public WrongPKMethodException(String s) {
        super(s);
    }
}
