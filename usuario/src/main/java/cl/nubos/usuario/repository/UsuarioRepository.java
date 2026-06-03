package cl.nubos.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nubos.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{

}