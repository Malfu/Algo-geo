package tp_geometrie1;
/** La classe Point. */
public class Point
{
	/** La valeur de x. */
	public double x;
	
	/** La valeur de y. */
	public double y;
	
	public String nom;
	
	/** Constructeur avec initialisation de x et y. */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
		this.nom = "undef";
	}
	public Point(double x, double y, String Nom)
	{
		this.x = x;
		this.y = y;
		this.nom = Nom;
	}
	/** Constructeur sans initialisation. */
	public Point(){}
}