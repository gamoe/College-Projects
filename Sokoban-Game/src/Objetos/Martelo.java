package Objetos;

import pt.SokobanGame;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Martelo extends AbstractObject implements ActiveObject{

	public Martelo(Point2D initialPosition) {
		super(initialPosition, "Martelo", 2);
	}
	@Override
	public void interacao(AbstractObject a, Direction d) {
		if(a instanceof Player) {
			SokobanGame.getInstance().removeObject(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			((MoveObjeto)(a)).move(d);
			((Player)a).activeMartelo();
		}
	}
}