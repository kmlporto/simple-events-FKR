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
	
	private int qtdVagas;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vaga", cascade = CascadeType.ALL)
	private List<CandidatoVaga> candidatoVaga = new ArrayList<>();
	
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
	
	public int getQtdVagas() {
		return qtdVagas;
	}
	
	public void setQtdVagas(int qtd) {
		this.qtdVagas = qtd;
	}
	
	public List<CandidatoVaga> getCandidatoVaga() {
		return candidatoVaga;
	}
	
	public void setCandidatoVaga(ArrayList<CandidatoVaga> candidato) {
		this.candidatoVaga = candidato;
	}

	public void add(CandidatoVaga candidato) {
		this.candidatoVaga.add(candidato);
	}
	
	public void remove(CandidatoVaga candidato) {
		this.candidatoVaga.remove(candidato);
	}
	
	@Override
	public String toString() {
		return "Vaga [id=" + id + ", evento=" + evento.getDescricao() + ", qtd vagas=" + qtdVagas+
				", especialidade=" + especialidade.getNome() + "]";
	}
}
