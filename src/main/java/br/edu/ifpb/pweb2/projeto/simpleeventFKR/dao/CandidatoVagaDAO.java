package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.CandidatoVaga;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

public interface CandidatoVagaDAO extends JpaRepository<CandidatoVaga, Long>{
	
	List<CandidatoVaga> findByCandidato (User user);

}
