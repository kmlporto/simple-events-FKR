package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.CandidatoVagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EventoDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.UserDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.VagaDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.CandidatoVaga;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Evento;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Status;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.StatusEvento;
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
	@Autowired
	public CandidatoVagaDAO candidaturaDAO;


	
	@RequestMapping
	public String createDataFaker() {
		createDataEspecialidade();
		createDataUser();
		createDataEvents();
		createDataVagas();
		createDataCandidatoVaga();
		return "datafaker";
	}
	
	public void createDataEspecialidade () {
		Especialidade especialidade;
		for (int i = 0; i < 30; i++) {
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
		for (int i = 0; i < 30; i++) {
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
		for (int i = 0; i < 30; i++) {
			Random rand = new Random();
			evento = new Evento();
			evento.setDescricao(faker.lorem().sentence());
			evento.setData(LocalDateTime.now().plusDays(10));
			evento.setLocal(faker.address().fullAddress());
			evento.setDono(usuarios.get(rand.nextInt(usuarios.size())));
			evento.setStatus(StatusEvento.values()[rand.nextInt(StatusEvento.values().length)]);
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
			vaga.setQtdVagas(rand.nextInt(10)+1);
//			List<Vaga> vagasExistentes = eventoDAO.findById(vaga.getEvento().getId()).get().getVagas();

//			Boolean testeEspecialidade = false;
			Especialidade novaEspecialidade;
			novaEspecialidade = especialidades.get(rand.nextInt(especialidades.size()));
//			do {
//				System.out.println(vagasExistentes);
//				novaEspecialidade = especialidades.get(rand.nextInt(especialidades.size()));
//				System.out.println(novaEspecialidade.getNome());
//				testeEspecialidade = false;
//				for (Vaga vagaExistente : vagasExistentes) {
//					System.out.println(vagaExistente.getEspecialidade().getNome());
//					if(vagaExistente.getEspecialidade().equals(novaEspecialidade)) {
//						testeEspecialidade = false;
//					}
//				}
//				System.out.println(testeEspecialidade);
//			} while (testeEspecialidade);
			vaga.setEspecialidade(novaEspecialidade);
			vagaDAO.save(vaga);
		}
	}

	public void createDataCandidatoVaga () {
		List<Vaga> vagas = vagaDAO.findAll();
		List<User> usuarios = userdao.findAll();
		CandidatoVaga candidatura;
		for (int i = 0; i < 100; i++) {
			Random rand = new Random();
			candidatura = new CandidatoVaga();
			candidatura.setVaga(vagas.get(rand.nextInt(vagas.size())));
			candidatura.setStatus(Status.AGUARDANDO_APROVACAO);
			List<CandidatoVaga> candidaturasExistentes = vagaDAO.findById(candidatura.getVaga().getId()).get().getCandidatoVaga();

			Boolean testeCandidato = false;
			User novoCandidato;
			do {
				novoCandidato = usuarios.get(rand.nextInt(usuarios.size()));
				testeCandidato = false;
				for (CandidatoVaga candidaturaExistente : candidaturasExistentes) {
					if(candidaturaExistente.getCandidato().equals(novoCandidato)) {
						testeCandidato = true;
					}
				}
			} while (testeCandidato);
			candidatura.setCandidato(novoCandidato);
			candidaturaDAO.save(candidatura);
		}
	}
}
