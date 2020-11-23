package Objetos;
import pt.SokobanGame;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Alvo extends AbstractObject implements ActiveObject  {

	public Alvo (Point2D initialPosition) {
		super(initialPosition,"Alvo",0);
	}

	public void interacao(AbstractObject a, Direction d) {
		Point2D next =a.getPosition().plus(d.asVector());
		if(SokobanGame.getInstance().getObject(a.getPosition().plus(d.asVector())) instanceof MoveObjeto) {
			((MoveObjeto)(SokobanGame.getInstance().getObject(next))).interacao(this, d);
			if(this.getPosition().equals(next))
				((MoveObjeto)(a)).move(d);
		}
	}
}


















