package conn.cursos.repository;

import conn.cursos.remedio.Remedio;
import conn.cursos.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
