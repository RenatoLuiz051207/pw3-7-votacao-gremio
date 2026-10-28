package br.com.etec.renatolsf_marcosfta.votacao.repository;

import br.com.etec.renatolsf_marcosfta.votacao.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByCategoriaIgnoreCaseOrderByQuantidadeVotosDescIdAsc(String categoria);
}
