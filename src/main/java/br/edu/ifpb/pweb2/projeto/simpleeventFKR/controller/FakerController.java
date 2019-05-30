package br.edu.ifpb.pweb2.projeto.simpleeventFKR.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;

import br.edu.ifpb.pweb2.projeto.simpleeventFKR.dao.EspecialidadeDAO;
import br.edu.ifpb.pweb2.projeto.simpleeventFKR.model.Especialidade;

@Controller
@RequestMapping("/datafaker")
public class FakerController {
//	https://github.com/DiUS/java-faker
	Faker faker = new Faker();
	
	@Autowired
	private EspecialidadeDAO especialidadedao;
	
	@RequestMapping
	public String createDataFaker() {
		createDataEspecialidade();
		return "datafaker";
	}
	
	public void createDataEspecialidade () {
		Especialidade especialidade;
		for (int i = 0; i < 10; i++) {
			especialidade = new Especialidade();
			especialidade.setNome(faker.company().profession());
			especialidade.setDescricao(faker.lorem().paragraph());
			especialidadedao.save(especialidade);
		}
		
	}


}
