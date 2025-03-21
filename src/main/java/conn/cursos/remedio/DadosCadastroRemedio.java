package conn.cursos.remedio;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record DadosCadastroRemedio(
        @NotBlank
        String nome,
        Via via,
        @NotBlank
        String lote,
        int quantidade,
        @Future
        LocalDate validade,
        Laboratorio laboratorio) {
}
