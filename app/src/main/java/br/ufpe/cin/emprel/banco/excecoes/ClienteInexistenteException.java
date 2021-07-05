package br.ufpe.cin.emprel.banco.excecoes;
public class ClienteInexistenteException extends BancoException {
	public ClienteInexistenteException() {
		super("Cliente inexistente!");
	}
	private double saldo;
	//...
}
