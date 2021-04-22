package com.gcb.main.entities.utils;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import com.gcb.main.entities.AddressLocation;
import com.gcb.main.services.exceptions.NotFoundParam;
import com.google.gson.Gson;

public class ServiceCep {

    public static class ServicoDeCep {
        static String webService = "http://viacep.com.br/ws/";
        static int codigoSucesso = 200;

        public static AddressLocation buscaEnderecoPelo(String cep) {
            try {
                cep = cep.replaceAll("\\D", "");
                String urlParaChamada = webService + cep + "/json";
                URL url = new URL(urlParaChamada);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                if (conexao.getResponseCode() != codigoSucesso)
                    throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

                BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
                String jsonEmString = Util.converteJsonEmString(resposta);

                Gson gson = new Gson();
                return gson.fromJson(jsonEmString, AddressLocation.class);
            } catch (Exception e) {
                throw new NotFoundParam("CEP invalido!");
            }
        }
    }
}
