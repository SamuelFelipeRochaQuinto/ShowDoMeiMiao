import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Questoes {

    private int idQuestao;
    private String enunciado;
    private String categoria;
    private int idOpcao;

    private static ArrayList<Questoes> listaQuestoes = new ArrayList<Questoes>();

    public Questoes(){}

    public Questoes(String enunciado, String categoria, int idOpcao){

        this.enunciado = enunciado;
        this.categoria = categoria;
        this.idOpcao = idOpcao;

    }

    public static int questoesDisponiveis() throws SQLException {

        String questoesDisponiveiSql = "SELECT * FROM questoes;";
        ResultSet resultado = ConexaoBD.buscar(questoesDisponiveiSql);
        ArrayList<Integer> questoesDisponiveis = new ArrayList<Integer>();

        while(resultado.next()){
            int questaoEncontrada = resultado.getInt("idQuestao");
            questoesDisponiveis.add(questaoEncontrada);
        }

        return questoesDisponiveis.size();

    }

    public static String pegarEnunciado(int idQuestao) throws SQLException {

        String pegarEnunciadoSql = "SELECT enunciado FROM questoes WHERE idQuestao = " +
                idQuestao + ";";
        ResultSet resultado = ConexaoBD.buscar(pegarEnunciadoSql);
        String enunciado = "";

        while(resultado.next()){
            enunciado = resultado.getString("enunciado");
        }

        return enunciado;

    }

    /*public static boolean verificarResposta(int idQuestao,int idOpcao) throws SQLException {

        String verificarRespostaSql = "SELECT idOpcao FROM questoes WHERE idQuestao = " +
                idQuestao + ";";
        ResultSet resultado = ConexaoBD.buscar(verificarRespostaSql);
        int opcaoEscolhida = 0;

        while(resultado.next()){
            opcaoEscolhida = resultado.getInt("idOpcao");
        }

        return opcaoEscolhida == idOpcao;

    } */

}
