package com.ufes.autenticacao.atvcomplementar.autenticacao;

import com.ufes.autenticacao.atvcomplementar.autenticacao.model.ClienteBuilder;
import com.ufes.autenticacao.atvcomplementar.autenticacao.model.ContaCorrenteProxy;
import com.ufes.autenticacao.atvcomplementar.autenticacao.model.Diretor;
import com.ufes.autenticacao.atvcomplementar.autenticacao.model.GerenteDeBancoBuilder;
import com.ufes.autenticacao.atvcomplementar.autenticacao.model.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Valmir Aguiar, Alexandre Breda
 */
public class Main {

    public static void main(String[] args) {
        Aplicacao aplicacao = new Aplicacao();

        try {
            ClienteBuilder clienteBuilder = new ClienteBuilder("Valmir Aguiar", "valmiraguiar", "1234");
            GerenteDeBancoBuilder gerenteBuilder = new GerenteDeBancoBuilder("Alexandre Breda", "alexandrebreda", "1234");

            Diretor diretor = new Diretor();
            diretor.criarCliente(clienteBuilder);
            Usuario cliente = clienteBuilder.getResultado();

            diretor.criarGerenteDeBanco(gerenteBuilder);
            Usuario gerente = gerenteBuilder.getResultado();

            ContaCorrenteProxy conta = new ContaCorrenteProxy("11111", 0.0, cliente);

            aplicacao.abrirConta(conta);

            ClienteBuilder cliente2Builder = new ClienteBuilder("Joao Aguiar", "joaoaguiar", "1234");
            diretor.criarCliente(cliente2Builder);
            Usuario cliente2 = clienteBuilder.getResultado();
            ContaCorrenteProxy conta2 = new ContaCorrenteProxy("21111", 0.0, cliente2);
            aplicacao.abrirConta(conta2);

            aplicacao.addUsuario(gerente);
            aplicacao.addUsuario(cliente);
            aplicacao.addUsuario(cliente2);

            ArrayList<ContaCorrenteProxy> contas = aplicacao.getContasCadastradas();
            ArrayList<Usuario> usuarios = aplicacao.getUsuarios();

            System.out.println("========== CONTAS ============");
            for (ContaCorrenteProxy contaCorrente : contas) {
                System.out.println("++++++++++++++\n" + contaCorrente.toString() + "\n++++++++++++++++++++++");
            }

            System.out.println("========== USUARIOS ============");
            for (Usuario usuarioCadastrado : usuarios) {
                System.out.println("++++++++++++++\n" + usuarioCadastrado.toString() + "\n++++++++++++++++++++++");
            }

            // ACOES DO GERENTE
            ContaCorrenteProxy contaAlterada = aplicacao.getConta("11111");
            System.out.println("Desativar e Ativar conta");
            contaAlterada.setGerente(gerente);
            contaAlterada.desativar();
            contaAlterada.ativar();
            
            
            
            
            System.out.println("Sacar");
            System.out.println("SALDO: R$"+contaAlterada.getSaldo());
            
            contaAlterada.depositar(10000.0);
            System.out.println("SALDO: R$"+contaAlterada.getSaldo());
            contaAlterada.sacar(2.0);
            contaAlterada.depositar(200.0);
            System.out.println("SALDO: R$"+contaAlterada.getSaldo());
            
            
            System.out.println("Desativar como cliente");
            contaAlterada.setGerente(cliente);
            contaAlterada.desativar();
            
            
        } catch (Exception e) {
            System.out.println("Error -> "+e.getMessage());
        }

    }
}
