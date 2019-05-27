package br.edu.ifpb.pweb2.projeto.simpleeventFKR.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_candidato_vaga")
public class Candidato_Vaga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Vaga vaga;
	private int nota_desempenho;
	private State state;
	@OneToOne
	private User candidato;

	public Candidato_Vaga() {
	};

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}

	public int getNota_desempenho() {
		return nota_desempenho;
	}

	public void setNota_desempenho(int nota_desempenho) {
		this.nota_desempenho = nota_desempenho;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public User getCandidato() {
		return candidato;
	}

	public void setCandidato(User candidato) {
		this.candidato = candidato;
	}

	@Override
	public String toString() {
		return "Candidato_Vaga [id=" + id + ", vaga=" + vaga.getEspecialidade().getNome() + ", nota_desempenho=" + nota_desempenho + ", state="
				+ state + ", candidato=" + candidato.getNome() + "]";
	}
	

}
