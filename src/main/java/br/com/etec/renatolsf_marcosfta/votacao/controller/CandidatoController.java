package br.com.etec.renatolsf_marcosfta.votacao.controller;

import br.com.etec.renatolsf_marcosfta.votacao.dto.CandidatoRequest;
import br.com.etec.renatolsf_marcosfta.votacao.entity.Candidato;
import br.com.etec.renatolsf_marcosfta.votacao.service.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    private final CandidatoService service;

    public CandidatoController(CandidatoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Candidato> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Candidato buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Candidato> criar(@Valid @RequestBody CandidatoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    public Candidato editar(@PathVariable Long id, @Valid @RequestBody CandidatoRequest request) {
        return service.editar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/votar")
    public Candidato votar(@PathVariable Long id) {
        return service.votar(id);
    }
}
