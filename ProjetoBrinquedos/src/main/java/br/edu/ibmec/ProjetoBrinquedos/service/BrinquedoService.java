package br.edu.ibmec.ProjetoBrinquedos.service;

import br.edu.ibmec.ProjetoBrinquedos.model.Brinquedo;
import br.edu.ibmec.ProjetoBrinquedos.repository.BrinquedoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BrinquedoService
{
    private final BrinquedoRepository brinquedoRepository;
    public BrinquedoService(BrinquedoRepository brinquedoRepository)
    {
        this.brinquedoRepository = brinquedoRepository;
    }
    public Brinquedo cadastrar(Brinquedo brinquedo)
    {
        return brinquedoRepository.save(brinquedo);
    }
    public List<Brinquedo> listar()
    {
        return brinquedoRepository.findAll();
    }
    public Brinquedo buscarPorId(long id)
    {
        return brinquedoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public Brinquedo atualizar(long id, Brinquedo dados)
    {
        Brinquedo brinquedo = buscarPorId(id);

        brinquedo.setNome(dados.getNome());
        brinquedo.setClassificacao(dados.getClassificacao());
        brinquedo.setPreco(dados.getPreco());
        brinquedo.setTipo(dados.getTipo());
        brinquedo.setTamanho(dados.getTamanho());

        return brinquedoRepository.save(brinquedo);
    }
    public void excluir(long id)
    {
        Brinquedo brinquedo = buscarPorId(id);
        brinquedoRepository.delete(brinquedo);
    }
}
