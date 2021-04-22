package com.gcb.main.services.exceptions;

public class NotFoundParam extends RuntimeException{
    private String nomeDoAtributo;

    public NotFoundParam(String nomeDoAtributo) { // Parametro para ser chamado
        this.nomeDoAtributo = nomeDoAtributo;
    }

    @Override
    public String getMessage() {
        return nomeDoAtributo; // Mensagem
    }
}
