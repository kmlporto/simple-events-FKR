package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.VagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.User;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Vaga;

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
	@Autowired 
	public EventoDAO eventoDAO;
	@Autowired 
	public VagaDAO vagaDAO;
	
	
	
	@RequestMapping
	public String createDataFaker() {
		createDataEspecialidade();
		createDataUser();
		createDataEvents();
		createDataVagas();
		return "datafaker";
	}
	
	public void createDataEspecialidade () {
		Especialidade especialidade;
		for (int i = 0; i < 5; i++) {
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
		for (int i = 0; i < 5; i++) {
			user = new User();
			user.setNome(faker.name().firstName());
			user.setEmail(user.getNome().toLowerCase()+"@test");
			user.setSenha(passwordEncoder.encode(user.getNome().toLowerCase()));
			userdao.save(user);
		}
		
	}
	
	public void createDataEvents () {
		List<User> usuarios = userdao.findAll();
		Evento evento;
		for (int i = 0; i < 10; i++) {
			Random rand = new Random();
			evento = new Evento();
			evento.setDescricao(faker.lorem().sentence());
			evento.setData(faker.date().future(10, TimeUnit.DAYS));
			evento.setLocal(faker.address().fullAddress());
			evento.setDono(usuarios.get(rand.nextInt(usuarios.size())));
			
			eventoDAO.save(evento);
		}
		
	}
	
	public void createDataVagas () {
		List<Especialidade> especialidades = especialidadedao.findAll();
		List<Evento> eventos = eventoDAO.findAll();
		Vaga vaga;
		for (int i = 0; i < 50; i++) {
			Random rand = new Random();
			vaga = new Vaga();
			vaga.setEvento(eventos.get(rand.nextInt(eventos.size())));
			vaga.setQtdVagas(rand.nextInt(10));
			vaga.setEspecialidade(especialidades.get(rand.nextInt(especialidades.size())));
			vagaDAO.save(vaga);
		}
		
	}

}
