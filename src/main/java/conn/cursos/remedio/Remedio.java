package conn.cursos.remedio;

import conn.cursos.service.DadosSalvarRemedio;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "remedio")
@Table(name = "remedios")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Remedio {
    public Remedio(DadosCadastroRemedio dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.laboratorio = dados.laboratorio();
        this.via = dados.via();
        this.validade = dados.validade();
        this.quantidade = dados.quantidade();
        this.lote = dados.lote();
    }

    public Remedio() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Via via;

    private String lote;
    private int quantidade;
    private LocalDate validade;

    @Enumerated(EnumType.STRING)
    private Laboratorio laboratorio;
    @Column(columnDefinition = "TINYINT(1)")
    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Via getVia() {
        return via;
    }

    public void setVia(Via via) {
        this.via = via;
    }

    public void atualizaDados(@Valid DadosSalvarRemedio dados) {
        if(!Objects.isNull(dados.nome()) || !dados.nome().isEmpty()) {
            this.nome = dados.nome();
        }
        if (!Objects.isNull(dados.via())) {
            this.via = dados.via();
        }
    }

    public void inativar() {
        this.ativo = false;
    }
    public void AtivarRemedio() {
        if (!this.ativo) this.ativo = true;
    }
}
