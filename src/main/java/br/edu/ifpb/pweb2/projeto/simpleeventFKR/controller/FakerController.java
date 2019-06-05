package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;

@Controller
@RequestMapping("/datafaker")
public class FakerController {
//	https://github.com/DiUS/java-faker
	Faker faker = new Faker();
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EspecialidadeDAO especialidadedao;
	@Autowired
	private UserDAO userdao;
	
	
	@RequestMapping
	public String createDataFaker() {
		createDataEspecialidade();
		createDataUser();
		return "datafaker";
	}
	
	public void createDataEspecialidade () {
		Especialidade especialidade;
		for (int i = 0; i < 10; i++) {
			especialidade = new Especialidade();
			especialidade.setNome(faker.company().profession());
			especialidade.setDescricao(faker.lorem().characters(100));
			especialidadedao.save(especialidade);
		}
		
	}
	
	public void createDataUser () {
		User user;
		user = new User();
		user.setNome("admin");
		user.setEmail("admin@test");
		user.setAdmin(true);
		user.setSenha(passwordEncoder.encode("admin"));
		userdao.save(user);
		for (int i = 0; i < 10; i++) {
			user = new User();
			user.setNome(faker.name().firstName());
			user.setEmail(user.getNome().toLowerCase()+"@test");
			user.setSenha(passwordEncoder.encode(user.getNome().toLowerCase()));
			userdao.save(user);
		}
		
	}

}
