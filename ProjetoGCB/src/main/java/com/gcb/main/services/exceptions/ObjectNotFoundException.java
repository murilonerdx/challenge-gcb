package com.gcb.main.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    private String nomeDoAtributo;

    public ObjectNotFoundException(String nomeDoAtributo) { // Parametro para ser chamado
        this.nomeDoAtributo = nomeDoAtributo;
    }

    @Override
    public String getMessage() {
        return "Not found obj"; // Mensagem
    }


}
