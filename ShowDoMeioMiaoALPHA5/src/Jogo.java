import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Jogo {

    private int idJogo;
    private int idQuestao;
    private int idOpcao;
    private int idJogador;

    public Jogo(){}

    public Jogo (int idQuestao, int idOpcao, int idJogador){

        this.idQuestao = idQuestao;
        this.idOpcao = idOpcao;
        this.idJogador = idJogador;

    }

    public static void Partida( Scanner entrada ) throws SQLException {

        Random gerador = new Random();
        ArrayList<Integer> questoesPassadas = new ArrayList<Integer>();
        int estagio = 1;
        boolean remocao5050 = false;
        boolean perdeu = false;

        while (!perdeu) {

            int questao = gerador.nextInt(Questoes.questoesDisponiveis()) + 1; //escolher uma questão aleatória

            //SISTEMA PARA NÃO REPETIR PERGUNTA NUMA MESMA RUN
            if(estagio > 1) {
                for (int i = 0; i < questoesPassadas.size(); i++) {

                    while (questoesPassadas.get(i) == questao) {
                        questao = gerador.nextInt(Questoes.questoesDisponiveis()) + 1;
                    }

                }
            }

            System.out.println(Questoes.pegarEnunciado(questao) +
                    "\n\nAlternativas: \n");
            ArrayList<OpcoesDeResposta> opcoes = new ArrayList<OpcoesDeResposta>();
            opcoes = OpcoesDeResposta.pegarOpcoes(questao);

            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println((i + 1) + ". " + OpcoesDeResposta.getOpcao(opcoes, i));
            }

            boolean pergunta = true;

            while(pergunta) {

                int resposta = 0;

                while (!entrada.hasNextInt()) {

                    System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");
                    entrada.nextLine();

                }

                resposta = entrada.nextInt();

                if (resposta > 0 && resposta <= 4) {

                    System.out.print("E a resposta está......");

                    int idRespostaEscolhida = OpcoesDeResposta.getIdRespostaEscolhida(opcoes, (resposta - 1));

                    if (OpcoesDeResposta.verificarResposta(questao, idRespostaEscolhida)) {
                        System.out.println(" CORRETA!");
                        estagio++;
                        questoesPassadas.add(questao);
                        pergunta = false;

                    } else {
                        System.out.println(" errada...");
                        perdeu = true;
                        pergunta = false;
                    }
                } else {
                    switch (resposta) {

                        case 5:

                            if(!remocao5050){
                                opcoes = OpcoesDeResposta.eliminacao5050(questao, opcoes);
                                remocao5050 = true;
                            } else {
                                System.out.println("Ajuda indisponível. \n");
                            }

                            System.out.println(Questoes.pegarEnunciado(questao) +
                                    "\n\nAlternativas: \n");
                            for(int i = 0; i < opcoes.size(); i++) {
                                System.out.println((i + 1) + ". " + OpcoesDeResposta.getOpcao(opcoes, i));
                            }

                            break;

                        default:
                            System.out.println("\nOpção inválida! \n");
                            break;

                    }
                }
            }
        }
    }
}
