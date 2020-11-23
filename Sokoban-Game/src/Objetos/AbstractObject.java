package Objetos;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class AbstractObject implements ImageTile {

	//Atributos
	private Point2D position;
	private String imageName;
	private int level;

	//Construtor

	public AbstractObject(Point2D initialPosition,String imageName, int level) {
		this.position = initialPosition;
		this.imageName = imageName;
		this.level = level;
	}
	@Override
	//Getters
	public Point2D getPosition() {
		return position;
	}

	public String getName() {
		return imageName;
	}

	public int getLayer() {
		return level;
	}

	//Setters

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	//SE PODE SER DELETED NO BURACO
	public boolean isDeletable() {
		return false;
	}
}