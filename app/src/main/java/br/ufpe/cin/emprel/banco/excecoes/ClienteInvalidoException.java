package br.ufpe.cin.emprel.banco.excecoes;
public class ClienteInvalidoException extends BancoException {
	public ClienteInvalidoException() {
		super("Cliente invalido!");
	}
	private double saldo;
	//...
}
