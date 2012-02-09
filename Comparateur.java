package tp_geometrie1;
import java.util.Comparator;

public class Comparateur implements Comparator {
	
	public Point BasGauche;
	Comparateur(Point BasGauche)
	{
		this.BasGauche = BasGauche;
	}
	  
	public int compare(Object arg1, Object arg2) 
	{
		Point p1 = (Point) arg1;
		Point p2 = (Point) arg2;
		double angle = Algorithmes.angle(BasGauche, p1, new Point(0,0));
		double angle2 = Algorithmes.angle(BasGauche, p2, new Point(0,0));
		
		if (angle > angle2) return 1;
		else if(angle == angle2)
		{
			if (p1.y > p2.y) return 1; // si ils sont align� on prend le plus loin en 1er
			else if (p1.y == p2.y) return 0; // si m�me angle et m�me y c'est que le x est egal lui aussi
											// donc m�me point, donc �a arrivera jamais trololololo
		}
		return -1;
	}

}
