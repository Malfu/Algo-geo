package tp_geometrie1;
import java.util.Random;
import java.util.Vector;
import java.util.Stack;

/** La classe algorithme. */
class Algorithmes {
	
	/** Algorithme qui prend un ensemble de points et qui retourne un ensemble de segments. */ 
	static Vector<Segment> algorithme1(Vector<Point> points)
	{
		// Creation de la liste des segments
		Vector<Segment> segments = new Vector<Segment>();
		
		// Ajout d'un segment entre chaque paire de points consecutifs
		for(int n1 = 0; n1 < points.size() - 1; n1++)
		{
			Point p1 = points.elementAt(n1);
			Point p2 = points.elementAt(n1+1);
			segments.addElement(new Segment(p1,p2));
		}
		
		return segments;
	}
	
	/** Algorithme qui met a jour les point plus haut et plus bas **/
	static void Marquer (Vector<Point> points)
	{
		Point ymax = points.elementAt(0);
		Point ymin = points.elementAt(0);
		for(int i =0; i < points.size(); ++i)
		{
			points.elementAt(i).nom = "p" + i;
			if (ymax.y <= points.elementAt(i).y)
			{
				ymax = points.elementAt(i);
			}
			if (ymin.y >= points.elementAt(i).y)
			{
				ymin = points.elementAt(i);
			}
		}
		ymax.nom = "Plus Haut";
		ymin.nom = "Plus Bas";
		Segment.SetSommet(ymax);
		Segment.SetBase(ymin);
		
	}
	
	static Vector<Point> CalculGauche (Vector<Point> points)
	{
		Vector<Point> gauche = new Vector<Point>();
		int indice = 0;
		for(;points.elementAt(indice).x != Segment.Sommet().x && points.elementAt(indice).y != Segment.Sommet().y;++indice);
		
		int indice2 = indice +1;
		if (indice2 == points.size()) indice2 = 0;
		
		boolean cote = points.elementAt(indice).x < points.elementAt(indice2).x;
		if(points.elementAt(indice2) == Segment.Base()) cote = ! cote;
		
		if (cote) -- indice;
		else ++ indice;
		if (cote && indice < 0) indice = points.size() -1;
		if(!cote && indice > points.size() -1) indice = 0;
		while(points.elementAt(indice).x != Segment.Base().x && points.elementAt(indice).y != Segment.Base().y)
		{
			points.elementAt(indice).cote = "gauche";
			gauche.add(points.elementAt(indice));
			if (cote) -- indice;
			else ++ indice;
			if (cote && indice < 0) indice = points.size() -1;
			if(!cote && indice > points.size() -1) indice = 0;
			
		}
		
		return gauche;
	}
	
	static Vector<Point> CalculDroite (Vector<Point> points )
	{
		Vector<Point> droite = new Vector<Point>();
		int indice = 0;
		for(;points.elementAt(indice).x != Segment.Sommet().x && points.elementAt(indice).y != Segment.Sommet().y;++indice);
		
		int indice2 = indice +1;
		if (indice2 == points.size()) indice2 = 0;
		
		
		boolean cote = points.elementAt(indice).x < points.elementAt(indice2).x;
		if(points.elementAt(indice2) == Segment.Base()) cote = ! cote;
		
		if (cote) ++ indice;
		else -- indice;
		if (cote && indice > points.size() -1) indice = 0;
		if(!cote && indice < 0) indice = points.size() -1;
		while(points.elementAt(indice).x != Segment.Base().x && points.elementAt(indice).y != Segment.Base().y)
		{
			points.elementAt(indice).cote = "droit";
			droite.add(points.elementAt(indice));
			if (cote) ++ indice;
			else -- indice;
			if (cote && indice > points.size() -1) indice = 0;
			if(!cote && indice < 0) indice = points.size() -1;
			
		}
		
		return droite;
	}
	
	static Vector<Point> Fusionner (Vector<Point> droite,  Vector<Point> gauche)
	{
		Vector<Point> liste = new Vector<Point>();
		liste.add(Segment.Sommet());
		int i = 0, j =0;
		
		for (; i < droite.size() && j < gauche.size();)
		liste.add((droite.elementAt(i).y > gauche.elementAt(j).y) ? droite.elementAt(i++) : gauche.elementAt(j++));
		
		for (; i < droite.size();++i) liste.add(droite.elementAt(i));
		for (; j < gauche.size();++j) liste.add(gauche.elementAt(j));
		
		liste.add(Segment.Base());
		
		return liste;
		
			
	}

	static double determinant (double[][] mat)
	{
		double result = 0.0;
		if (mat.length == 1)
		{
			result = mat[0][0];
			return result;
		}
		
		if (mat.length == 2)
		{
			result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
			return result;
		}
		
		for (int i = 0; i < mat[0].length; ++i)
		{
			double temp[][] = new double[mat.length -1][mat[0].length -1];
			
			for(int j = 1; j < mat.length; ++j)
			{
				System.arraycopy(mat[j], 0, temp[j-1], 0, i);
				System.arraycopy(mat[j], i+1, temp[j-1], i, mat[0].length - i - 1);
			}
			result += mat[0][i] * Math.pow(-1, i) * determinant(temp);
		}
		
		return result;
	}
	
	static boolean InfPi(Point a, Point b , Point c)
	{
		double mat[][] = new double[3][3];
		mat[0][0] = a.x;
		mat[0][1] = a.y;
		mat[1][0] = b.x;
		mat[1][1] = b.y;
		mat[2][0] = c.x;
		mat[2][1] = c.y;
		mat[0][2] = mat[1][2] = mat[2][2] = 1;
		double result = determinant(mat);
		System.out.println("determinant vaut " + result + " pour les points : " + a.nom + " " + b.nom + " " + c.nom + "\n" );
		return (result >= 0.0);
	}
	static void affichersegment(Point a, Point b)
	{
		System.out.println("segment : point " + a.nom + " vers point " + b.nom + "\n");
	}
	static Vector<Segment> NouvellesDiags(Vector<Point> liste, Vector<Point> droite, Vector<Point> gauche)
	{
		Vector<Segment> diagonales = new Vector<Segment>();
		Stack<Point> pile = new Stack<Point>();
		
		pile.push(liste.elementAt(0));
		pile.push(liste.elementAt(1));
		
		for(int j =2; j < liste.size() -1; ++j)
		{
			if (!liste.elementAt(j).cote.equals(pile.peek().cote))
			{
				while(!pile.empty())
				{
					System.out.println("Contexte : chaines differentes \n");
					affichersegment(liste.elementAt(j),pile.peek());
					diagonales.add(new Segment(liste.elementAt(j),pile.pop()));
				}
				diagonales.remove(diagonales.size()-1);
				pile.push(liste.elementAt(j-1));
				pile.push(liste.elementAt(j));
			}
			else
			{
				Point sommet = pile.pop();
				/* angle */
				Point dernier_sommet = sommet;
				System.out.println("sommet =" + sommet.nom + " dernier_sommet =" + pile.peek().nom + " element j =" + liste.elementAt(j).nom);
				while( ((InfPi(sommet, pile.peek() ,  liste.elementAt(j))) && 
						(sommet.cote.equals("droit") )) ||
						((!InfPi(sommet, pile.peek(), liste.elementAt(j))) && 
						(sommet.cote.equals("gauche"))) )
				{
					diagonales.add(new Segment(liste.elementAt(j), pile.peek()));
					System.out.println("Contexte : chaines identiques");
					affichersegment(liste.elementAt(j),pile.peek());
					dernier_sommet = pile.pop();
					if(pile.empty())break;
				}
				pile.push(dernier_sommet);
				pile.push(liste.elementAt(j));
			}
		}
		if(!pile.empty())
		{
			pile.pop();
			if(!pile.empty())
			{
				while(!pile.empty())
				{
					System.out.println("Contexte : Dernière condition \n");
					affichersegment(liste.elementAt(liste.size()-1),pile.peek());
					diagonales.add(new Segment(liste.elementAt(liste.size()-1),pile.pop()));
				}
				System.out.println("Contexte : Remove \n");
				affichersegment(diagonales.elementAt(diagonales.size()-1).a , diagonales.elementAt(diagonales.size()-1).b);
				diagonales.remove(diagonales.size()-1);
			}
		}
		
		return diagonales;
		
	}
	/** Algorithme qui triangule le polygone **/
	static Vector<Segment> trianguler ( Vector<Point> points )
	{
		Marquer(points); // le point haut et bas sont marqué
		Vector<Point> droite = CalculDroite(points);
		Vector<Point> gauche = CalculGauche(points);
		
		for(int i = 0; i<droite.size(); ++i)
			System.out.println(droite.elementAt(i).nom + " appartient a droite ( coord :" + droite.elementAt(i).x + " , " + droite.elementAt(i).y + ")");
		for(int i = 0; i<gauche.size(); ++i)
			System.out.println(gauche.elementAt(i).nom + " appartient a gauche ( coord :" + gauche.elementAt(i).x + " , " + gauche.elementAt(i).y + ")");
		
		for(int i = 0; i<points.size(); ++i)
			System.out.println("cot du point" + points.elementAt(i).nom + " est : " + points.elementAt(i).cote);
		
		Vector<Point> liste = Fusionner(droite, gauche);
		Vector<Segment> nouvellesDiags = NouvellesDiags(liste,droite, gauche);

		return nouvellesDiags;
	}
	static Point CalculPointGauche(Vector<Point> points)
	{
		Point xmin = points.elementAt(0);
		for(int i =0; i < points.size(); ++i)
		{
			points.elementAt(i).nom = "p" + i;
			if (xmin.x >= points.elementAt(i).x)
			{
				xmin = points.elementAt(i);
			}
		}
		xmin.nom = "GAUCHE";
		return xmin;
	}
	static double distance (Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}
	static double surface (Point p1, Point p2, Point p3)
	{
		return ((p2.x - p1.x ) * (p3.y - p1.y) - (p3.x - p1.x)*(p2.y - p1.y)) * 0.5;
	}
	static Vector<Point> initQuickHull(Vector<Point> points)
	{
		Point gauche = CalculPointGauche(points);
		Point point = new Point();
		point.x = gauche.x;
		point.y = gauche.y + 1.3;
		point.nom = "temp";
		Vector<Point> enveloppe = new Vector<Point>();
		points.add(point);
		quickHull(gauche, point, points, enveloppe);
		for(int i = 0; i< enveloppe.size(); ++i)
			System.out.println("point " + enveloppe.elementAt(i).nom + "fait partis de l'enveloppe");
		points.removeElement(point);
		enveloppe.add(gauche);
		return enveloppe;
		
	}
	static void quickHull (Point p1, Point p2, Vector<Point> e, Vector<Point> enveloppe)
	{
	     if( e.isEmpty() ) return;
	     if( e.size() == 1 )
	     { 
	    	 enveloppe.add(e.elementAt(0)); 
	    	 return; 
	     }
	     // recherche du point de e le plus éloigné du segment p1, p2
	     double d = distance(p1, p2);
	     Point pM = e.elementAt(0) ;
	     double dM = 2*Math.abs(surface(p1, p2, pM)/d);
	     for( int i = 1; i < e.size(); ++i)
	     {
	         Point p = e.elementAt(i);
	         double dl = 2*Math.abs(surface(p1, p2, p)/d);
	         if(dl > dM)
	         {
	            pM = p;
	            dM = dl;
	         }
	     }
	     // Division de e en e1, e2
	     Vector<Point> e1 = new Vector<Point>();    
	     Vector<Point> e2 = new Vector<Point>();
	     for( int i = 0; i< e.size(); ++i) 
	     {
	    	 Point p = e.elementAt(i);
	    	 if( distance(p, pM) != 0 )
	    	 {
	    		 if( surface(p1, pM, p) < 0 )  e1.add(p);
	    		 if( surface(pM, p2, p) < 0 )  e2.add(p);
	    	 }
	     }
	     quickHull(p1, pM, e1, enveloppe);
	     enveloppe.add(pM);
	     quickHull(pM, p2, e2, enveloppe);
	}
	
	/** Retourne un nombre aleatoire entre 0 et n-1. */
	static int rand(int n)
	{
		int r = new Random().nextInt();
		
		if (r < 0) r = -r;
		
		return r % n;
	}
}

