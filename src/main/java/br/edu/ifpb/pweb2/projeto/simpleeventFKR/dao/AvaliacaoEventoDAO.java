package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.AvaliacaoEvento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

public interface AvaliacaoEventoDAO extends JpaRepository<AvaliacaoEvento, Long>{

	List<AvaliacaoEvento> findByParticipante (User participante);
}
