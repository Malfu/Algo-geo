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
			if (p1.y > p2.y) return 1; // si ils sont aligné on prend le plus loin en 1er
			else if (p1.y == p2.y) return 0; // si même angle et même y c'est que le x est egal lui aussi
											// donc même point, donc ça arrivera jamais trololololo
		}
		return -1;
	}

}
