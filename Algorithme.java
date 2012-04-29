package tp_geometrie1;
import java.util.*;

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
		if (points.size() <4)
		{
			return new Vector<Segment>();
		}
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
	
	static double angle (Point p1, Point p2, Point p3) // renvois l'angle entre le segment p1 p2 et l'horizontale (ou p3) par al kashi
	{
		Point u = new Point(p2.x - p1.x, p2.y - p1.y);
		Point v = new Point(p3.x - p1.x, p3.y - p1.y);
		double result = Math.atan2(v.y, v.x) - Math.atan2(u.y, u.x);
		return result;
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
		point.y = 1;//gauche.y + 1.3;
		point.nom = "temp";
		quickHull(gauche, point, points, enveloppe);
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
		
		// recherche du point le plus bas et le plus à gauche(si il y a des y égaux)
		Point BasGauche = nuage.elementAt(0);
		int iBasGauche = 0;
		for( int i = 1; i < nuage.size(); ++i)
		{
			Point point = nuage.elementAt(i);
		    if(point.y < BasGauche.y || (point.y== BasGauche.y && point.x < BasGauche.x))
		    {
		          BasGauche = point;
		          iBasGauche = i;
		    }
		}
		BasGauche.nom = "Bas_Gauche"; // on marque notre point
		nuage.remove(iBasGauche); // on enleve le point trouvé pour l'exclure du trie (c'est lui qui sert de reference)
		Collections.sort(nuage ,new Comparateur(BasGauche)); // on ordonne par angle entre l'élément courant et BasGauche et (0,0)
		nuage.add(0, BasGauche); // on remet le plus bas gauche a l'élément 0

		// calcul de l'enveloppe
		Point p0 = nuage.elementAt(nuage.size()-1);// le premier et le dernier font partit de l'enveloppe
		Point p1 = nuage.elementAt(0);
		enveloppe.add(p0);
		enveloppe.add(p1);
		for( int i = 1; i < nuage.size(); ++i) 
		{
			Point p2 = nuage.elementAt(i); // on se place à l'élément courant
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
	
	static Vector<Point> jarvis(Vector<Point> nuage)// papier cadeau
	{
		Vector<Point> enveloppe = new Vector<Point>();
	    // recherche du point bas droite (pour changer =p)
		
		if( nuage.size() < 4) // c'est un triangle ou une droite, voir un point
		{ 
			enveloppe.addAll(nuage);
			return enveloppe;
		}
		
	    int iBasDroite = 0;
	    Point BasDroite = nuage.elementAt(0);
	    for( int i = 1; i < nuage.size(); ++i) 
	    {
	         Point p = nuage.elementAt(i);
	         if(p.y < BasDroite.y || (p.y == BasDroite.y && p.x < BasDroite.x) )
	         {
	            BasDroite = p;
	            iBasDroite = i;
	         }
	    }
	    BasDroite.nom = "BasDroite";
	    nuage.add(BasDroite);
	    // basdroite extrait, maintenant on fait les angles
	    double angle = 0.0;
	    double anglePrec;
	    int m, iMin = iBasDroite;
	    Point tmp = new Point();
	    for(  m = 0; m < nuage.size()-1; ++m)
	    {
	    	tmp = nuage.elementAt(iMin);
	    	nuage.set(iMin, nuage.elementAt(m));
	    	nuage.set(m, tmp);
	    	// recherche du point suivant
	        iMin = nuage.size()-1;
	        anglePrec = /*angle;*/ 0.0;
	        angle = 360.0;
	        int n = (m-1 >=0 ) ? (m-1) : 0;
	        for( int i = m+1; i < nuage.size(); ++i)
	        {
	        	double a =  Math.toDegrees(angle(nuage.elementAt(m),  nuage.elementAt(n), nuage.elementAt(i)));
	        	if (a < 0) a = 360 + a;
	        	if(a > anglePrec && a < angle)
	            {
	        		iMin = i;
	        		angle = a;
	            }
	            else if( a==angle )
	            { 
	            	// points alignés : choisir le plus éloigné
	            	if(distance(nuage.elementAt(iMin), nuage.elementAt(m)) < distance(nuage.elementAt(i) , nuage.elementAt(m))) 
	            	iMin = i; 
	            }
	        }
	        if(iMin == nuage.size()-1 )break;// on est retombé sur le point de départ
	    }
	    for( int i = 0; i <= m; ++i) enveloppe.add(nuage.elementAt(i));
	    nuage.remove(nuage.size()-1);
	    
	    return enveloppe;
	}
	
	static Vector<Point> partition(Vector<Point> nuage)
	{
		Vector<Point> enveloppe = new Vector<Point>();
		
		// trie des points par x
		Collections.sort(nuage, new CompX());
		if(nuage.size() < 4)
		{
	        enveloppe.addAll(nuage); 
	        if(  nuage.size() == 3 && surface(enveloppe.elementAt(0), enveloppe.elementAt(1), enveloppe.elementAt(2)) > 0 )
	        {
	        	Point p = enveloppe.elementAt(1);
	        	enveloppe.remove(p); // u don't understand what I'm doing?
	        	enveloppe.add(p); // u mad?
	        	// mais si on enleve ces deux lignes ça marce plus, guess why \o/
	        }
	        return enveloppe;
		}


		//On divise en deux le nuage
		Vector<Point> nuage1 = new Vector<Point>();
		Vector<Point> nuage2 = new Vector<Point>();
		for(int i = 0; i < nuage.size()/2 ; ++i) nuage1.add(nuage.elementAt(i));
	    for(int i = nuage.size()/2 ; i < nuage.size() ; ++i) nuage2.add(nuage.elementAt(i));
	    
	    // Calcul des sous enveloppes
	    Vector<Point> enveloppe1 = partition(nuage1);
	    Vector<Point> enveloppe2 = partition(nuage2);
	    // fusion des enveloppes et retour 
	    enveloppe.addAll( fusion(enveloppe1, enveloppe2) );
	    return enveloppe;
	}
	    
	static Vector<Point> fusion(Vector<Point> enveloppe1, Vector<Point> enveloppe2)
	{
		// recherche x max dans enveloppe 1
		int iMax = 0;
		for( int i = 1; i < enveloppe1.size(); ++i) 
			if(enveloppe1.elementAt(i).x > enveloppe1.elementAt(iMax).x) iMax = i;
		// le point xMin dans enveloppe2 est en 0 (u don't know why? u mad?)
		int iMin = 0;
		
		// partie haute
		int ip1 = iMax; 
		int ip2 = iMin;
		boolean bool = true;
		while(bool)
		{ 
			bool = false;
			if(surface(enveloppe2.elementAt(ip2), enveloppe1.elementAt(ip1), enveloppe1.elementAt((ip1+1)%enveloppe1.size()))>0)
			{
				ip1 = (ip1+1)%enveloppe1.size();
				bool = true;
			}
			if(surface(enveloppe1.elementAt(ip1), enveloppe2.elementAt(ip2), enveloppe2.elementAt((ip2-1+enveloppe2.size())%enveloppe2.size()))<0)
			{
				ip2 = (ip2-1+enveloppe2.size())%enveloppe2.size();
				bool = true;
			}
		}
		// partie basse, c'est la même chose en inversant les conditions et l'affectation
		// quelquefois il faut pas réfléchir, a ce stade mon cerveau avais déjà surchauffé
		int im1 = iMax; 
		int im2 = iMin;
		bool = true;
		while(bool)
		{ 
			bool = false;
			if(surface(enveloppe2.elementAt(im2), enveloppe1.elementAt(im1), enveloppe1.elementAt((im1-1+enveloppe1.size())%enveloppe1.size()))<0)
			{
	            im1 = (im1-1+enveloppe1.size())%enveloppe1.size();
	            bool = true;
			}
			if(surface(enveloppe1.elementAt(im1), enveloppe2.elementAt(im2), enveloppe2.elementAt((im2+1)%enveloppe2.size()))>0)
			{
	            im2 = (im2+1)%enveloppe2.size();
	            bool = true;
			}
		}
		// on crée l'enveloppe finale
		Vector<Point> enveloppe = new Vector<Point>();
		
		for( int i = 0; i<=im1; ++i) enveloppe.add(enveloppe1.elementAt(i));
		
		if(ip2 == 0)
		{
			for( int i = im2; i<enveloppe2.size(); ++i) enveloppe.add(enveloppe2.elementAt(i));
			
			enveloppe.add(enveloppe2.elementAt(0));
		}
		else for( int i = im2;  i<=ip2; ++i) enveloppe.add(enveloppe2.elementAt(i));
		
		if(ip1 != 0) for( int i = ip1; i<enveloppe1.size(); ++i) enveloppe.add(enveloppe1.elementAt(i));

	    return enveloppe;
	}
	
	
	public static Point intersect(Segment s1, Segment s2)
	{
		if (s1 == null || s2 == null) return null;
		Point A = s1.a;
		Point B = s1.b;
		Point C = s2.a;
		Point D = s2.b;
		
		double Ax = A.x;
		double Ay = A.y;
		double Bx = B.x;
		double By = B.y;
		double Cx = C.x;
		double Cy = C.y;
		double Dx = D.x;
		double Dy = D.y;
 
		double Sx;
		double Sy;
 
		if(Ax==Bx)
		{
			if(Cx==Dx) return null;
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				Sx = Ax;
				Sy = pCD*(Ax-Cx)+Cy;
			}
		}
		else
		{
			if(Cx==Dx)
			{
				double pAB = (Ay-By)/(Ax-Bx);
				Sx = Cx;
				Sy = pAB*(Cx-Ax)+Ay;
			}
			else
			{
				double pCD = (Cy-Dy)/(Cx-Dx);
				double pAB = (Ay-By)/(Ax-Bx);
				double oCD = Cy-pCD*Cx;
				double oAB = Ay-pAB*Ax;
				Sx = (oAB-oCD)/(pCD-pAB);
				Sy = pCD*Sx+oCD;
			}
		}
		if((Sx<Ax && Sx<Bx)|(Sx>Ax && Sx>Bx) | (Sx<Cx && Sx<Dx)|(Sx>Cx && Sx>Dx)
				| (Sy<Ay && Sy<By)|(Sy>Ay && Sy>By) | (Sy<Cy && Sy<Dy)|(Sy>Cy && Sy>Dy)) return null;
		  return new Point((double)Sx,(double)Sy);
	}
	
	public static boolean Contains(PriorityQueue<Pair<Point, Integer>> X , Point p)
	{
		for(Pair<Point, Integer> pp : X)
		{
			if(pp.obj1.x == p.x && pp.obj1.y == p.y) return true;
		}
		return false;
	}
	
	//fonction qui teste si un point est l'extremitée gauche d'un segment
	public static boolean isExtrGauche(Pair<Point, Integer> point, Vector<Vector<Segment>> segments)
	{
		double gauche = Math.min(segments.elementAt(point.obj2).elementAt(0).a.x,segments.elementAt(point.obj2).elementAt(0).b.x);
						
		if (gauche == point.obj1.x) return true;
		return false;
	}
	
	public static boolean isIntersect(Pair<Point, Integer> point, int limit_intersect)
	{
		if (point.obj2 >= limit_intersect)
		{
			return true;
		}
		return false;
	}
	
	public static void swap_order(Segment s1, Segment s2, Point intersect)
	{
		if (s1.a.x > s1.b.x) s1.b = intersect;
		else s1.a = intersect;
		
		if (s2.a.x > s2.b.x) s2.b = intersect;
		else s2.a = intersect;
	}
	
	public static Vector<Point> intersect_segments(Vector<Vector<Segment>> segments)
	{
		TreeSet<Segment> T = new TreeSet<Segment>(new Seg_Comp());
		
		PriorityQueue<Pair<Point, Integer>> X = new PriorityQueue<Pair<Point, Integer>>(11, new CompXPair());

		Vector<Point> result = new Vector<Point>();
		Pair<Point, Integer> E;//point évènement
		Pair<Point, Integer> intersection = new Pair<Point, Integer>();// point d'intersection
		
		int limit_intersect = segments.size(); // a partir de cet indice ce sont des segments d'intersections

		for(int i =0; i < segments.size() ; ++i)
		{
			X.add(new Pair(segments.elementAt(i).elementAt(0).a, i));
			X.add(new Pair(segments.elementAt(i).elementAt(0).b, i));
		}


		while(! X.isEmpty())
		{
			E = X.poll();
			if (isIntersect(E, limit_intersect))
			{
				Segment s1 = segments.elementAt(E.obj2).elementAt(0);
				Segment s2 = segments.elementAt(E.obj2).elementAt(1);
				
				T.remove(s1);
				T.remove(s2);
				
				swap_order(s1, s2, E.obj1);
				
				T.add(s1);
				T.add(s2);
				
				Seg_Comp comp = new Seg_Comp();
				Segment dessus, dessous;
				if (comp.compare(s1, s2) == 1) // s2 plus haut; on recupere le dessous du segment plus haut et dessus du segment plus bas
				{
					dessus  = T.higher(s1);
					dessous = T.lower(s2);
					
					if(dessus != null) 
					{
						intersection.obj1 = intersect(dessus, s1);
						if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
						{
							if(!Contains(X, intersection.obj1))
	          				{
								intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
								Vector<Segment> segments_intersection = new Vector<Segment>();
								segments_intersection.add(dessus);
								segments_intersection.add(s1);
								segments.add(segments_intersection);
								
								result.add(intersection.obj1);
								X.add(intersection);
	          				}
						}
					}
					
					if(dessous != null) 
					{
						intersection.obj1 = intersect(dessous, s2);
						if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
						{
							if(!Contains(X, intersection.obj1))
	          				{
								intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
								Vector<Segment> segments_intersection = new Vector<Segment>();
								segments_intersection.add(dessous);
								segments_intersection.add(s2);
								segments.add(segments_intersection);
								
								result.add(intersection.obj1);
								X.add(intersection);
	          				}
						}
					}
					
				}
				else
				{
					dessus  = T.higher(s2);
					dessous = T.lower(s1);
					
					if(dessus != null) 
					{
						intersection.obj1 = intersect(dessus, s2);
						if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
						{
							if(!Contains(X, intersection.obj1))
	          				{
								intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
								Vector<Segment> segments_intersection = new Vector<Segment>();
								segments_intersection.add(dessus);
								segments_intersection.add(s2);
								segments.add(segments_intersection);
								
								result.add(intersection.obj1);
								X.add(intersection);
	          				}
						}
					}
					
					if(dessous != null) 
					{
						intersection.obj1 = intersect(dessous, s1);
						if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
						{
							if(!Contains(X, intersection.obj1))
	          				{
								intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
								Vector<Segment> segments_intersection = new Vector<Segment>();
								segments_intersection.add(dessous);
								segments_intersection.add(s1);
								segments.add(segments_intersection);
								
								result.add(intersection.obj1);
								X.add(intersection);
	          				}
						}
					}
				}
				
				
				
			}
			else if(isExtrGauche(E, segments))//gauche
			{	
				Segment segment_courant = segments.elementAt(E.obj2).elementAt(0);
				T.add(segment_courant);
				Segment dessus  = T.higher(segment_courant);
				Segment dessous = T.lower(segment_courant);

				if(dessus != null) 
				{
					intersection.obj1 = intersect(dessus, segment_courant);
					if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
					{
						intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
						Vector<Segment> segments_intersection = new Vector<Segment>();
						segments_intersection.add(dessus);
						segments_intersection.add(segment_courant);
						segments.add(segments_intersection);
						
						result.add(intersection.obj1);
						X.add(intersection);
					}
				}
				
				if(dessous != null) 
				{
					intersection.obj1 = intersect(dessous, segment_courant);
					if (intersection.obj1 != null && !Double.isNaN(intersection.obj1.x))
					{
						intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
						Vector<Segment> segments_intersection = new Vector<Segment>();
						segments_intersection.add(dessous);
						segments_intersection.add(segment_courant);
						segments.add(segments_intersection);
						
						result.add(intersection.obj1);
						X.add(intersection);
					}
				}
			
			}
			else // droite
			{
				Segment segment_courant = segments.elementAt(E.obj2).elementAt(0);
				Segment dessus = T.higher(segment_courant);
				Segment dessous = T.lower(segment_courant);
          		T.remove(segment_courant);

          		if(dessus != null && dessous != null)
          		{
          			intersection.obj1 = intersect(dessus, dessous);
          			if(intersection.obj1 != null &&  !Double.isNaN(intersection.obj1.x))
          			{
          				if(!Contains(X, intersection.obj1))
          				{
	    					intersection.obj2 = segments.size(); // on place les segments d'intersections dans l'ensemble des segments
							Vector<Segment> segments_intersection = new Vector<Segment>();
							segments_intersection.add(dessous);
							segments_intersection.add(dessus);
	    					segments.add(segments_intersection);
	    					
	    					result.add(intersection.obj1);
	          				X.add(intersection);
          				}
          			}
          		}
          		
			}
		}
		return result;
	}
	
	public static Vector<Point> create_polygon_result(Vector<Pair<Point,Boolean>> nuage1, Vector<Pair<Point,Boolean>> nuage2, int i)
	{
		Vector<Point> result = new Vector<Point>();//vecteur de point result
		
		Point current = new Point();//point courant a ajouter aux resultats
		current = nuage2.elementAt(i).obj1;//initialisation
		
		Pair<Point,Boolean> tmp = new Pair<Point,Boolean>(nuage2.elementAt(i).obj1, true);//tmp = marqueur de visite des points
		boolean current_polygone = true;// true = polygone2, false = polygone1
		
		while((current_polygone && !nuage2.elementAt(i).obj2) || (!current_polygone && !nuage1.elementAt(i).obj2))//tant que le point n'est pas deja visité
		{
			result.add(current);
			//recherche du current prochain
			if(current_polygone)//polygone2
			{
				tmp = nuage2.elementAt(i);
				tmp.obj2 = true;
				nuage2.setElementAt(tmp, i);// marquer comme visité
				
				++i;
				if (i >= nuage2.size()) i = 0;
				
				current = nuage2.elementAt(i).obj1;
				if(current.nom.equals("intersection"))//intersection, se placer sur l'autre polygone
				{
					tmp = nuage2.elementAt(i);
					tmp.obj2 = true;
					nuage2.setElementAt(tmp, i);// marquer comme visité
					current_polygone = false;
					i = 0;
			
					while(nuage1.elementAt(i).obj1.x != current.x && nuage1.elementAt(i).obj1.y != current.y) ++i;
				}
			}
			else//polygone1
			{
				tmp = nuage1.elementAt(i);
				tmp.obj2 = true;
				nuage1.setElementAt(tmp, i);// marquer comme visité
				++i;
				if (i >= nuage1.size()) i = 0;
				
				current = nuage1.elementAt(i).obj1;
				
				if(current.nom.equals("intersection"))//intersection, se placer sur l'autre polygone
				{
					tmp = nuage1.elementAt(i);
					tmp.obj2 = true;
					nuage1.setElementAt(tmp, i);// marquer comme visité
					current_polygone = true;
					
					i = 0;
					while(nuage2.elementAt(i).obj1.x != current.x && nuage2.elementAt(i).obj1.y != current.y) ++i;
				}
			}
		}
		return result;
	}
	
	public static void inserer_proche(Vector<Pair<Point,Boolean>> nuage , Pair<Point,Boolean> point, int indice)
	{
		int indice2 = indice + 1;
		
		while(indice2 < nuage.size() && distance(nuage.elementAt(indice).obj1, nuage.elementAt(indice2).obj1) < distance(nuage.elementAt(indice).obj1, point.obj1)) ++indice2;
		nuage.add(indice2, point);
	}
	public static Vector<Vector<Point>> Weiler (Vector<Point> nuage1, Vector<Point> nuage2)
	{
		Vector<Vector<Point>> result = new Vector<Vector<Point>>();
		
		Vector<Pair<Point,Boolean>> new_nuage1 = new Vector<Pair<Point,Boolean>>();// nouveau nuage de point du polygone
		Vector<Pair<Point,Boolean>> new_nuage2 = new Vector<Pair<Point,Boolean>>();// nouveau nuage de point du polygone
		
		// recherche des points d'intersection des polygones
		int indice;
		for(int i =0; i<nuage1.size(); ++i)
		{
			indice = new_nuage1.size();
			new_nuage1.add(new Pair<Point,Boolean>(nuage1.elementAt(i),false));
			for(int j=0; j<nuage2.size(); ++j)
			{
				
				int ni = (i== nuage1.size() -1)? 0 : i+1;
				int nj = (j== nuage2.size() -1)? 0 : j+1;
				Segment s1 = new Segment(nuage1.elementAt(i), nuage1.elementAt(ni));
				Segment s2 = new Segment(nuage2.elementAt(j), nuage2.elementAt(nj));
				
				Point intersect = intersect(s1, s2);
				if (intersect != null)
				{	
					intersect.nom = "intersection";
					Pair<Point,Boolean> intersect_pair = new Pair<Point,Boolean>(intersect,false);
					inserer_proche(new_nuage1, intersect_pair, indice);
				}
			}
		}
		
		for(int i =0; i<nuage2.size(); ++i)
		{
			indice = new_nuage2.size();
			new_nuage2.add(new Pair<Point,Boolean>(nuage2.elementAt(i),false));
			for(int j=0; j<nuage1.size(); ++j)
			{
				
				int ni = (i== nuage2.size() -1)? 0 : i+1;
				int nj = (j== nuage1.size() -1)? 0 : j+1;
				Segment s1 = new Segment(nuage2.elementAt(i), nuage2.elementAt(ni));
				Segment s2 = new Segment(nuage1.elementAt(j), nuage1.elementAt(nj));
				
				Point intersect = intersect(s1, s2);
				if (intersect != null)
				{	
					intersect.nom = "intersection";
					Pair<Point,Boolean> intersect_pair = new Pair<Point,Boolean>(intersect,false);
					inserer_proche(new_nuage2, intersect_pair, indice);
				}
			}
		}
		// fin de la recherche
		
		boolean inbound = false;
		for(int i =0; i<new_nuage2.size(); ++i)
		{
			if (!new_nuage2.elementAt(i).obj1.nom.equals("intersection"))continue;// pas une intersection, on continue
			//intersection , on change d'état(si on est dedans, on sort, et inversement)
			inbound = !inbound;
			if(inbound && !new_nuage2.elementAt(i).obj2)//dedans et non visité, on crée un nouveau polygone resultat
			{
				result.add(create_polygon_result(new_nuage1, new_nuage2,i));
			}
		}
		return result;
		
	}
	
	/** Retourne un nombre aleatoire entre 0 et n-1. */
	static int rand(int n)
	{
		int r = new Random().nextInt();
		
		if (r < 0) r = -r;
		
		return r % n;
	}
}

