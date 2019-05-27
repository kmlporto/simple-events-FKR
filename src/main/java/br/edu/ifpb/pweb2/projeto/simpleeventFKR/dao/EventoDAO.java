package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;

public interface EventoDAO extends JpaRepository<Evento, Long> {

}
