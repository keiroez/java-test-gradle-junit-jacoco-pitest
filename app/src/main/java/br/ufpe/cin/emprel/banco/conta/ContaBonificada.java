package br.ufpe.cin.emprel.banco.conta;

import br.ufpe.cin.emprel.banco.cliente.Cliente;

public class ContaBonificada extends Conta {

	private double bonus;

	public ContaBonificada(String numeroConta, Cliente c) {
		super(numeroConta, c);
	}

	public ContaBonificada(String numeroConta, double saldo, Cliente c) {
		super(numeroConta, saldo, c);
	}

	public void creditar(double valor) {
		bonus = bonus + valor * 0.01;
		super.creditar(valor);//saldo = saldo + valor;
		
	}

	public void renderBonus() {
		super.creditar(bonus);
		bonus = 0;
	}

	public double getBonus() {
		return bonus;
	}
}