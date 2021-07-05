package br.ufpe.cin.emprel.banco.cliente;

public class Cliente {

	private String cpf;

	private String nome;

	private TipoCliente tipo;

	public Cliente(String newCpf, String newNome, TipoCliente newTipo) {
		this.cpf = newCpf;
		this.nome = newNome;
		this.tipo = newTipo;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public TipoCliente getTipo() {
		return tipo;
	}

	public void setCpf(String newCpf) {
		cpf = newCpf;
	}

	public void setNome(String newNome) {
		nome = newNome;
	}

	public void setTipo(TipoCliente newtipo) {
		this.tipo = newtipo;
	}
}