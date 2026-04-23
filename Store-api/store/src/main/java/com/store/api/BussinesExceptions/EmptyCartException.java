package com.store.api.BussinesExceptions;

public class EmptyCartException  extends RuntimeException{
    public EmptyCartException(String message) {
        super(message);
    }
}
