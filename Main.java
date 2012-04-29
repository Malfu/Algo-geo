package tp_geometrie1;
import java.awt.Dimension;

import javax.swing.JFrame;

/** La classe principale. */
public class Main  {
	
	/** La methode main. */
	public static void main(String[] args) {
		// Construction de la fenetre
		JFrame frame = new JFrame("Saisir des points et afficher des segments en resultat");
		
		// Construction de la zone d'affichage
		ZoneSaisirPointsAfficherSegments zoneAffichage = new ZoneSaisirPointsAfficherSegments();
		
		// Ajout de la zone d'affichage a la fenetre
		frame.getContentPane().add(zoneAffichage);
		 
		// Dimension de la zone d'affichage
		zoneAffichage.setPreferredSize(new Dimension(1200,600));
		
		// Resize autour de la zone d'affichage
		frame.pack();
		
		// Affichage de la fenetre
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		AVLTree<Integer> lol1 = new AVLTree<Integer>();
		for(int i = 0, j = 10; i < 100; i +=10, j+=10)
		lol1.insert(new Integer(i));
		
		System.out.println(lol1.toString());
		Integer test1 = new Integer(10);
		System.out.println(lol1.contains(test1));
		
		AVLTree<Segment> lol = new AVLTree<Segment>();
		for(double i = 0, j = 10; i < 100; i +=10, j+=10)
		lol.insert(new Segment(new Point(j, i), new Point(j, i)));
		
		System.out.println(Segment.assemble(lol.getRootAbove(), 0));
		Segment test = new Segment(new Point(0,10), new Point(0,10));
		System.out.println(lol.contains(test));
		*/
	}
}
