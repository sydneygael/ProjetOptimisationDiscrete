package Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Algorithmes.Solution;
import Formation.Agence;
import Formation.Carte;
import Formation.CentreFormation;

public class Utilitaires {

	public static Carte carte ;

	private static List<Agence> agences;

	private static List<CentreFormation> centresDeFormation;

	private Random random = new Random();

	/**
	 * construction de la premi�re solution
	 * @return
	 * @throws IOException 
	 */

	public static Solution premiereSolution () throws IOException {

		//Solution une
		Solution s1 = new Solution();

		//la carte
		carte =new Carte();
		carte.getAgencesFromFile("Ressources/ListeAgences_100.txt");
		carte.getCentreFormationFromFile("Ressources/LieuxPossibles.txt");
		agences = carte.getListAgences();
		centresDeFormation = carte.getListCentreFormation();

		do {

			//on prend le premier centre de formation

			CentreFormation centreEnTete = centresDeFormation.remove(0);

			System.out.println("Prise d'un centre du centre de formation "
					+ centreEnTete.getNom() + " id : "
					+ centreEnTete.getId());

			List<Agence> agencesSolution = chercherDisposition(centreEnTete);
			System.out.println("recherche et ajout de la disposition pour id : "
					+ centreEnTete.getId());
			s1.ajouterUneDisposition(centreEnTete, agencesSolution);

		}
		while(!centresDeFormation.isEmpty() && !agences.isEmpty());

		System.out.println();
		System.out.println("nb villes total = "+s1.getNb_Villes());
		return s1;

	}

	public static Solution genererSolutionAleatoire() {

		return null;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
		{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				return (o1.getValue()).compareTo( o2.getValue() );
			}
		} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}

	private static List<Agence> chercherDisposition(CentreFormation centreEnTete) {

		//map pour les distances � trier
		Map <Agence,Double> distancesTri = new HashMap<Agence, Double>();

		if ( !agences.isEmpty()){

			//on calcul les distances
			for ( Agence agence : agences) {

				//on �vite le cas ou l'agence est dej� un lieu de formation
				if ( centreEnTete.equals(agence)) {

					try {
						centreEnTete.addNbEmployes(agence.getNbEmploye());
						agences.remove(agence);
					} catch (AjoutException e) {
						e.printStackTrace();
					}
				}

				else {

					double distance = centreEnTete.distance(agence);
					distancesTri.put(agence, distance);
				}
			}

			// une fois qu'on a fini de calculer les distances on tri
			Map<Agence, Double> result = Utilitaires.sortByValue(distancesTri);
			distancesTri=result;
			//
			//			for ( Double d : distancesTri.values()) {
			//				System.out.println("vleur dista" +" : " +d+" km" );
			//			}
		}

		// apr�s avoir trier on construit une solution 

		List<Agence> agencesSolution = new ArrayList<Agence>() ;
		int compteur=centreEnTete.getNbEmployes();

		for (Agence a : distancesTri.keySet()) {
			compteur+=a.getNbEmploye();

			if (compteur <= 60) {
				agencesSolution.add(a);
				//on enl�ve de la liste les agences d�j� trait�es
				agences.remove(a);
			}
		}

		return agencesSolution;
	}

}
