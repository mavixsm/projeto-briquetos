package br.edu.ibmec.ProjetoBrinquedos.controller;

import br.edu.ibmec.ProjetoBrinquedos.model.Brinquedo;
import br.edu.ibmec.ProjetoBrinquedos.repository.BrinquedoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    private final BrinquedoRepository repository;

    public WebController(BrinquedoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/painel")
    public String painel(Model model) {
        return "brinquedos";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @PostMapping("/admin/brinquedos")
    public String salvar(@ModelAttribute Brinquedo brinquedo) {
        return "redirect:/admin";
    }

    @PostMapping("/admin/brinquedos/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        return "redirect:/admin";
    }
}
