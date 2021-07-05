package br.ufpe.cin.emprel.banco.fachada;

import br.ufpe.cin.emprel.banco.conta.*;
import br.ufpe.cin.emprel.banco.cliente.*;
import br.ufpe.cin.emprel.banco.excecoes.*;

import java.util.List;

public class Fachada {

	private static Fachada instancia;

	private CadastroContas contas;
	private CadastroClientes clientes;

	IRepositorioContas rep;
	IRepositorioClientes repClientes;

	private Fachada(String arg) {
		initCadastros(arg);
	}

	private void initCadastros(String arg) {
		if(arg.equals("array")) {
				rep = new RepositorioContasArray();
				repClientes = new RepositorioClientesArray();
		}
		if (arg.equals("map")) {
				rep = new RepositorioContasMap();
				repClientes = new RepositorioClientesMap();
		}

		contas = new CadastroContas(rep);
		clientes = new CadastroClientes(repClientes);
	}


	public static Fachada obterInstancia(String arg) {
//		if (instancia == null) {
			instancia = new Fachada(arg);
//		}
		return instancia;
	}

	public void restartInstancia(){
		instancia = null;
	}

	public void atualizar(Cliente c) throws ClienteInexistenteException {
		clientes.atualizar(c);
	}

	public Cliente procurarCliente(String cpf) throws ClienteInexistenteException {
		return clientes.procurar(cpf);
	}

	public void cadastrar(Cliente c) throws ClienteExistenteException {
		clientes.cadastrar(c);
	}

	public void descadastrarCliente(String cpf) throws ClienteInexistenteException {
		clientes.descadastrar(cpf);
	}

	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		contas.atualizar(c);
	}

	public ContaAbstrata procurarConta(String n) throws ContaInexistenteException {
		return contas.procurar(n);
	}

	public void cadastrar(ContaAbstrata c) throws ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException {
		Cliente cli = c.getCliente();
		if (cli != null) {
			//Estava faltando cadastrar conta
			contas.cadastrar(c);
//			clientes.procurar(cli.getCpf());
		} else {
			throw new ClienteInvalidoException();
		}
	}

	public void descadastrarConta(String n) throws ContaInexistenteException {
		contas.remover(n);
	}

	public void creditar(String n, double v) throws ContaInexistenteException {
		contas.creditar(n, v);
	}

	public void debitar(String n, double v) throws ContaInexistenteException, SaldoInsuficienteException {
		contas.debitar(n, v);
	}

	public void transferir(String origem, String destino, double val) throws ContaInexistenteException, SaldoInsuficienteException {
		contas.transferir(origem, destino, val);
	}

	//Metodo para listar cliente inexistente
	public List<Cliente> listarCliente(){
		return clientes.listarClientes();
	}

	//Metodo listar contas inexistente
	public List<ContaAbstrata> listarContas(){
		return contas.listarContas();
	}
}