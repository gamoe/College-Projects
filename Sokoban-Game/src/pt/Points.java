package pt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
public class Points {

	private String nickname;
	private int pontos;

	public Points (String nomeplayer,int pontos ){
		this.nickname=nomeplayer;
		this.pontos=pontos;
	}

	//GETTERS
	public String getNickname() {
		return nickname;
	}

	public int getPontos() {
		return pontos;
	}

	//SETTERS
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	//SETSCORES
	public static void  setScore(int nivel, ArrayList<Points> Escrita) {
		SokobanGame skgame = SokobanGame.getInstance();
		int g =0;

		// Escrita no txt
		try {
			Scanner txt = new Scanner (new File("scores/Level"+ nivel +".txt"));
			String s = "";
			while(txt.hasNext())
				s += txt.next() + System.lineSeparator();
			txt.close();

			int d = skgame.getInstance().getMoves()-1;
			s += (skgame.getNome()+" "+d);
			PrintWriter writer = new PrintWriter(new FileWriter("scores/Level"+nivel+".txt"));
			writer.write(s);
			writer.close();


			Scanner txt2=new Scanner (new File("scores/Level"+nivel+".txt"));
			while(txt2.hasNext())    {
				Escrita.add(new Points(txt2.next(),txt2.nextInt()));
				Escrita.sort(new PointsComparator());
			}
			txt2.close();
			for(Points f : Escrita) {
				if( g == 3d)break;
				System.out.println(f.getNickname() + "-" + f.getPontos());
				g++;
			}
		}
		catch (IOException e) {
		}
	}
}