import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaParam;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Jogador {

    private int idJogador;
    private String nome;
    private int pontuacao;
    private String senha;

    private static ArrayList<Jogador> listaJogadores = new ArrayList<Jogador>();
    private static Jogador jogadorAtual = new Jogador();

    public Jogador(){}

    public Jogador(int idJogador, String nome, int pontuacao, String senha){

        this.idJogador = idJogador;
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.senha = senha;

    }

    public static boolean verificarNickJaExiste(String nick) throws SQLException {

        String VerificarNickSql = "SELECT nome FROM jogadores WHERE nome = '" +
                nick + "';";
        ResultSet resultado = ConexaoBD.buscar(VerificarNickSql);
        listaJogadores = new ArrayList<Jogador>();

        while(resultado.next()){
            Jogador jogadorEncontrado = new Jogador();
            jogadorEncontrado.nome = resultado.getString("nome");
            listaJogadores.add(jogadorEncontrado);
        }

        return listaJogadores.size() != 0;

        /*if(listaJogadores.size() == 0){
            return false;
        } else {
            return true;
        } */

    }

    public static boolean verificarSenha(String nick, String senha) throws SQLException{

        String VerificarSql = "SELECT senha FROM jogadores WHERE nome = '" +
                nick + "';";

        ResultSet resultado = ConexaoBD.buscar(VerificarSql);
        boolean senhaCorreta = false;

        while(resultado.next()) {
            String jogadorSenha = resultado.getString("senha");
            senhaCorreta = senha.equals(jogadorSenha);
        }

        return senhaCorreta;

    }

    public static int getIdJogador(String nome) throws SQLException {

        String pegarIdJogador = "SELECT idJogador FROM jogadores WHERE nome = '" +
                nome + "';";
        ResultSet resultado = ConexaoBD.buscar(pegarIdJogador);
        int idJogador = 0;

        while(resultado.next()){
            idJogador = resultado.getInt("idJogador");
        }

        return idJogador;

    }

    public static int getPontuacao(int idJogador) throws SQLException {

        String pegarPontuacao = "SELECT pontuacao FROM jogadores WHERE idJogador = " +
                idJogador + ";";
        ResultSet resultado = ConexaoBD.buscar(pegarPontuacao);
        int pontuacao = 0;

        while(resultado.next()){
            pontuacao = resultado.getInt("pontuacao");
        }

        return pontuacao;

    }

    public static void setJogadorAtual(int idJogador, String nome, int pontuacao, String senha) throws SQLException {
        jogadorAtual.idJogador = idJogador;
        jogadorAtual.nome = nome;
        jogadorAtual.pontuacao = pontuacao;
        jogadorAtual.senha = senha;
    }

    public static void resetJogadorAtual(){
        jogadorAtual = new Jogador();
    }

    public static void mostrarPerfil(Scanner entrada) throws SQLException{

        int opcoesDePerfil = 0;

        while (opcoesDePerfil != 3){

            System.out.println(" -PERFIL DE JOGADOR: \n" +
                    "Jogador: " + jogadorAtual.nome + "\n" +
                    "Recorde Pessoal: " + jogadorAtual.pontuacao + "\n\n" +
                    "Opçoes de perfil: \n1.Atualizar Perfil \n2.Deletar Perfil \n3.Voltar \n");

            while (!entrada.hasNextInt()){
                System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");

                entrada.nextLine();
            }

            opcoesDePerfil = entrada.nextInt();

            switch (opcoesDePerfil){

                case 1:

                    System.out.println("Qual informação deseja atualizar: \n" +
                            "1.Nome \n2.Senha \n3.Cancelar");

                    while (!entrada.hasNextInt()) {
                        System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");

                        entrada.nextLine();
                    }

                    int opcoesAtualizar = entrada.nextInt();

                    String novoNome = "";
                    String novaSenha = "";

                    switch (opcoesAtualizar){

                        case 1:
                            System.out.println("Informe o seu novo nome: ");
                            entrada.skip("[\r\n]");
                            novoNome = entrada.nextLine();
                            break;

                        case 2:
                            System.out.println("Informe a sua nova senha: ");
                            entrada.skip("[\r\n]");
                            novaSenha = entrada.nextLine();
                            break;

                        default:
                            System.out.println("Opção inválida! \n");
                            break;

                    }

                    editarPerfil(novoNome,novaSenha);

                    break;

                case 2:

                    int confirmarExclusao = 0;

                    System.out.println("Você tem certeza que deseja deletar seu perfil? \n" +
                            "1.Cancelar \n2.Sim");

                    while (!entrada.hasNextInt()){
                        System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");

                        entrada.nextLine();
                    }

                    confirmarExclusao = entrada.nextInt();

                    switch (confirmarExclusao){

                        case 1:
                            break;

                        case 2:
                            System.out.println("Deletando... \n");
                            deletarPerfil();
                            resetJogadorAtual();
                            return;

                        default:
                            System.out.println("Opção inválida! \n");
                            break;

                    }

                    break;

                case 3:
                    System.out.println("Voltando para o menu...");
                    break;

                default:
                    System.out.println("Opção inválida! \n");
                    break;
            }

        }
    }

    public static void deletarPerfil(){

        String deletarPefilSql = "DELETE FROM jogadores WHERE idJogador = "+
                jogadorAtual.idJogador + ";";
        boolean perfilExcluido = ConexaoBD.deletar(deletarPefilSql);

        if(perfilExcluido){
            System.out.println("Perfil deletado com sucesso! \n");
        } else {
            System.out.println("Falha ao deletar perfil. \n");
        }

    }

    public static boolean voltarMenuLogin(){
        Jogador comparacao = new Jogador();

        return jogadorAtual.idJogador == comparacao.idJogador &&
                jogadorAtual.nome.equals(comparacao.nome) &&
                jogadorAtual.pontuacao == comparacao.pontuacao &&
                jogadorAtual.senha.equals(comparacao.senha);

    }

    public static void editarPerfil(String novoNome, String novaSenha) throws SQLException{

        if(!novoNome.isEmpty()){
            String atualizarNomeSQL = "UPDATE jogadores SET nome = '" +
                    novoNome + "' WHERE idJogador = " + jogadorAtual.idJogador + ";";
            boolean nomeAlterado = ConexaoBD.atualizar(atualizarNomeSQL);
            jogadorAtual.nome = novoNome;

        } else {
            String atualizarSenhaSql = "UPDATE jogadores SET senha = '" +
                    novaSenha + "' WHERE idJogador = " + jogadorAtual.idJogador + ";";
            boolean senhaAlterada = ConexaoBD.atualizar(atualizarSenhaSql);
            jogadorAtual.senha = novaSenha;
        }
    }

}
