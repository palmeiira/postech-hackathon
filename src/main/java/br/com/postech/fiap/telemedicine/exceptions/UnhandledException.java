package br.com.postech.fiap.telemedicine.exceptions;

public class UnhandledException extends RuntimeException{
    public UnhandledException(String message, Throwable cause) {
        super(message, cause);
    }
}
