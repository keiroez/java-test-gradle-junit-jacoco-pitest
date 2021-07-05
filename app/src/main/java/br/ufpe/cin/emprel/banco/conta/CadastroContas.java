package br.ufpe.cin.emprel.banco.conta;

import br.ufpe.cin.emprel.banco.excecoes.ContaExistenteException;
import br.ufpe.cin.emprel.banco.excecoes.ContaInexistenteException;
import br.ufpe.cin.emprel.banco.excecoes.SaldoInsuficienteException;

import java.util.List;

public class CadastroContas {

	private IRepositorioContas contas;
	public CadastroContas(IRepositorioContas r) {
		this.contas = r;
	}

	public void cadastrar(ContaAbstrata c) throws ContaExistenteException {
		if (!contas.existe(c.getNumero())) {
			contas.inserir(c);
		} else {
			throw new ContaExistenteException();
		}
	}

	public void creditar(String n, double v) throws ContaInexistenteException {
		ContaAbstrata c = contas.procurar(n);
		c.creditar(v);
	}

	public void debitar(String n, double v) throws ContaInexistenteException, SaldoInsuficienteException {
		ContaAbstrata c = contas.procurar(n);
		//Não estava debitando
		c.debitar(v);
	}

	public ContaAbstrata procurar(String n) throws ContaInexistenteException {
		return contas.procurar(n);
	}

	public void transferir(String origem, String destino, double val) throws ContaInexistenteException, SaldoInsuficienteException {
		ContaAbstrata o = contas.procurar(origem);
		ContaAbstrata d = contas.procurar(destino);
		//Estava enviando a origem invés do destino
//		o.transferir(o, val);
		o.transferir(d, val);
	}

	public void remover(String n) throws ContaInexistenteException {
		contas.remover(n);
	}

	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		contas.atualizar(c);
	}

	public List<ContaAbstrata> listarContas(){
		return contas.listar();
	}
}