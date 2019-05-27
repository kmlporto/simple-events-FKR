package br.edu.ifpb.pweb2.projeto.simpleeventFKR.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_vaga")
public class Vaga {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Evento evento;
	private int qtd_vagas;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vaga", cascade = CascadeType.ALL)
	private List<Candidato_Vaga> candidato_vaga = new ArrayList<>();
	@OneToOne
	private Especialidade especialidade;
	
	public Vaga() {};
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public int getQtd_vagas() {
		return qtd_vagas;
	}
	public void setQtd_vagas(int qtd_vagas) {
		this.qtd_vagas = qtd_vagas;
	}
	public List<Candidato_Vaga> getCandidato_vaga() {
		return candidato_vaga;
	}
	public void setCandidato_vaga(ArrayList<Candidato_Vaga> candidato_vaga) {
		this.candidato_vaga = candidato_vaga;
	}

	@Override
	public String toString() {
		return "Vaga [id=" + id + ", evento=" + evento.getDescricao() + ", qtd_vagas=" + qtd_vagas+
				", especialidade=" + especialidade.getNome() + "]";
	}
	
	
	
	
	
	
	

}
