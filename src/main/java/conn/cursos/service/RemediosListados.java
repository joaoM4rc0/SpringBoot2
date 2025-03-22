package conn.cursos.service;

import conn.cursos.remedio.Remedio;

import java.time.LocalDate;

public record RemediosListados(long id, String nome, int quantidade, String lote, LocalDate validade) {
    public RemediosListados(Remedio remedio) {
        this(remedio.getId(), remedio.getNome(), remedio.getQuantidade(), remedio.getLote(), remedio.getValidade());
    }
}
