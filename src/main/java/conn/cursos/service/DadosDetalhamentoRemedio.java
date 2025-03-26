package conn.cursos.service;

import conn.cursos.remedio.Laboratorio;
import conn.cursos.remedio.Remedio;
import conn.cursos.remedio.Via;

import java.time.LocalDate;

public record DadosDetalhamentoRemedio(
        long id,
        String nome,
        Via via,
        int quantidade,
        LocalDate validade,
        Laboratorio laboratorio,
        boolean ativo) {
    public DadosDetalhamentoRemedio(Remedio remedio) {
        this(
                remedio.getId(),
                remedio.getNome(),
                remedio.getVia(),
                remedio.getQuantidade(),
                remedio.getValidade(),
                remedio.getLaboratorio(),
                remedio.isAtivo()
        );
    }
}
