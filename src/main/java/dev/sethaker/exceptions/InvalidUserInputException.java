package dev.sethaker.exceptions;

public class InvalidUserInputException extends Exception{
    public InvalidUserInputException() {
        super();
    }
    public InvalidUserInputException(String message){
        super(message);
    }
}
