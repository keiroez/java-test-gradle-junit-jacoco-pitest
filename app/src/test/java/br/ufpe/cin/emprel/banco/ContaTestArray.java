package br.ufpe.cin.emprel.banco;

import br.ufpe.cin.emprel.banco.cliente.Cliente;
import br.ufpe.cin.emprel.banco.cliente.TipoCliente;
import br.ufpe.cin.emprel.banco.conta.*;
import br.ufpe.cin.emprel.banco.excecoes.*;
import br.ufpe.cin.emprel.banco.fachada.Fachada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContaTestArray {
    Fachada fachada;
    @BeforeEach
    void setup(){
        fachada = Fachada.obterInstancia("array");
    }

    @AfterEach
    void restart(){
        fachada.restartInstancia();
    }

    @Test
    void consultarConta(){
        Cliente cli1 = new Cliente("1234","nomePre1", TipoCliente.CLASS);
        Conta c1 = new Conta("1",100,cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(c1);
            ContaAbstrata cBusca = fachada.procurarConta("1");
            assertNotNull(cBusca);
            assertEquals(100, cBusca.getSaldo());
            assertEquals(cli1.getNome(), cBusca.getCliente().getNome());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        }
    }

    @Test
    void consultarContaInexistente(){
        try {
            fachada.procurarConta("0");
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void cadastrarConta(){
        Cliente cli1 = new Cliente("12345","nomePre1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("56789","nomePre2", TipoCliente.CLASS);
        Conta c1 = new Conta("2",100,cli1);
        Poupanca p1 = new Poupanca("3", 150, cli2);
        ContaBonificada cb1 = new ContaBonificada("4",100,cli2);

        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(c1);
            fachada.cadastrar(p1);
            fachada.cadastrar(cb1);

            ContaAbstrata cBusca = fachada.procurarConta("2");
            assertNotNull(cBusca);
            assertEquals(100, cBusca.getSaldo());
            assertEquals("nomePre1", cBusca.getCliente().getNome());

            ContaAbstrata pBusca = fachada.procurarConta("3");
            assertNotNull(pBusca);
            assertEquals(150, pBusca.getSaldo());
            assertEquals("nomePre2", pBusca.getCliente().getNome());

            ContaAbstrata cbBusca = fachada.procurarConta("4");
            assertNotNull(cbBusca);
            assertEquals(100, cbBusca.getSaldo());
            assertEquals("nomePre2", cbBusca.getCliente().getNome());

        } catch (ClienteExistenteException | ContaExistenteException | ClienteInexistenteException | ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void cadastrarContaExistente(){
        Cliente cli1 = new Cliente("44444","nomePre1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("55555","nomePre2", TipoCliente.CLASS);
        Conta c1 = new Conta("5",100,cli1);
        Poupanca p1 = new Poupanca("5", 150, cli2);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(c1);
            fachada.cadastrar(p1);
        } catch (ClienteExistenteException | ContaExistenteException | ClienteInexistenteException | ClienteInvalidoException e) {
            assertEquals("Conta ja existe!", e.getMessage());
        }
    }

    @Test
    void cadastrarContaClienteInvalido(){
        Conta c1 = new Conta("6",100,null);
        try {
            fachada.cadastrar(c1);
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            assertEquals("Cliente invalido!", e.getMessage());
        }

    }

    @Test
    void atualizarConta(){
        Cliente cli1 = new Cliente("66666","nomePre1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("77777","nomePre2", TipoCliente.CLASS);
        Conta c1 = new Conta("7",100,cli1);
        Conta c2 = new Conta("7",300,cli2);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(c1);

            fachada.atualizar(c2);
            ContaAbstrata conta = fachada.procurarConta("7");
            assertEquals("nomePre2", conta.getCliente().getNome());
            assertEquals(300, conta.getSaldo());

            c1.getCliente().setNome("juca");
            c1.getCliente().setTipo(TipoCliente.VIP);

            ContaAbstrata conta2 = fachada.procurarConta("7");
            assertNotEquals(c1.getCliente().getNome(), conta2.getCliente().getNome());
            assertNotEquals(c1.getCliente().getTipo(), conta2.getCliente().getTipo());
            assertNotEquals(c1.getSaldo(), conta2.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void atualizarContaInexistente(){
        Cliente cli1 = new Cliente("8888","nomePre1", TipoCliente.CLASS);
        Conta c1 = new Conta("0",100,cli1);
        try {
            fachada.atualizar(c1);
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void removerConta(){
        Cliente cli1 = new Cliente("9999","nomePre1", TipoCliente.CLASS);
        Conta c1 = new Conta("8",100,cli1);
        Conta c2 = new Conta("81",100,cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(c1);
            fachada.cadastrar(c2);

            fachada.descadastrarConta("8");
            assertEquals(1, fachada.listarContas().size());
            fachada.descadastrarConta("81");
            assertEquals(0, fachada.listarContas().size());
            assertNull(fachada.procurarConta("8"));
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void removerContaInexistente(){
        try {
            fachada.descadastrarConta("0");
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void listarContas(){
        Cliente cli1 = new Cliente("99900","TestRemover", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("99901","TestRemover", TipoCliente.CLASS);
        Cliente cli3 = new Cliente("99902","TestRemover", TipoCliente.CLASS);
        Cliente cli4 = new Cliente("99903","TestRemover", TipoCliente.CLASS);
        Conta c1 = new Conta("9", 100, cli1);
        Conta c2 = new Conta("10", 100, cli2);
        Conta c3 = new Conta("11", 100, cli3);
        Conta c4 = new Conta("12", 100, cli4);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(cli3);
            fachada.cadastrar(cli4);
            fachada.cadastrar(c1);
            fachada.cadastrar(c2);
            fachada.cadastrar(c3);
            fachada.cadastrar(c4);
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        }
        List<ContaAbstrata> contasList = fachada.listarContas();
        assertNotNull(contasList);
        assertFalse(contasList.isEmpty());
        assertEquals(4, contasList.size());
    }

    @Test
    void testePoupanca(){
        Cliente cli1 = new Cliente("1000","nomePre1", TipoCliente.CLASS);
        Poupanca p1 = new Poupanca("9", 100, cli1);
        Poupanca p2 = new Poupanca("10", cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(p1);
            fachada.cadastrar(p2);
            Double taxa = 4.75;
            Double juros = 100*taxa;
            p1.renderJuros(taxa);
            assertEquals(100+juros, fachada.procurarConta("9").getSaldo());
            assertEquals(0, p2.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testeDebitoContaImposto(){
        Cliente cli1 = new Cliente("2000","nomePre1", TipoCliente.CLASS);
        ContaImposto ci1 = new ContaImposto("11", 100, cli1);
        ContaImposto ci2 = new ContaImposto("12", cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(ci1);
            fachada.cadastrar(ci2);
            ci1.debitar(50);
            double valorDebitado = 50 + (50*0.001);
            assertEquals(100-valorDebitado, fachada.procurarConta("11").getSaldo());

            assertTrue(ci1.getSaldo()>=0);

            assertEquals(0, ci2.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            assertNull(e);
        }
    }

    @Test
    void testeDebitoContaImpostoInsuficiente(){
        Cliente cli1 = new Cliente("3000","nomePre1", TipoCliente.CLASS);
        ContaImposto ci1 = new ContaImposto("13", 20, cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(ci1);
            ci1.debitar(50);
        } catch (ClienteExistenteException | ContaExistenteException | ClienteInexistenteException | ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            assertEquals("Saldo insuficiente!", e.getMessage());
        }
    }

    @Test
    void testeDebitoConta(){
        Cliente cli1 = new Cliente("4000","nomePre1", TipoCliente.CLASS);
        Conta ci1 = new Conta("14", 100, cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(ci1);
            ci1.debitar(25);

            assertEquals(75, fachada.procurarConta("14").getSaldo());
        } catch (ClienteExistenteException | ContaExistenteException | ClienteInexistenteException | ClienteInvalidoException | ContaInexistenteException | SaldoInsuficienteException e) {
            assertNull(e);
        }
    }

    @Test
    void testeDebitoContaInsuficiente(){
        Cliente cli1 = new Cliente("5000","nomePre1", TipoCliente.CLASS);
        Conta ci1 = new Conta("15", 20, cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(ci1);
            ci1.debitar(50);
            assertTrue(ci1.getSaldo() >= 0);
        } catch (ClienteExistenteException | ContaExistenteException | ClienteInexistenteException | ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            assertEquals("Saldo insuficiente!", e.getMessage());
        }
    }

    @Test
    void testeContaBonificada(){
        Cliente cli1 = new Cliente("6000","nomePre1", TipoCliente.CLASS);
        ContaBonificada cb1 = new ContaBonificada("16", cli1);

        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cb1);
            cb1.creditar(100);
            assertEquals(100, cb1.getSaldo());
            cb1.creditar(50);
            assertEquals(150, cb1.getSaldo());
            assertTrue(cb1.getSaldo()>=100);
            assertEquals(150 * 0.01, cb1.getBonus());

            double bonus = cb1.getBonus();
            cb1.renderBonus();
            assertEquals(150+bonus, cb1.getSaldo());
            assertEquals(0, cb1.getBonus());
            assertTrue(cb1.getSaldo()>=100);
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        }
    }

    @Test
    void debitarConta(){
        Cliente cli1 = new Cliente("7000","nomePre1", TipoCliente.CLASS);
        Conta c1 = new Conta("17", 100, cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(c1);
            fachada.debitar("17", 25);
            assertEquals(75,c1.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            assertNull(e);
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void creditarConta(){
        Cliente cli1 = new Cliente("8000","nomePre1", TipoCliente.CLASS);
        Conta c1 = new Conta("18", 100, cli1);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(c1);
            fachada.creditar("18", 25);
            assertEquals(125,c1.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void transferir(){
        Cliente cli1 = new Cliente("9000","nomePre1", TipoCliente.CLASS);
        Cliente cli2 = new Cliente("9009","nomePre2", TipoCliente.CLASS);

        Conta c1 = new Conta("19", 100, cli1);
        Conta c2 = new Conta("20", 200, cli2);
        try {
            fachada.cadastrar(cli1);
            fachada.cadastrar(cli2);
            fachada.cadastrar(c1);
            fachada.cadastrar(c2);
            fachada.transferir("19", "20", 75);

            assertEquals(25, c1.getSaldo());
            assertEquals(275, c2.getSaldo());
        } catch (ClienteExistenteException e) {
            e.printStackTrace();
        } catch (ContaExistenteException e) {
            e.printStackTrace();
        } catch (ClienteInexistenteException e) {
            e.printStackTrace();
        } catch (ClienteInvalidoException e) {
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            e.printStackTrace();
        } catch (ContaInexistenteException e) {
            e.printStackTrace();
        }
    }

}
