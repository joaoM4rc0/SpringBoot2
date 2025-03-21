package conn.cursos.repository;

import conn.cursos.remedio.Remedio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemedioRepository extends JpaRepository<Remedio, Long>{
}
