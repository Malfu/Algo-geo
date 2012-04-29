package tp_geometrie1;
import java.util.Comparator;

public class Seg_Comp implements Comparator {

	
	Seg_Comp(){}
	  
	public int compare(Object arg1, Object arg2) 
	{
		Segment A = (Segment) arg1;
		Segment B = (Segment) arg2;
		
		Point Agauche = (A.a.x > A.b.x) ? A.b : A.a;
		Point Adroite = (A.a.x > A.b.x) ? A.a : A.b;
		
		Point Bgauche = (B.a.x > B.b.x) ? B.b : B.a;
		Point Bdroite = (B.a.x > B.b.x) ? B.a : B.b;
		
		if (Agauche.y < Bgauche.y) return 1;
		else if (Agauche.y == Bgauche.y)
		{
			if (Adroite.y < Bdroite.y) return 1;
			else if (Adroite.y == Bdroite.y) return 0;	
		}
		return -1;
	}
	
	
}
