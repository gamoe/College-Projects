package pt; 
import java.util.Comparator;

public class PointsComparator implements Comparator<Points> {

	public int compare(Points a, Points b) {
		return a.getPontos() - b.getPontos();
	}
}