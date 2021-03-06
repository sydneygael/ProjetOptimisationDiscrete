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

import Algorithmes.Population;
import Algorithmes.Solution;
import Algorithmes.Solution;
import Formation.Agence;
import Formation.Carte;
import Formation.CentreFormation;

public class Utilitaires {

	public static final String RESSOURCES_LIEUX_POSSIBLES = "Ressources/LieuxPossibles.txt";

	public static final String RESSOURCES_LISTE_AGENCES = "Ressources/ListeAgences_300.txt";

	public static Carte carte = new Carte();

	private static List<Agence> agences;

	private static List<CentreFormation> centresDeFormation;

	private static Random random = new Random();

	private static int randomIndex;


	public static Solution genererUneSolutionSimple () throws IOException {

		carte.getAgencesFromFile(RESSOURCES_LISTE_AGENCES);
		carte.getCentreFormationFromFile(RESSOURCES_LIEUX_POSSIBLES);

		//la carte

		agences = carte.getListAgences();
		centresDeFormation = carte.getListCentreFormation();
		Solution s = new Solution(carte);
		int nbCentres=0 ;

		do {

			randomIndex = random.nextInt(centresDeFormation.size());
			CentreFormation centreAleatoire = centresDeFormation.remove(randomIndex);
			System.out.println("-----------------------------------------");
			System.out.println("Prise d'un centre du centre de formation "
					+ centreAleatoire.getNom() + " id : "
					+ centreAleatoire.getId());
			System.out.println("-----------------------------------------");
			System.out.println();
			List<Agence> agencesSolution = chercherAgencesLesPlusProches(centreAleatoire);
			System.out.println("recherche et ajout de la disposition pour id : "
					+ centreAleatoire.getId());
			centreAleatoire.setAgencesAssociees(agencesSolution);

			for (Agence a : agencesSolution) {
				s.ajouterDispositon(a,centreAleatoire);
			}

			nbCentres++;
			s.centresDansLaSolution.add(centreAleatoire);
			s.calculerFitness();
		}
		
		while(!centresDeFormation.isEmpty() && !agences.isEmpty());
		s.setNbCentres(nbCentres);

		return s;
	}
	
	public static void afiicherSolution (Solution s) {
		
		System.out.println(" ------------affichage de la solution---------------");
		for (CentreFormation c : s.centresDansLaSolution) {
			
			System.out.println(" agences associ�es aux centre : "+c.getId());
			System.out.println();
			List<Agence> list = c.getAgencesAssociees();
			
			for (Agence a: list) {
				System.out.println(a.getId());
			}
			System.out.println();
		}
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		Utilitaires.random = random;
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

	private static List<Agence> chercherAgencesLesPlusProches(CentreFormation centreEnTete) {

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
		}

		// une fois qu'on a fini de calculer les distances on tri
		Map<Agence, Double> result = Utilitaires.sortByValue(distancesTri);
		distancesTri=result;

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

	public static List<Agence> chercherAgencesLesPlusProches(CentreFormation centreEnTete,
			List<Agence> agencesListe) {
		//map pour les distances � trier
		Map <Agence,Double> distancesTri = new HashMap<Agence, Double>();

		if ( !agencesListe.isEmpty()){

			//on calcul les distances
			for ( Agence agence : agencesListe) {

				//on �vite le cas ou l'agence est dej� un lieu de formation
				if ( centreEnTete.equals(agence)) {

					try {
						centreEnTete.addNbEmployes(agence.getNbEmploye());
						agencesListe.remove(agence);
					} catch (AjoutException e) {
						e.printStackTrace();
					}
				}

				else {

					double distance = centreEnTete.distance(agence);
					distancesTri.put(agence, distance);
				}
			}	
		}

		// une fois qu'on a fini de calculer les distances on tri
		Map<Agence, Double> result = Utilitaires.sortByValue(distancesTri);
		distancesTri=result;

		// apr�s avoir trier on construit une solution 

		List<Agence> agencesSolution = new ArrayList<Agence>() ;
		int compteur=centreEnTete.getNbEmployes();

		for (Agence a : distancesTri.keySet()) {
			compteur+=a.getNbEmploye();

			if (compteur <= 60) {
				agencesSolution.add(a);
				//on enl�ve de la liste les agences d�j� trait�es
				agencesListe.remove(a);
			}
		}

		return agencesSolution;
	}

	public static Population construitrePopulation(int taille) {
		return null;
	}

}
