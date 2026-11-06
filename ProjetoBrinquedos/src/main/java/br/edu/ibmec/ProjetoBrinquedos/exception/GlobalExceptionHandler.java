package br.edu.ibmec.ProjetoBrinquedos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarErro(RuntimeException erro) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", erro.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> tratarErroGeral(Exception erro) {

        Map<String, String> resposta = new HashMap<>();

        resposta.put("erro", "Ocorreu um erro interno no servidor.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resposta);
    }
}