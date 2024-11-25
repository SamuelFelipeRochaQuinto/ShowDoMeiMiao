import java.sql.SQLException;
import java.util.Scanner;

public class MenuInicial {

    private int escolha;

    public MenuInicial(){}

    public MenuInicial(int escolha){

        this.escolha = escolha;

    }

    public static void menu() throws SQLException {

        Scanner entrada = new Scanner(System.in);
        int escolha = 0;

        while(escolha != 4) {

            if(Jogador.voltarMenuLogin()){
                return;
            }

            System.out.println("BEM VINDO AO SHOW DO MEI MIÃO! \nEscolha o que deseja fazer:" +
                    "\n1.Jogar \n2.Ver recordes \n3.Seu perfil \n4.Voltar");

            while(!entrada.hasNextInt()){

                System.out.println("\nVocê digitou um valor inválido. Tente novamente.\n");

                entrada.nextLine();

            }

            escolha = entrada.nextInt();

            switch (escolha){

                case 1:
                    //iniciar jogo
                    Jogo.Partida(entrada);
                    break;

                case 2:
                    //mostrar recordes
                    break;

                case 3:
                    //mostrar perfil de jogador
                    Jogador.mostrarPerfil(entrada);
                    break;

                case 4:
                    System.out.println("Voltando...\n");
                    Jogador.resetJogadorAtual();
                    break;

                default:
                    System.out.println("\nOpção inválida! \nTente novamente.\n");
                    break;

            }
        }

    }
}