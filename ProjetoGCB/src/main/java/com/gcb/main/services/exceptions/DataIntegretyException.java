package com.gcb.main.services.exceptions;

public class DataIntegretyException extends RuntimeException{

    private String mensagem;
    public DataIntegretyException(String mensagem){
        super(mensagem);
        this.mensagem=mensagem;
    }

    @Override
    public String getMessage(){
        return mensagem;
    }
}
