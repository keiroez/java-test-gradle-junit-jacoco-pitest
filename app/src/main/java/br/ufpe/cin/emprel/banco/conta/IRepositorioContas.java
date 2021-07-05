package br.ufpe.cin.emprel.banco.conta;

import java.util.List;

import br.ufpe.cin.emprel.banco.excecoes.ContaInexistenteException;

public interface IRepositorioContas {

	void inserir(ContaAbstrata c);

	boolean existe(String num);

	void atualizar(ContaAbstrata c) throws ContaInexistenteException;

	ContaAbstrata procurar(String num) throws ContaInexistenteException;

	void remover(String num) throws ContaInexistenteException;

	List<ContaAbstrata> listar();

}