package conn.cursos.service;

import conn.cursos.remedio.Remedio;
import conn.cursos.remedio.Via;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record DadosSalvarRemedio(@NotNull long id, String nome, Via via) {

}
