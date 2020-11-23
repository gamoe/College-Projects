package Objetos;
import pt.SokobanGame;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SmallStone extends MoveObjeto{

	public SmallStone(Point2D initialPosition) {
		super(initialPosition, "SmallStone", 2);
	}

	@Override
	public void interacao(AbstractObject a, Direction d) {

		Point2D next = super.getPosition().plus(d.asVector());

		Point2D oldnext = super.getPosition();
		if(SokobanGame.getInstance().getObject(next)==null) {
			this.move(d);

			if(SokobanGame.getInstance().getActive(oldnext)!= null)
				((ActiveObject)(SokobanGame.getInstance().getActive(oldnext))).interacao(a, d);

			else {
				((MoveObjeto)(a)).move(d);
			}}
		else {
			if(SokobanGame.getInstance().getObject(next) instanceof ActiveObject) {
				((ActiveObject)(SokobanGame.getInstance().getObject(next))).interacao(this, d);
			}
		}

	}
}