package tp_geometrie1;
import java.util.Comparator;

public class CompX implements Comparator {
	
	CompX(){}
	  
	public int compare(Object arg1, Object arg2) 
	{
		Point p1 = (Point) arg1;
		Point p2 = (Point) arg2;
		
		if (p1.x > p2.x) return 1;
		else if(p1.x == p2.x)
		{
			if (p1.y > p2.y) return 1;
			else if (p1.y == p2.y) return 0;
		}
		return -1;
	}

}