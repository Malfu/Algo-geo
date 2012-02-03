package tp_geometrie1;
/** La classe segment */
public class Segment 
{
	/** L'extremite a. */
	public Point a;
	
	/** L'extremite b. */
	public Point b;
	
	private static Point sommet;
	
	private static Point base;
	
	/** Constructeur avec initialisation de a et b. */
	public Segment(Point a, Point b)
	{
		this.a = a;
		this.b = b;
	}
	
	public static Point Sommet()
	{
		return sommet;
	}
	
	public static Point Base()
	{
		return base;
	}
	
	public static void SetSommet(Point point)
	{
		sommet = point;
	}
	
	public static void SetBase(Point point)
	{
		base = point;
	}
	
	/** Constructeur sans initialisation. */
	public Segment(){}
}
