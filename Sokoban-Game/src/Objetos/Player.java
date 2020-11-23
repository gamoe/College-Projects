package Objetos;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player extends MoveObjeto{

	private int martelo=0;

	public Player (Point2D initialPosition) {
		super(initialPosition,"Empilhadora_U",2);
	}

	public void activeMartelo() {
		martelo=1;
	}
	
	@Override
	public boolean isDeletable() {
		return true;
	}

	@Override
	public void move(Direction dir) {
		Point2D newPosition = getPosition().plus(dir.asVector());
		super.setPosition(newPosition);
		super.setImageName("Empilhadora_"+dir.name().charAt(0));
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void interacao(AbstractObject a, Direction d) {
	}

	public boolean hasMartelo() {
		return martelo == 1;
	}
}
