package conn.cursos.controller;

import conn.cursos.remedio.DadosCadastroRemedio;
import conn.cursos.remedio.Remedio;
import conn.cursos.repository.RemedioRepository;
import conn.cursos.service.RemediosListados;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
}
