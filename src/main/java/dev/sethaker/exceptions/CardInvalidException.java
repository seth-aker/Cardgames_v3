package dev.sethaker.exceptions;

public class CardInvalidException extends Exception {
    public CardInvalidException(){super();}

    public CardInvalidException(String message){
        super(message);
    }
}
