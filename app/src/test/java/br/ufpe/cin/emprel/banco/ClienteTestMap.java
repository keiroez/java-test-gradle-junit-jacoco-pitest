package br.ufpe.cin.emprel.banco;

import br.ufpe.cin.emprel.banco.cliente.Cliente;
import br.ufpe.cin.emprel.banco.cliente.TipoCliente;
import br.ufpe.cin.emprel.banco.excecoes.ClienteExistenteException;
import br.ufpe.cin.emprel.banco.excecoes.ClienteInexistenteException;
import br.ufpe.cin.emprel.banco.fachada.Fachada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTestMap {
    Fachada fachada;
    @BeforeEach
    void setup(){
        fachada = Fachada.obterInstancia("map");
    }

    @AfterEach
    void restart(){
        fachada.restartInstancia();
    }

    @Test
    void consultarCliente(){
        Cliente cli1 = new Cliente("123","nomePre1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("456","nomePre2",TipoCliente.VIP);
        Cliente cli3 = new Cliente("789","nomePre3",TipoCliente.ESPECIAL);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(cli3);
             Cliente cliente1 = fachada.procurarCliente("123");
             Cliente cliente2 = fachada.procurarCliente("456");
             Cliente cliente3 = fachada.procurarCliente("789");

             assertEquals("nomePre1", cliente1.getNome());
             assertEquals("nomePre2", cliente2.getNome());
             assertEquals("nomePre3", cliente3.getNome());
            assertNotNull(cliente1);
            assertNotNull(cliente2);
            assertNotNull(cliente3);
        } catch (ClienteInexistenteException e) {
            assertNull(e);
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consultarClienteNaoExistente(){
        try {
            assertNull(fachada.procurarCliente("000"));
        } catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        }
    }

    @Test
    void cadastrarCliente(){
        Cliente cli1 = new Cliente("001","TestCad1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("010","TestCad2",TipoCliente.VIP);
        Cliente cli3 = new Cliente("011","TestCad3",TipoCliente.ESPECIAL);

        try {
            fachada.cadastrar(cli1);
            Cliente cliente1 = fachada.procurarCliente("001");
            assertEquals("TestCad1", cliente1.getNome());

            fachada.cadastrar(cli2);
            Cliente cliente2 = fachada.procurarCliente("010");
            assertEquals("TestCad2", cliente2.getNome());

            fachada.cadastrar(cli3);
            Cliente cliente3 = fachada.procurarCliente("011");
            assertEquals("TestCad3", cliente3.getNome());

        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void cadastrarJaExistente(){
        Cliente cli1 = new Cliente("555","TestExistente", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cli1);

            fachada.cadastrar(cli1);
        } catch (ClienteExistenteException e) {
            assertEquals("Cliente ja existe!", e.getMessage());
        }
    }

    @Test
    void atualizar(){
        Cliente cli1 = new Cliente("666","TestAtualizar", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("777","TestAtualizar2", TipoCliente.CLASS);
        Cliente cli3 = new Cliente("666", "TestAtualizar", TipoCliente.VIP);
        Cliente cli4 = new Cliente("777", "Joao", TipoCliente.VIP);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);

//            fachada.atualizar(cli3);
            assertDoesNotThrow(() -> fachada.atualizar(cli3));
            Cliente cliente1 = fachada.procurarCliente("666");
            assertEquals("VIP", cliente1.getTipo().name());

//            fachada.atualizar(cli4);
            assertDoesNotThrow(() -> fachada.atualizar(cli4));
            Cliente cliente2 = fachada.procurarCliente("777");
            assertEquals("Joao", cliente2.getNome());
            assertEquals("VIP", cliente2.getTipo().name());
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void atualizarInexistente(){
        Cliente cli1 = new Cliente("000","TestAtualizarInexist", TipoCliente.CLASS);
        try {
            cli1.setCpf("0000");
            fachada.atualizar(cli1);
        } catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        }
    }

    @Test
    void descadastrar(){
        Cliente cli1 = new Cliente("999","TestRemover", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("999111","TestRemover", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);

            assertDoesNotThrow(() -> fachada.descadastrarCliente("999"));
            assertEquals(1, fachada.listarCliente().size());

            fachada.descadastrarCliente("999111");
            assertEquals(0, fachada.listarCliente().size());
            //Teste na exceção de cliente não existente
            assertNull(fachada.procurarCliente("999"));
        }  catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void descadastrarInexistente(){
        try {
            //Teste na exceção de cliente não existente
            fachada.descadastrarCliente("000");
        }  catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        }
    }

    @Test
    void listarCliente(){
        Cliente cli1 = new Cliente("99900","TestRemover", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("99901","TestRemover", TipoCliente.CLASS);
        Cliente cli3 = new Cliente("99902","TestRemover", TipoCliente.CLASS);
        Cliente cli4 = new Cliente("99903","TestRemover", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(cli3);
            fachada.cadastrar(cli4);
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        }
        List<Cliente> clientesList = fachada.listarCliente();
        assertNotNull(clientesList);
        assertFalse(clientesList.isEmpty());
        assertEquals(4, clientesList.size());
    }

}
