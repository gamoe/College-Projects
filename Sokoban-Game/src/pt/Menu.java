package pt;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Menu {
	
	//NAME
	public static String name(){
		String NickGirls = JOptionPane.showInputDialog("Introduza o nome");
		if (NickGirls.equals("")){NickGirls = "Desconhecido";}
		return NickGirls;
	}
	//INICIO DO JOGO
	public static void welcome() {
		JOptionPane.showMessageDialog(null,"Bem-vindo ao SokobanGame!" + " " + "Para prosseguir de nível "
				+ "coloca todas as caixas nos respetivos alvos e tenta fazer o menor número de moves possíveis "
				+ "para tentares chegar ao TOP 3 SCORES." + " " + "Diverte-te :)" , "Welcome" , JOptionPane.INFORMATION_MESSAGE);
	}
	
	//CRÉDITOS
	public static void credits() {
		JOptionPane.showMessageDialog(null,"Jogo feito por: "+"Hugo Santos"+" e "+"Gabriel","Créditos", JOptionPane.INFORMATION_MESSAGE);
	}

	//GET SCORES
	public static void getScores(int n) {
		System.out.println("Top 3 Scores");
		Points.setScore(n,new ArrayList<Points>());

	}
}

