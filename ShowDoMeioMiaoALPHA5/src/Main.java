import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        Scanner entrada = new Scanner(System.in);

        while(true) {

            System.out.println("BEM VINDO AO JOGO DO MEI MIÃO! \n1.Login \n2.Registrar \n3.Sair");

            while(!entrada.hasNextInt()){ //verificando se o valor digitado é um número inteiro

                System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");

                entrada.nextLine();

            }

            int escolha = entrada.nextInt();

            switch(escolha){

                case 1: //login
                    entrada.skip("[\r\n]");

                    System.out.print("\nInforme seu nick: ");
                    String nick = entrada.nextLine();

                    System.out.print("Informe sua senha: ");
                    String senha = entrada.nextLine();

                    if(Jogador.verificarNickJaExiste(nick) && Jogador.verificarSenha(nick,senha)){

                        System.out.println("Login realizado com sucesso!\n");
                        int idJogador = Jogador.getIdJogador(nick);
                        int pontuacao = Jogador.getPontuacao(idJogador);
                        Jogador.setJogadorAtual(idJogador,nick,pontuacao,senha);
                        MenuInicial.menu();

                    } else {

                        System.out.println("Usuário e/ou senha incorretos! \nTente novamente!\n");
                    }

                    break;

                case 2: //registrar
                    entrada.skip("[\r\n]");

                    System.out.print("\nEscolha seu nick: ");
                    String novoNick = entrada.nextLine();

                    System.out.print("\nEscolha sua senha: ");
                    String novaSenha = entrada.nextLine();

                    if(!Jogador.verificarNickJaExiste(novoNick) && novaSenha.length()>0){

                        System.out.println("Registrado com sucesso!\n");
                        //mandar nick e senha para o banco de dados
                        String registroSql = "INSERT INTO jogadores (nome,pontuacao,senha) " +
                                "VALUES ('" + novoNick + "'," + 0 + ",'" + novaSenha +"');";

                        boolean salvo = ConexaoBD.salvar(registroSql);

                        if(salvo){
                            System.out.println("\nRegistrado com sucesso!\n");
                        } else {
                            System.out.println("\nFalha ao registrar!\n");
                        }

                    } else {
                        System.out.println("Nick e/ou Senha inválido(s)! \n");
                    }

                    break;

                case 3:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida! \nTente novamente.\n");
                    break;

            }
        }

    }
}