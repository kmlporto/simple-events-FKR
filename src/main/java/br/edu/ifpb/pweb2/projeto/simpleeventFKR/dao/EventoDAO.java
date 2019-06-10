package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.StatusEvento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

public interface EventoDAO extends JpaRepository<Evento, Long> {

	List<Evento> findByDono (User dono);
	
	@Query("select e from Evento e left join e.vagas v left join v.especialidade f "
			+ "where lower(e.descricao) like lower(concat('%',:nome,'%'))"
			+ "or lower(e.dono.nome) like lower(concat('%',:nome,'%')) "
			+ "or lower(f.nome) like lower(concat('%',:nome,'%')) "

			)
	List<Evento> findByName (@Param("nome") String nome);
	
	List<Evento> findByStatus(StatusEvento status);


}

//+ "and e.status.name() like '%ABERTO%'"