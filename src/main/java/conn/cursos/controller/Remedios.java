package conn.cursos.controller;

import conn.cursos.remedio.DadosCadastroRemedio;
import conn.cursos.remedio.Remedio;
import conn.cursos.repository.RemedioRepository;
import conn.cursos.service.DadosSalvarRemedio;
import conn.cursos.service.RemediosListados;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<RemediosListados> listar() {
        return remedioRepository.findAll().stream()
                .map(RemediosListados::new).toList();
    }
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosSalvarRemedio dados) {
        Remedio remedio = getReferenceById(dados.id());
        remedio.atualizaDados(dados);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable long id) {
        getReferenceById(id);
        remedioRepository.deleteById(id);
    }
    private Remedio getReferenceById(long id) {
        return remedioRepository.getReferenceById(id);
    }
}
