package Objetos;

import pt.SokobanGame;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Gelo extends AbstractObject implements ActiveObject{

	public Gelo(Point2D initialPosition) {
		super(initialPosition, "Gelo", 0);

	}

	@Override
	public void interacao(AbstractObject a, Direction d) {


		Point2D gelonext=this.getPosition().plus(d.asVector());

		Point2D lastgelo=this.getPosition();


		while(SokobanGame.getInstance().getObject(lastgelo.plus(d.asVector())) instanceof Gelo) 

			lastgelo=lastgelo.plus(d.asVector());

		gelonext=lastgelo.plus(d.asVector());

		AbstractObject f=SokobanGame.getInstance().getObject(gelonext);


		if(f instanceof MoveObjeto ||f instanceof Estatico) {
			if(!(a instanceof Player))
				SokobanGame.getInstance().getPlayer().move(d);
			((MoveObjeto)(a)).move(d);
			a.setPosition(lastgelo);


		}
		if(f==null) {
			if(!(a instanceof Player))
				SokobanGame.getInstance().getPlayer().move(d);;
				((MoveObjeto)(a)).move(d);

				a.setPosition(gelonext);

		}
		if(f instanceof ActiveObject) {
			a.setPosition(lastgelo);

			((ActiveObject)(f)).interacao(a, d);
		}
	}
}
