package br.com.etec.renatolsf_marcosfta.votacao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TBL_CANDIDATO")
@Getter
@Setter
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CANDIDATO")
    private Long id;

    @Column(name = "TX_NOME", nullable = false, length = 120)
    private String nome;

    @Column(name = "TX_CATEGORIA", nullable = false, length = 60)
    private String categoria;

    @Column(name = "NR_QUANTIDADE_VOTOS", nullable = false)
    private Integer quantidadeVotos = 0;

    @Column(name = "CHK_ELEITO", nullable = false)
    private Boolean eleito = false;
}
