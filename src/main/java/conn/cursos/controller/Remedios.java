package conn.cursos.controller;

import conn.cursos.remedio.DadosCadastroRemedio;
import conn.cursos.remedio.Remedio;
import conn.cursos.repository.RemedioRepository;
import conn.cursos.service.DadosDetalhamentoRemedio;
import conn.cursos.service.DadosSalvarRemedio;
import conn.cursos.service.RemediosListados;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/remedios")
public class Remedios {
    @Autowired
    private RemedioRepository remedioRepository;
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados, UriComponentsBuilder uriBuilder) {
        Remedio remedio = new Remedio(dados);
        remedioRepository.save(remedio);
        URI uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamentoRemedio(remedio));
    }

    @GetMapping
    public ResponseEntity<List<RemediosListados>> listar() {
        List<RemediosListados> listaRemedios = remedioRepository.findAllByAtivoTrue().stream()
                .map(RemediosListados::new).toList();
        return ResponseEntity.ok(listaRemedios);
    }
    @GetMapping("RemediosInativados")
    public ResponseEntity<List<RemediosListados>> listarIdInativados() {
        List<RemediosListados> remediosListados = remedioRepository.findAllByAtivoFalse().stream()
                .map(RemediosListados::new).toList();
        return ResponseEntity.ok(remediosListados);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoRemedio> ListarRemediosPorId(@PathVariable long id) {
        Remedio remedio = getReferenceById(id);
        return ResponseEntity.ok(dadosDetalhamentoRemedio(remedio));
    }
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosSalvarRemedio dados) {
        Remedio remedio = getReferenceById(dados.id());
        remedio.atualizaDados(dados);
        return ResponseEntity.ok(dadosDetalhamentoRemedio(remedio));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        getReferenceById(id);
        remedioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("inativar/{id}")
    @Transactional
    public ResponseEntity<Void> inativar(@PathVariable long id) {
        Remedio remedio = getReferenceById(id);
        remedio.inativar();
        return ResponseEntity.noContent().build();
    }
    @PutMapping("ativarRemedio/{id}")
    @Transactional
    public ResponseEntity<Void>atualizarEstadoRemedio(@PathVariable long id) {
        Remedio remedio = getReferenceById(id);
        remedio.AtivarRemedio();
        return ResponseEntity.noContent().build();
    }
    private Remedio getReferenceById(long id) {
        return remedioRepository.getReferenceById(id);
    }
    private static DadosDetalhamentoRemedio dadosDetalhamentoRemedio(Remedio remedio) {
        return new DadosDetalhamentoRemedio(remedio);
    }
}
