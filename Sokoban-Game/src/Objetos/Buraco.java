package Objetos;

import pt.SokobanGame;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends AbstractObject implements ActiveObject{

	public Buraco (Point2D initialPosition) {
		super(initialPosition,"Buraco",1);
	}
	@Override
	public void interacao(AbstractObject a, Direction d) {
		if(a.isDeletable()) {
			SokobanGame.getInstance().reset();
		}
		if(a instanceof SmallStone) {
			SokobanGame.getInstance().removeObject(a);
			ImageMatrixGUI.getInstance().removeImage(a);
			SokobanGame.getInstance().getPlayer().move(d);
			((MoveObjeto)(a)).move(d);
		}
		if(a instanceof BigStone) {
			SokobanGame.getInstance().removeObject(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			SokobanGame.getInstance().getPlayer().move(d);
			((MoveObjeto)(a)).move(d);
			((BigStone)a).Stuck();
		}
	}
}