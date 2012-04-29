package tp_geometrie1;
import java.util.Comparator;

public class CompXPair implements Comparator {
	
	CompXPair(){}
	  
	public int compare(Object arg1, Object arg2) 
	{	
		Pair<Point, Integer> p1 = (Pair<Point, Integer>) arg1;
		Pair<Point, Integer> p2 = (Pair<Point, Integer>) arg2;
		/////
		if ( p1.obj1 == null) return -1;
		if ( p2.obj1 == null) return 1;
		/////
		if (p1.obj1.x > p2.obj1.x) return 1;
		else if(p1.obj1.x == p2.obj1.x)
		{
			if (p1.obj1.y > p2.obj1.y) return 1;
			else if (p1.obj1.y == p2.obj1.y) return 0;
		}
		return -1;
	}

}
