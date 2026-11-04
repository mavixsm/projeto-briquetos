package br.edu.ibmec.ProjetoBrinquedos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController
{
    @GetMapping("/")
    public String inicio()
    {
        return "index";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/brinquedos")
    public String brinquedos()
    {
        return "brinquedos";
    }
    @GetMapping("/admin")
    public String admin()
    {
        return "admin";
    }
}
