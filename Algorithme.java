package tp_geometrie1;
import java.util.Collections;
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
				while( ((surface(sommet, pile.peek() ,  liste.elementAt(j))) >0 && 
						(sommet.cote.equals("droit") )) ||
						((surface(sommet, pile.peek(), liste.elementAt(j))) < 0 && 
						(sommet.cote.equals("gauche"))) )
				{
					diagonales.add(new Segment(liste.elementAt(j), pile.peek()));
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
					diagonales.add(new Segment(liste.elementAt(liste.size()-1),pile.pop()));
				}
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
		Vector<Point> enveloppe = new Vector<Point>();
		if(points.size() < 4)
		{
			enveloppe.addAll(points); // point ou droite ou triangle
			return enveloppe;
		}
		Point gauche = CalculPointGauche(points);
		Point point = new Point();
		point.x = gauche.x;
		point.y = gauche.y + 1.3;
		point.nom = "temp";
		points.add(point);
		quickHull(gauche, point, points, enveloppe);
		points.removeElement(point);
		enveloppe.add(gauche);
		return enveloppe;
		
	}
	static void quickHull (Point p1, Point p2, Vector<Point> nuage, Vector<Point> enveloppe)
	{
	     if( nuage.isEmpty() ) return;
	     if( nuage.size() == 1 )
	     { 
	    	 enveloppe.add(nuage.elementAt(0)); 
	    	 return; 
	     }
	     // recherche du point du nuage le plus éloigné du segment p1, p2
	     double d = distance(p1, p2);
	     Point pM = nuage.elementAt(0) ;
	     double dM = 2*Math.abs(surface(p1, p2, pM)/d);
	     for( int i = 1; i < nuage.size(); ++i)
	     {
	         Point p = nuage.elementAt(i);
	         double dl = 2*Math.abs(surface(p1, p2, p)/d);
	         if(dl > dM)
	         {
	            pM = p;
	            dM = dl;
	         }
	     }
	     // Division du nuage
	     Vector<Point> nuage1 = new Vector<Point>();    
	     Vector<Point> nuage2 = new Vector<Point>();
	     for( int i = 0; i< nuage.size(); ++i) 
	     {
	    	 Point p = nuage.elementAt(i);
	    	 if( distance(p, pM) != 0 )
	    	 {
	    		 if( surface(p1, pM, p) < 0 )  nuage1.add(p);
	    		 if( surface(pM, p2, p) < 0 )  nuage2.add(p);
	    	 }
	     }
	     quickHull(p1, pM, nuage1, enveloppe);
	     enveloppe.add(pM);
	     quickHull(pM, p2, nuage2, enveloppe);
	}
	
	static Vector<Point> graham(Vector<Point> nuage)
	{
		Vector<Point> enveloppe = new Vector<Point>();
		
		if( nuage.size() < 4) // c'est un triangle ou une droite, voir un point
		{ 
			enveloppe.addAll(nuage);
			return enveloppe;
		}
		
		Vector<Point> nuage2 = (Vector<Point>)nuage.clone(); // on clone pour pas modifier nos points
		// recherche du point le plus bas et le plus à gauche(si il y a des y égaux)
		Point BasGauche = nuage2.elementAt(0);
		int iBasGauche = 0;
		for( int i = 1; i < nuage2.size(); ++i)
		{
			Point point = nuage2.elementAt(i);
		    if(point.y < BasGauche.y || (point.y== BasGauche.y && point.x < BasGauche.x))
		    {
		          BasGauche = point;
		          iBasGauche = i;
		    }
		}
		BasGauche.nom = "Bas_Gauche"; // on marque notre point
		nuage2.remove(iBasGauche); // on enleve le point trouvé pour l'exclure du trie (c'est lui qui sert de reference)
		Collections.sort(nuage2 ,new Comparateur(BasGauche)); // on ordonne par angle entre l'élément courant et BasGauche et (0,0)
		nuage2.add(0, BasGauche); // on remet le plus bas gauche a l'élément 0

		// calcul de l'enveloppe
		Point p0 = nuage2.elementAt(nuage2.size()-1);// le premier et le dernier font partit de l'enveloppe
		Point p1 = nuage2.elementAt(0);
		enveloppe.add(p0);
		enveloppe.add(p1);
		for( int i = 1; i < nuage2.size(); ++i) 
		{
			Point p2 = nuage2.elementAt(i); // on se place à l'élément courant
			for( ; ;)
			{
				p0 = enveloppe.elementAt(enveloppe.size()-2);
				p1 = enveloppe.elementAt(enveloppe.size()-1);
				// si ça tourne pas dans le bon sens on enlève des points de notre enveloppe
				// jusqu'a ce que ce soit bon
				if(surface(p0, p1, p2) >= 0 && enveloppe.size()>1)
				{
					enveloppe.remove(enveloppe.size()-1);
		        }
		        else break;
			}
			enveloppe.add(p2); // sinon on ajoute et on continue
		}
		return enveloppe;
	}
	
	/** Retourne un nombre aleatoire entre 0 et n-1. */
	static int rand(int n)
	{
		int r = new Random().nextInt();
		
		if (r < 0) r = -r;
		
		return r % n;
	}
}

