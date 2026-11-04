package br.edu.ibmec.ProjetoBrinquedos.controller;

import br.edu.ibmec.ProjetoBrinquedos.model.Brinquedo;
import br.edu.ibmec.ProjetoBrinquedos.service.BrinquedoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController
{
    private final BrinquedoService brinquedoService;
    public BrinquedoController(BrinquedoService brinquedoService)
    {
        this.brinquedoService = brinquedoService;
    }
   @PostMapping
    public ResponseEntity<Brinquedo> cadastrar(@RequestBody Brinquedo brinquedo)
   {
       Brinquedo brinquedoNovo = brinquedoService.cadastrar(brinquedo);
       return ResponseEntity.ok(brinquedoNovo);
   }
   @GetMapping
    public List<Brinquedo> listar(@RequestBody Brinquedo brinquedo)
   {
       return brinquedoService.listar();
   }
   @GetMapping("/{id}")
    public Brinquedo buscarPorId(@PathVariable String id)
   {
       return brinquedoService.buscarPorId(Integer.parseInt(id));
   }
   @PutMapping ("/{id}")
    public Brinquedo atualizar(@PathVariable long id, @RequestBody Brinquedo brinquedo)
   {
       return brinquedoService.atualizar(id, brinquedo);
   }
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable long id)
   {
       brinquedoService.excluir(id);
       return  ResponseEntity.noContent().build();
   }
}
