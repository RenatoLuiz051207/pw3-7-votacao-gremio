package br.com.etec.renatolsf_marcosfta.votacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CandidatoRequest(
        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 120, message = "O nome deve ter no maximo 120 caracteres")
        String nome,
        @NotBlank(message = "A categoria e obrigatoria")
        @Size(max = 60, message = "A categoria deve ter no maximo 60 caracteres")
        String categoria
) {
}
