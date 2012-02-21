package tp_geometrie1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** La classe ZoneSaisirPointsAfficherSegments. */
public class ZoneSaisirPointsAfficherSegments extends JPanel  {

	/** Creation de la zone d'affichage. */
	public ZoneSaisirPointsAfficherSegments()
	{
		// Le canvas d'affichage
		final CanvasSaisirPointsAfficherSegments canvas = new CanvasSaisirPointsAfficherSegments(); 
		
		// Panel des boutons
		JPanel panelBoutons = new JPanel();
		
		
		// Creation du bouton Effacer
		JButton effacer = new JButton("Effacer");
		
		// Creation du bouton Polygone suivant
		final JButton suivant = new JButton("Polygone suivant (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")" );
		
		// Creation du bouton Polygone Precedent
		final JButton precedent = new JButton("Polygone précédent (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
		
		// Action du bouton Effacer
		effacer.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					// Suppression des points et des segments
					canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).removeAllElements();
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).removeAllElements();
					canvas.diagonales.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).removeAllElements();
					canvas.repaint();
					if (CanvasSaisirPointsAfficherSegments.polygone_courant > 0 ) --CanvasSaisirPointsAfficherSegments.polygone_courant;
					suivant.setText("Polygone suivant (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
					precedent.setText("Polygone precedent (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
				}
			}
		);
		
		// Action du bouton suivant
		suivant.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					canvas.polygoneSuivant();
					suivant.setText("Polygone suivant (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
					precedent.setText("Polygone precedent (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
				}
			}
		);
		
		// Action du bouton precedent
		precedent.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					canvas.polygonePrecedent();
					suivant.setText("Polygone suivant (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
					precedent.setText("Polygone precedent (" + CanvasSaisirPointsAfficherSegments.polygone_courant + ")");
				}
			}
		);
		
		// Creation du bouton Trianguler
		JButton trianguler = new JButton("Trianguler");
		
		// Action du bouton trianguler
		trianguler.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					canvas.diagonales.setElementAt(Algorithmes.trianguler(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)), CanvasSaisirPointsAfficherSegments.polygone_courant);
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
					canvas.repaint();
				}
			}
		);
		
		// Creation du bouton QuickHull
		JButton quickHull = new JButton("QuickHull");
		
		// Action du bouton QuickHull
		quickHull.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					Vector<Vector<Point>> tmp = new Vector<Vector<Point>>();
					tmp = (Vector<Vector<Point>>)canvas.points.clone();
					canvas.points.setElementAt(Algorithmes.initQuickHull(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)) , CanvasSaisirPointsAfficherSegments.polygone_courant);
					canvas.calculer();
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
					canvas.repaint();
					canvas.points = tmp; // on remet les points initiaux
				}
			}
		);
		
		// Creation du bouton Graham
		JButton graham = new JButton("Graham");
		
		// Action du bouton graham
		graham.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					Vector<Vector<Point>> tmp = new Vector<Vector<Point>>();
					tmp = (Vector<Vector<Point>>)canvas.points.clone();
					canvas.points.setElementAt(Algorithmes.graham(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)) , CanvasSaisirPointsAfficherSegments.polygone_courant);
					canvas.calculer();
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
					canvas.repaint();
					canvas.points = tmp; // on remet les points initiaux
				}
			}
		);
		
		// Creation du bouton Jarvis
		JButton jarvis = new JButton("Jarvis");
		
		// Action du bouton jarvis
		jarvis.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					Vector<Vector<Point>> tmp = new Vector<Vector<Point>>();
					tmp = (Vector<Vector<Point>>)canvas.points.clone();
					canvas.points.setElementAt(Algorithmes.jarvis(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)) , CanvasSaisirPointsAfficherSegments.polygone_courant);
					canvas.calculer();
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
					canvas.repaint();
					canvas.points = tmp; // on remet les points initiaux
				}
			}
		);
		
		// Creation du bouton partitionFusion
		JButton partitionFusion = new JButton("partitionFusion");
		
		// Action du bouton partition fusion
		partitionFusion.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					Vector<Vector<Point>> tmp = new Vector<Vector<Point>>();
					tmp = (Vector<Vector<Point>>)canvas.points.clone();
					canvas.points.setElementAt(Algorithmes.partition(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)) , CanvasSaisirPointsAfficherSegments.polygone_courant);
					canvas.calculer();
					canvas.segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(canvas.points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
					canvas.repaint();
					canvas.points = tmp; // on remet les points initiaux
				}
			}
		);
		// Ajout des boutons au panel panelBoutons
		panelBoutons.add(effacer);
		panelBoutons.add(precedent);
		panelBoutons.add(suivant);
		panelBoutons.add(trianguler);
		panelBoutons.add(quickHull);
		panelBoutons.add(graham);
		panelBoutons.add(jarvis);
		panelBoutons.add(partitionFusion);
		setLayout(new BorderLayout());
		
		// Ajout du canvas au centre
		add(canvas, BorderLayout.CENTER);
		
		// Ajout des boutons au nord
		add(panelBoutons, BorderLayout.SOUTH);
	}
}

/** La classe CanvasSaisirPointsAfficherSegments. */
class CanvasSaisirPointsAfficherSegments extends JPanel implements MouseListener, MouseMotionListener {
	/** La liste des points affiches. */
	Vector<Vector<Point>> points;
	
	/** La liste des segments affiches. */
	Vector<Vector<Segment>> segments;
	
	Vector<Vector<Segment>> diagonales;
	
	public static int polygone_courant = 0;
	/** Le numero du point selectionne. */
	private int numSelectedPoint;
	
	/** La couleur d'un point a l'ecran. */
	private final Color pointColor = Color.GRAY;
	
	/** La couleur d'un segment a l'ecran. */
	private final Color segmentColor = Color.BLUE;
	
	private final Color diagColor = Color.BLACK;
	
	/** La couleur d'un point selectionne a l'ecran. */
	private final Color selectedPointColor = Color.RED;

	/** La taille d'un point a l'ecran. */
	private final int POINT_SIZE = 2;
	
	/** Creation de la zone d'affichage. */
	public CanvasSaisirPointsAfficherSegments()
	{
		// Creation du vecteur de points
		points = new Vector<Vector<Point>>();
		points.add( new Vector<Point>());
		
		// Creation du vecteur de segments
		segments = new Vector<Vector<Segment>>();
		segments.add( new Vector<Segment>());
		
		diagonales = new Vector<Vector<Segment>>();
		diagonales.add( new Vector<Segment>());
		// Initialisation du point selectionne
		numSelectedPoint = -1;
		
		// Initialisation de la couleur de fond
		setBackground(Color.WHITE);
		
		// Ajout de la gestion des actions souris
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	void polygoneSuivant()
	{
		++ polygone_courant;
		if (polygone_courant >= points.size())
		{
			points.add( new Vector<Point>());
		
			segments.add( new Vector<Segment>());
			
			diagonales.add( new Vector<Segment>());
		}
			
	}
	
	void polygonePrecedent()
	{
		if (polygone_courant > 0)
		-- polygone_courant;
		else System.out.println("pas de polygone precedent");
	}
	
	/** Dessin de la zone d'affichage. */
	public void paint(Graphics g) {
		// Efface le fond
		g.clearRect(0,0,getWidth(),getHeight());
		
		// Dessin des segments 
		drawSegments(g);
		
		// Dessin des diagonales
		drawDiag(g);
		// Dessin des points
		drawPoints(g);
	}

	/** Affichage des points. */
	private void drawPoints(Graphics g) {
		for (int n = 0; n < points.size(); n++) {
			for(int m = 0; m < points.elementAt(n).size(); ++m){
				Point p = points.elementAt(n).elementAt(m);
			
			if ( n == numSelectedPoint ) 
				g.setColor(selectedPointColor);
			else
				g.setColor(pointColor);
			
			g.fillOval((int)(p.x - POINT_SIZE), (int)(p.y - POINT_SIZE), 2 * POINT_SIZE + 1, 2 * POINT_SIZE + 1);
			g.drawOval((int)(p.x - 2 * POINT_SIZE), (int)(p.y - 2 * POINT_SIZE), 2 * 2 * POINT_SIZE,	2 * 2 * POINT_SIZE);
			g.drawString(p.nom, (int)p.x + 10, (int)p.y + 10);
		}
		}
	}
	
	/** Affichage des segments.
	 */
	private void drawSegments(Graphics g) {
		for (int n = 0; n < segments.size(); n++) {
			for(int m = 0; m < segments.elementAt(n).size(); ++m){
			Segment segment = segments.elementAt(n).elementAt(m);
			g.setColor(segmentColor);
			g.drawLine((int)segment.a.x,(int)segment.a.y,(int)segment.b.x,(int)segment.b.y);
		}
		}
	}
	
	private void drawDiag(Graphics g)
	{
		for (int n = 0; n < diagonales.size(); n++) {
			for(int m = 0; m < diagonales.elementAt(n).size(); ++m){
			Segment segment = diagonales.elementAt(n).elementAt(m);
			g.setColor(diagColor);
			g.drawLine((int)segment.a.x,(int)segment.a.y,(int)segment.b.x,(int)segment.b.y);
		}
		}
	}
	
	/** Retourne le numero du point situe en (x,y). */
	private int getNumSelectedPoint(int x, int y) {
		for(int n = 0; n < points.size(); n++)
			for(int m = 0; m < points.elementAt(n).size(); ++m){
		{
			Point p = points.elementAt(n).elementAt(m);
			if
			(
				p.x > x - 2 * POINT_SIZE && 
				p.x < x + 2 * POINT_SIZE &&
				p.y > y - 2 * POINT_SIZE && 
				p.y < y + 2 * POINT_SIZE
			)
				return n;
		}
		}
		return -1;
	}

	/** Un point est ajoute si on presse le bouton de gauche
	 * 	et si aucun point n'est selectionne.
	 * 	Un point est suuprime si on presse un autre bouton
	 * 	et si un point est selectionne.
	 */
	public void mousePressed(MouseEvent evt) {
		if ( evt.getButton() == 1 )
		{
			if (numSelectedPoint == -1)
			{
				numSelectedPoint = points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size();
				points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).addElement(new Point(evt.getX(), evt.getY(), "p" + numSelectedPoint));
				calculer();
				segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
				repaint();
			}
		}
		else
		{
			if (numSelectedPoint != -1)
			{
				points.removeElementAt(numSelectedPoint);
				numSelectedPoint = getNumSelectedPoint(evt.getX(), evt.getY());
				calculer();
				repaint();
			}
		}
	}
	
	/** Le x et y du point numSelectedPoint est modifie si
	 * 	la souris change de position avec un bouton enfonce.
	 */
	public void mouseDragged(MouseEvent evt) {
		if (numSelectedPoint != -1)
		{
			points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(numSelectedPoint).x = evt.getX();
			points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(numSelectedPoint).y = evt.getY();
			calculer();
			segments.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).add(new Segment (points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(0), points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).elementAt(points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant).size()-1)));
			repaint();
		}
	}
	
	/** Le numSelectedPoint est calcule si
	 * 	la souris change de position sans bouton enfonce.
	 */
	public void mouseMoved(MouseEvent evt) {
		numSelectedPoint = getNumSelectedPoint(evt.getX(), evt.getY());
		repaint();
	}
	
	/** Lance l'algorithme sur l'ensemble de points. */
	public void calculer()
	{
		segments.setElementAt(Algorithmes.algorithme1(points.elementAt(CanvasSaisirPointsAfficherSegments.polygone_courant)) , CanvasSaisirPointsAfficherSegments.polygone_courant);
	}
	
	public void mouseReleased(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
}
