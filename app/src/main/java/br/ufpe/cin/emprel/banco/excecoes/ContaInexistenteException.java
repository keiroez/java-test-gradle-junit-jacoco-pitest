package br.ufpe.cin.emprel.banco.excecoes;
public class ContaInexistenteException extends BancoException {
	public ContaInexistenteException() {
		super("Conta Inexistente!");
	}
	private double saldo;
	//...
}
