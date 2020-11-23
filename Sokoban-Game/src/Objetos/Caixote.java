package Objetos;
import pt.SokobanGame;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Caixote extends MoveObjeto{
	public Caixote (Point2D initialPosition) {
		super(initialPosition,"Caixote",2);
	}
	
	@Override
	public boolean isDeletable() {
		return true;
	}

	@Override
	public void interacao( AbstractObject a, Direction d) {

		Point2D next = super.getPosition().plus(d.asVector());
		Point2D oldnext = super.getPosition();
		if(SokobanGame.getInstance().getObject(next)==null) {
			this.move(d);

			if(SokobanGame.getInstance().getActive(oldnext)!= null)
				((ActiveObject)(SokobanGame.getInstance().getActive(oldnext))).interacao(a, d);

			else {
				((MoveObjeto)(a)).move(d);
			}
		}
		else {

			if(SokobanGame.getInstance().getObject(this.getPosition().plus(d.asVector())) instanceof ActiveObject) {
				((ActiveObject)(SokobanGame.getInstance().getObject(this.getPosition().plus(d.asVector())))).interacao(this, d);
			}
		}
	}
}