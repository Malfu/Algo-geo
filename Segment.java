package tp_geometrie1;
/** La classe segment */
public class Segment implements Comparable<Segment>
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
	
	public int compareTo(Segment arg2) // pour arbre AVL, par l'ordonnée
	{
		if (this == null) return -1;
		Segment s2 = (Segment) arg2;
		
		Point gaucheThis = (this.a.x > this.b.x) ? this.b : this.a;
		Point droiteThis = (this.a.x < this.b.x) ? this.b : this.a;
		
		Point gauche = (s2.a.x > s2.b.x) ? s2.b : s2.a;
		Point droite = (s2.a.x < s2.b.x) ? s2.b : s2.a;
		
		if (gaucheThis.y > gauche.y) return 1;
		else if(gaucheThis.y == gauche.y)
		{
			if (droiteThis.y > droite.y) return 1;
			else if (droiteThis.y == droite.y) return 0;
		}
		return -1;
	}
	
	public boolean equals (Segment s2)// pour arbre AVL
	{
		if (this == null) return false;
		return (this.a.x == s2.a.x &&
				this.a.y == s2.a.y &&
				this.b.x == s2.b.x &&
				this.b.y == s2.b.y);
	}
	/*
	   public static String assemble(AVLNode<Segment> temp, int offset)
	   {
	       String ret= "";
	       for(int i = 0; i < offset; i++)
	           ret += "\t";
	       if(temp.getElement() != null)
	       ret += temp.getElement().a.y + "\n";

	       if(temp.getLeft() != null)
	       {
	            ret += "Left: " + assemble(temp.getLeft(), offset + 1);
	       }
	       if(temp.getRight() != null)
	       {
	            ret += "Right: " + assemble(temp.getRight(), offset + 1);
	       }
	       return ret;

	   }
	   */
}
