package br.ufpe.cin.emprel.banco.cliente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufpe.cin.emprel.banco.excecoes.ClienteInexistenteException;

public class RepositorioClientesArray implements IRepositorioClientes {
	private Cliente[] clientes;
	private int indice;

	private final static int tamCache = 100;

	public RepositorioClientesArray() {
		indice = 0;
		clientes = new Cliente[tamCache];
	}

	@Override
	public void atualizar(Cliente c) throws ClienteInexistenteException {
		int i = procurarIndice(c.getCpf());
		if (i != -1) {
			clientes[i] = c;
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public boolean existe(String cpf) {
		boolean resp = false;

		int i = this.procurarIndice(cpf);
		if (i != -1) {
			resp = true;
		}

		return resp;
	}

	@Override
	public void inserir(Cliente c) {
		clientes[indice] = c;
		indice = indice + 1;
	}

	@Override
	public Cliente procurar(String cpf) throws ClienteInexistenteException {
		Cliente c = null;
		if (existe(cpf)) {
			int i = this.procurarIndice(cpf);
			c = clientes[i];
		} else {
			throw new ClienteInexistenteException();
		}

		return c;
	}

	private int procurarIndice(String cpf) {
		int i = 0;
		int ind = -1;
		boolean achou = false;

		while ((i < indice) && !achou) {
			if ((clientes[i].getCpf()).equals(cpf)) {
				ind = i;
				achou = true;
			}
			i = i + 1;
		}
		return ind;
	}

	@Override
	public void remover(String cpf) throws ClienteInexistenteException {
		if (existe(cpf)) {
			int i = this.procurarIndice(cpf);
			clientes[i] = clientes[indice - 1];
			clientes[indice - 1] = null;
			indice = indice - 1;
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList();
		for (Cliente c: clientes) {
			if(c != null)
				lista.add(c);
		}
		return lista;
		//		Retornava a lista completa, até com os indices nulos
		//		return Arrays.asList(clientes);
	}
}