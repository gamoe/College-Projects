package pt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import Objetos.AbstractObject;
import Objetos.ActiveObject;
import Objetos.Alvo;
import Objetos.Bateria;
import Objetos.BigStone;
import Objetos.Buraco;
import Objetos.Caixote;
import Objetos.Chao;
import Objetos.Gelo;
import Objetos.Martelo;
import Objetos.MoveObjeto;
import Objetos.Parede;
import Objetos.ParedeQuebradica;
import Objetos.Player;
import Objetos.SmallStone;
import Objetos.Teleport;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGame implements Observer {
	//ATRIBUTOS
	private Player player; 
	private ArrayList<AbstractObject>all=new ArrayList<>();
	private ArrayList<ImageTile>tiles= new ArrayList<>();
	private ArrayList<File>levels= new ArrayList<>();
	private File levelatual;
	private Iterator<File> it;
	private int Energia = 100;
	private String nome;
	private int level = 0;
	private int Moves = 0;
	private static SokobanGame INSTANCE = null;

	//CONSTRUCTOR
	private SokobanGame(){
		Menu.welcome();
		nome=Menu.name();
		File file =new File ("levels");

		for(File file1:file.listFiles())
			levels.add(file1);
		it=levels.iterator();
		levelatual=it.next();
		factory(levelatual);
		ImageMatrixGUI.getInstance().addImages(tiles);
		ImageMatrixGUI.getInstance().update();
		setMenu();
	}

	//BUILD FLOOR LEVEL (TILES)
	private ArrayList<ImageTile> buildSampleLevel(){
		ArrayList<ImageTile> sampleLevelTiles = new ArrayList<ImageTile>();
		// Build 10x10 floor tiles
		for (int y=0; y!=10; y++)
			for (int x=0; x!=10 ; x++)
				sampleLevelTiles.add(new Chao(new Point2D(x,y)));

		return sampleLevelTiles;	
	}

	//UPDATE
	@Override
	public void update(Observed arg0) {
		Point2D playerpoint=player.getPosition();

		int lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();
		if(lastKeyPressed == 82){
			reset();
			setMenu();
			ImageMatrixGUI.getInstance().update();

		}
		if(Direction.isDirection(lastKeyPressed)) {
			Direction dir=Direction.directionFor(lastKeyPressed);

			if (player != null){
				if(getObject(player.getPosition().plus(dir.asVector()))==null)
					player.move(dir);
				else {
					if(getObject(playerpoint.plus(dir.asVector())) instanceof MoveObjeto) {


						((MoveObjeto)(getObject(playerpoint.plus(dir.asVector())))).interacao(player, dir);
					}

					if(getObject(playerpoint.plus(dir.asVector())) instanceof ActiveObject) {

						if(getMovable(playerpoint.plus(dir.asVector()))==null)

							((ActiveObject)(getActive(playerpoint.plus(dir.asVector())))).interacao(player, dir);

						else 
							((MoveObjeto)(getMovable(playerpoint.plus(dir.asVector())))).interacao(player, dir);
					}
				}
			}
			if(playerpoint!=player.getPosition())
				setMenu();
			//NEXT LEVEL APLICAÇÃO
			if(nextLevelAux()) {
				nextLevel();
			}
		}
	}

	//SCAN DO LEVEL
	public void factory(File file) {
		levelatual=file;
		tiles = buildSampleLevel();	
		try {
			Scanner scanner =new Scanner (file);
			for(int y=0;y<10;y++) {
				if(scanner.hasNextLine()) {
					String s=scanner.nextLine();
					for(int x =0;x<10;x++) {
						switch(s.charAt(x)) {
						case '#':all.add(new Parede(new Point2D(x,y)));
						break;
						case 'X':all.add(new Alvo (new Point2D(x,y)));
						break;
						case 'C':all.add(new Caixote(new Point2D(x,y)));
						break;
						case 'E':this.player=new Player(new Point2D(x,y)) ;  
						all.add(player);
						break;
						case 'b':all.add(new Bateria(new Point2D(x,y)));
						break;
						case 'O':all.add(new Buraco(new Point2D(x,y)));
						break;
						case 'P':all.add(new BigStone(new Point2D(x,y)));
						break;
						case 'p':all.add(new SmallStone(new Point2D(x,y)));
						break;
						case 'm':all.add(new Martelo(new Point2D(x,y)));
						break;
						case '%':all.add(new ParedeQuebradica(new Point2D(x,y)));
						break;
						case 't':all.add(new Teleport(new Point2D(x,y)));
						break;
						case 'g':all.add(new Gelo(new Point2D(x,y)));
						break;
						}
					}
				}
			}
			scanner.close();
		}
		catch(FileNotFoundException e) {};
		tiles.addAll(all);
		ImageMatrixGUI.getInstance().addImages(tiles);
	}

	// OBTER A INSTANCE
	public static SokobanGame getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SokobanGame();
		}
		return INSTANCE;
	}

	//OBTER OBJETO NUMA DETERMINADA POSIÇÃO
	public  AbstractObject getObject (Point2D point) {
		for(AbstractObject f: all ) {
			if ( f.getPosition().equals(point)&&  !(f instanceof  Alvo) ) 
				return f;
		}
		return null;
	}

	//OBTER MOVES
	public int getMoves() {
		return Moves++;
	}
	
	// SET MOVES
	public void setMoves(int moves) {
		Moves = moves;
	}

	// OBTER O LEVEL
	public int getLevel() {
		return level;
	}

	// SET O LEVEL
	public void setLevel(int level) {
		this.level = level;
	}

	//GET ARRAYLIST WITH ALL THE OBJECTS
	public ArrayList<AbstractObject> getAll() {
		return all;
	}

	//GET PLAYER
	public Player getPlayer() {
		return player;
	}

	//OBTER ENERGIA
	public int getEnergia() {
		if(Energia<=0) {
			reset();
		}
		return Energia--;
	}

	// SET ENERGIA
	public void setEnergia(int energia) {
		Energia = energia;
	}
	//GET PLAYER'S NAME
	public String getNome() {
		return nome;
	}

	// RODAPÉ
	public void setMenu() {
		ImageMatrixGUI.getInstance().setStatusMessage( "Nivel:" + getLevel() + "  | " + "Moves:" + getMoves() + "  |  "
				+ "Energia: " + getEnergia() + "%" + "  |  " + "Nickname:" + "  " + getNome() + " |  Restart: Press R");
		ImageMatrixGUI.getInstance().update();
	}

	//RESET
	public void reset () {
		ImageMatrixGUI.getInstance().removeImages(tiles);
		tiles.clear();
		all.clear();
		factory(levelatual);
		setEnergia(100);
		setMoves(0);
		ImageMatrixGUI.getInstance().update();
	}

	//REMOVEOBJECT
	public void removeObject(AbstractObject obj) {
		all.remove(obj);
		ImageMatrixGUI.getInstance().removeImage(obj);
	}

	//CHECK IF CAIXOTES ARE AT THE SAME POSITION THAN ALVOS
	public boolean nextLevelAux() {
		int counter=0;
		ArrayList <AbstractObject> alvos = new ArrayList<>();
		ArrayList <AbstractObject> caixotes = new ArrayList<>();
		for( AbstractObject f: all) {
			if(f instanceof Alvo) {
				alvos.add(f);
			}
			if( f instanceof Caixote ) {
				caixotes.add(f);
			}
		}
		for( AbstractObject i : caixotes) {
			for( AbstractObject j : alvos) {
				if( j.getPosition().equals(i.getPosition())) {
					counter++;
				}
			}
		}
		if (counter == alvos.size()) {
			return true;
		}
		return false;
	}

	//NEXT LEVEL
	public void nextLevel() {
		ImageMatrixGUI.getInstance().clearImages();
		all.clear();
		tiles.clear();
		if((it.hasNext())){
			levelatual=it.next();
			factory(levelatual);
		}
		else {

			ImageMatrixGUI.getInstance().dispose();
			Menu.credits();
		}
		Menu.getScores(getLevel());
		setEnergia(100);
		setLevel(level+1);
		setMoves(0);
		setMenu();
	}

	//GET MOVABLE
	public  AbstractObject getMovable (Point2D point) {
		for(AbstractObject f: all ) {
			if ( f.getPosition().equals(point)&&  f instanceof MoveObjeto && !(f instanceof  Alvo))

				return f;
		}
		return null;
	}
	//GET ACTIVE
	public  AbstractObject getActive (Point2D point) {
		for(AbstractObject f: all ) {
			if ( f.getPosition().equals(point)&&  f instanceof ActiveObject && !(f instanceof  Alvo)) 
				return f;
		}
		return null;
	}
}