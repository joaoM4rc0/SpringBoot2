package conn.cursos.service;

import conn.cursos.remedio.Remedio;

import java.time.LocalDate;

public record RemediosListados(String nome, int quantidade, LocalDate validade) {
    public RemediosListados(Remedio remedio) {
        this(remedio.getNome(), remedio.getQuantidade(), remedio.getValidade());
    }
}
