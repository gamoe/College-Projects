package Objetos;
import pt.SokobanGame;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bateria extends AbstractObject implements ActiveObject{

	public Bateria (Point2D initialPosition) {
		super(initialPosition,"Bateria",1);
	}

	@Override
	public void interacao(AbstractObject a, Direction d) {
		if(a instanceof Player) {
			SokobanGame.getInstance().setEnergia(100);
			SokobanGame.getInstance().removeObject(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			((MoveObjeto)(a)).move(d);
		}
	}
}