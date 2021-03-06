package br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

public interface UserDAO extends JpaRepository<User, Long> {
	
	User findByEmail (String email);

}
