package br.ufpe.cin.emprel.banco.conta;

import java.util.*;

import br.ufpe.cin.emprel.banco.cliente.Cliente;
import br.ufpe.cin.emprel.banco.excecoes.ContaInexistenteException;

public class RepositorioContasMap implements IRepositorioContas {

	private Map<String,ContaAbstrata> contas;

	public RepositorioContasMap() {
		contas = new HashMap<String,ContaAbstrata>();
	}
	
	@Override
	public void inserir(ContaAbstrata c) {
		contas.put(c.getNumero(), c);
	}
	
	@Override
	public boolean existe(String num) {
//		Estava retornando falso
//		return false;
		if(contas.get(num)!=null){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		if (existe(c.getNumero())) {
			contas.put(c.getNumero(), c);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public ContaAbstrata procurar(String num) throws ContaInexistenteException {
		if (existe(num)) {
			return contas.get(num);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public void remover(String num) throws ContaInexistenteException {
		if (existe(num)) {
			contas.remove(num);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public List<ContaAbstrata> listar() {
		//		Estava retornando uma lista vazia
		//		return List.of();
		List<ContaAbstrata> lista = new ArrayList<ContaAbstrata>(contas.values());
		return lista;
	}
}
