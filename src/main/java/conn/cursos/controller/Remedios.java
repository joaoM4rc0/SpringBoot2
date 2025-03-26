package conn.cursos.controller;

import conn.cursos.remedio.DadosCadastroRemedio;
import conn.cursos.remedio.Remedio;
import conn.cursos.repository.RemedioRepository;
import conn.cursos.service.DadosDetalhamentoRemedio;
import conn.cursos.service.DadosSalvarRemedio;
import conn.cursos.service.RemediosListados;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remedios")
public class Remedios {
    @Autowired
    private RemedioRepository remedioRepository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {
        remedioRepository.save(new Remedio(dados));
    }
    @GetMapping
    public ResponseEntity<List<RemediosListados>> listar() {
        List<RemediosListados> listaRemedios = remedioRepository.findAllByAtivoTrue().stream()
                .map(RemediosListados::new).toList();
        return ResponseEntity.ok(listaRemedios);
    }
    @GetMapping("RemediosInativados")
    public List<RemediosListados> listarIdInativados() {
        return remedioRepository.findAllByAtivoFalse().stream()
                .map(RemediosListados::new).toList();
    }
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosSalvarRemedio dados) {
        Remedio remedio = getReferenceById(dados.id());
        remedio.atualizaDados(dados);
        return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
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
}
