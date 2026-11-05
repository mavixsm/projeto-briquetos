package br.edu.ibmec.ProjetoBrinquedos.controller;

import br.edu.ibmec.ProjetoBrinquedos.model.Brinquedo;
import br.edu.ibmec.ProjetoBrinquedos.repository.BrinquedoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String painel(Model model)
    {
        model.addAttribute("brinquedos", repository.findAll());
        return "brinquedos";
    }

    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("brinquedo", new Brinquedo());
        model.addAttribute("brinquedos", repository.findAll());
        return "admin";
    }

    @PostMapping("/admin/salvar")
    public String salvar(@Valid @ModelAttribute("brinquedo") Brinquedo brinquedo,
                         BindingResult resultado,
                         Model model)
    {
        if (resultado.hasErrors())
        {
            model.addAttribute("brinquedos", repository.findAll());
            return "admin";
        }

        repository.save(brinquedo);
        return "redirect:/admin";
    }

    @PostMapping("/admin/brinquedos/{id}/excluir")
    public String excluir(@PathVariable Long id)
    {
        repository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/brinquedos/{id}/editar")
    public String editar(@PathVariable Long id, Model model)
    {
        Brinquedo brinquedo = repository.findById(id).orElseThrow(() ->
                              new IllegalArgumentException("Brinquedo não encontrado"));
        System.out.println("ID do brinquedo: " + brinquedo.getId());
        model.addAttribute("brinquedo", brinquedo);
        model.addAttribute("brinquedos", repository.findAll());
        return "admin";
    }
}
