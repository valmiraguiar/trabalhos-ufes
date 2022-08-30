package com.ufes.autenticacao.atvcomplementar.autenticacao.token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;



public class TokenAuthenticator {
    
    public String gerarToken(String nomeUsuario, String senha) {
        String token;
        String texto = nomeUsuario+"-"+senha;
        
        token = new String(Base64.getEncoder().encode(texto.getBytes()));

        return token;        
    }

    public String decodificarToken(String token) {
        String textoDecoded = new String(Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8)));

        return textoDecoded;
    }
}
