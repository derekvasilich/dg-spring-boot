package com.dg.response;

public class MessageResponse<T> {
    protected T message;

    public MessageResponse(T message) {
        this.message = message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}
