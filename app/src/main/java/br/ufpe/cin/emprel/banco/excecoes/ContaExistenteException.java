package br.ufpe.cin.emprel.banco.excecoes;
public class ContaExistenteException extends BancoException {
	public ContaExistenteException() {
		super("Conta ja existe!");
	}
	private double saldo;
	//...
}
