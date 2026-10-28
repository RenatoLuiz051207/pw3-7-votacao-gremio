package br.com.etec.renatolsf_marcosfta.votacao.service;

import br.com.etec.renatolsf_marcosfta.votacao.dto.CandidatoRequest;
import br.com.etec.renatolsf_marcosfta.votacao.entity.Candidato;
import br.com.etec.renatolsf_marcosfta.votacao.repository.CandidatoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CandidatoService {

    private final CandidatoRepository repository;

    public CandidatoService(CandidatoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Candidato> listar() {
        return repository.findAll(Sort.by(
                Sort.Order.desc("quantidadeVotos"),
                Sort.Order.asc("id")
        ));
    }

    @Transactional(readOnly = true)
    public Candidato buscar(Long id) {
        return encontrarOuFalhar(id);
    }

    @Transactional
    public Candidato criar(CandidatoRequest request) {
        Candidato candidato = new Candidato();
        candidato.setNome(request.nome().trim());
        candidato.setCategoria(request.categoria().trim());

        candidato.setQuantidadeVotos(0);
        candidato.setEleito(false);
        return repository.save(candidato);
    }

    @Transactional
    public Candidato editar(Long id, CandidatoRequest request) {
        Candidato candidato = encontrarOuFalhar(id);
        String categoriaAnterior = candidato.getCategoria();
        String novaCategoria = request.categoria().trim();

        candidato.setNome(request.nome().trim());
        candidato.setCategoria(novaCategoria);
        repository.saveAndFlush(candidato);

        if (!categoriaAnterior.equalsIgnoreCase(novaCategoria)) {
            recalcularEleito(categoriaAnterior);
            recalcularEleito(novaCategoria);
        }
        return candidato;
    }

    @Transactional
    public void excluir(Long id) {
        Candidato candidato = encontrarOuFalhar(id);
        String categoria = candidato.getCategoria();
        repository.delete(candidato);
        repository.flush();
        recalcularEleito(categoria);
    }

    @Transactional
    public Candidato votar(Long id) {
        Candidato candidato = encontrarOuFalhar(id);
        if (candidato.getQuantidadeVotos() == Integer.MAX_VALUE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Limite de votos atingido");
        }

        candidato.setQuantidadeVotos(candidato.getQuantidadeVotos() + 1);
        repository.saveAndFlush(candidato);
        recalcularEleito(candidato.getCategoria());
        return candidato;
    }

    private Candidato encontrarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Candidato nao encontrado: " + id));
    }

    private void recalcularEleito(String categoria) {
        List<Candidato> candidatos = repository
                .findByCategoriaIgnoreCaseOrderByQuantidadeVotosDescIdAsc(categoria);


        for (int i = 0; i < candidatos.size(); i++) {
            Candidato candidato = candidatos.get(i);
            candidato.setEleito(i == 0 && candidato.getQuantidadeVotos() > 0);
        }
        repository.saveAll(candidatos);
    }
}
