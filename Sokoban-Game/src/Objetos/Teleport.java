package Objetos;

import pt.SokobanGame;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Teleport extends AbstractObject implements ActiveObject {

	public Teleport(Point2D initialPosition) {
		super(initialPosition, "Portal_Azul", 1);
	}

	@Override
	public void interacao(AbstractObject a, Direction d) {
		Point2D entrada= new Point2D(0,0);
		AbstractObject obj = null;
		for( AbstractObject f : SokobanGame.getInstance().getAll()) {
			if( f instanceof Teleport && !(f.getPosition().equals(super.getPosition())) ) {
				entrada=f.getPosition();
				for(AbstractObject w : SokobanGame.getInstance().getAll()) 
					if(w instanceof MoveObjeto && (w.getPosition().equals(entrada))) 
						obj=w;
				if(obj==null) {
					((MoveObjeto)(a)).move(d);
					SokobanGame.getInstance().getPlayer().move(d);
					a.setPosition(entrada);
				}
			}
		}
	}
}