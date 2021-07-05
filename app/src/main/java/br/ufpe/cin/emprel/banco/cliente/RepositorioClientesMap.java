package br.ufpe.cin.emprel.banco.cliente;

import java.util.*;

import br.ufpe.cin.emprel.banco.excecoes.ClienteInexistenteException;

public class RepositorioClientesMap implements IRepositorioClientes {
	private Map<String,Cliente> clientes;

	public RepositorioClientesMap() {
		clientes = new HashMap<String,Cliente>();
	}

	@Override
	public void atualizar(Cliente c) throws ClienteInexistenteException {
		if (existe(c.getCpf())) {
			clientes.put(c.getCpf(), c);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public boolean existe(String cpf) {
//		Estava retornando true apenas
//		return true;
		if(clientes.get(cpf)!=null){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void inserir(Cliente c) {
		clientes.put(c.getCpf(), c);
	}

	@Override
	public Cliente procurar(String cpf) throws ClienteInexistenteException {
		if (existe(cpf)) {
			return clientes.get(cpf);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public void remover(String cpf) throws ClienteInexistenteException {
		if (existe(cpf)) {
			clientes.remove(cpf);
		} else {
			throw new ClienteInexistenteException();
		}
	}

	@Override
	public List<Cliente> listar() {
//		Estava retornando uma lista vazia
//		return List.of();
		List<Cliente> lista = new ArrayList<Cliente>(clientes.values());
		return lista;
	}
}