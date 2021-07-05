package br.ufpe.cin.emprel.banco.conta;

import br.ufpe.cin.emprel.banco.cliente.Cliente;
import br.ufpe.cin.emprel.banco.excecoes.SaldoInsuficienteException;

public class ContaImposto extends ContaAbstrata {
	private final double TAXA = 0.001;
	public ContaImposto(String num, Cliente c) {
		super(num, c);
	}
	
	public ContaImposto(String num, double s, Cliente c) {
		super(num,s,c);
	}

	@Override
	public void debitar(double valor) throws SaldoInsuficienteException {
		double imposto = valor * TAXA;
		double valorDebitado = valor + imposto;
		if (valorDebitado <= getSaldo()) {
			setSaldo(getSaldo() - valorDebitado);
		} else {
			throw new SaldoInsuficienteException();
		}
	}

}