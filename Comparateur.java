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
		double a = Math.sqrt(Math.pow(BasGauche.x,2) + Math.pow(BasGauche.y,2));
		double b = Math.sqrt(Math.pow(p1.x,2) + Math.pow(p1.y,2));
		double c = Math.sqrt(Math.pow(p1.x - BasGauche.x,2) + Math.pow(p1.y - BasGauche.y,2));
		double angle = Math.acos((c + a - b)/(2*a*c));
		
		Point p2 = (Point) arg2;
		double b2 = Math.sqrt(Math.pow(p2.x,2) + Math.pow(p2.y,2));
		double c2 = Math.sqrt(Math.pow(p2.x - BasGauche.x,2) + Math.pow(p2.y - BasGauche.y,2));
		double angle2 = Math.acos((c2 + a - b2)/(2*a*c2));
		
		if (angle > angle2) return 1;
		else if(angle == angle2)
		{
			if (p1.y > p2.y) return 1; // si ils sont aligné on prend le plus proche en 1er
			else if (p1.y == p2.y) return 0; // si même angle et même y c'est que le x est egal lui aussi
											// donc même point, donc ça arrivera jamais trololololo
		}
		return -1;
	}

}
