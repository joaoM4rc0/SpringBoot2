package conn.cursos.service;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarEstadoRemedio(@NotNull long id, boolean ativo) {

}
