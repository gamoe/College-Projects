package Objetos;
import pt.SokobanGame;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class ParedeQuebradica extends AbstractObject implements ActiveObject {

	public ParedeQuebradica(Point2D initialPosition) {
		super(initialPosition, "Parede_Partida", 1);
	}

	@Override
	public void interacao(AbstractObject a, Direction d) {
		if(a instanceof Player) {
			if((boolean)((Player) a).hasMartelo()){
				SokobanGame.getInstance().removeObject(this);
				ImageMatrixGUI.getInstance().removeImage(this);
				((MoveObjeto)(a)).move(d);
			}
		}
	}
}