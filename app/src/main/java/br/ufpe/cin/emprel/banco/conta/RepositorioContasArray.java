package br.ufpe.cin.emprel.banco.conta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufpe.cin.emprel.banco.excecoes.ContaInexistenteException;

public class RepositorioContasArray implements IRepositorioContas {
	private ContaAbstrata[] contas;
	private int indice;
	private final static int tamCache = 100;
	public RepositorioContasArray() {
		indice = 0;
		contas = new ContaAbstrata[tamCache];
	}
	/* (non-Javadoc)
	 * @see RepositorioContas#inserir(Conta)
	 */
	@Override
	public void inserir(ContaAbstrata c) {
		contas[indice] = c;
		indice = indice + 1;
	}
	private int procurarIndice(String num) {
		int i = 0;
		int ind = -1;
		boolean achou = false;

		while ((i < indice) && !achou) {
			if ((contas[i].getNumero()).equals(num)) {
				ind = i;
				achou = true;
			}
			i = i + 1;
		}
		return ind;
	}
	
	@Override
	public boolean existe(String num) {
		boolean resp = false;

		int i = this.procurarIndice(num);
		if (i != -1) {
			resp = true;
		}

		return resp;
	}

	@Override
	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		int i = procurarIndice(c.getNumero());
		if (i != -1) {
			contas[i] = c;
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public ContaAbstrata procurar(String num) throws ContaInexistenteException {
		ContaAbstrata c = null;
		if (existe(num)) {
			int i = this.procurarIndice(num);
			c = contas[i];
		} else {
			throw new ContaInexistenteException();
		}

		return c;
	}

	@Override
	public void remover(String num) throws ContaInexistenteException {
		if (existe(num)) {
			int i = this.procurarIndice(num);
			contas[i] = contas[indice - 1];
			contas[indice - 1] = null;
			indice = indice - 1;
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public List<ContaAbstrata> listar() {
		List<ContaAbstrata> lista = new ArrayList();
		for (ContaAbstrata c: contas) {
			if(c!=null)
				lista.add(c);
		}
		return lista;
//		Retornava a lista completa, até com os indices nulos
//		return Arrays.asList(contas);
	}
}
