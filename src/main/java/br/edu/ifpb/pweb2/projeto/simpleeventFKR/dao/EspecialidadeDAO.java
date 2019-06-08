package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;

public interface EspecialidadeDAO extends JpaRepository<Especialidade, Long> {


}
