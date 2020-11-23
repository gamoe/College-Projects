package Objetos;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class MoveObjeto extends AbstractObject {

	public MoveObjeto(Point2D initialPosition, String imageName, int level) {
		super(initialPosition, imageName, level);
	}
	
	//Função que move
	public void move(Direction Dir) {
		Point2D newPosition = getPosition().plus(Dir.asVector());
		setPosition(newPosition);
		ImageMatrixGUI.getInstance().update();
	}

	public abstract void interacao(AbstractObject a, Direction d);
}