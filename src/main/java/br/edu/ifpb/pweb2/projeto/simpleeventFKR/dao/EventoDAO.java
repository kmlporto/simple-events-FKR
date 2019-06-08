package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

public interface EventoDAO extends JpaRepository<Evento, Long> {

	List<Evento> findByDono (User dono);

}
