package com.book.common;

@SuppressWarnings("serial")
public class LogicException extends RuntimeException{

	public LogicException() {
        super();
    }
	
    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
