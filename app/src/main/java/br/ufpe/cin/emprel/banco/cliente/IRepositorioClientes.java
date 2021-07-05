package br.ufpe.cin.emprel.banco.cliente;

import java.util.List;

import br.ufpe.cin.emprel.banco.excecoes.ClienteInexistenteException;

public interface IRepositorioClientes {

	void atualizar(Cliente c) throws ClienteInexistenteException;

	boolean existe(String cpf);

	void inserir(Cliente c);

	Cliente procurar(String cpf) throws ClienteInexistenteException;

	void remover(String cpf) throws ClienteInexistenteException;

	List<Cliente> listar();

}