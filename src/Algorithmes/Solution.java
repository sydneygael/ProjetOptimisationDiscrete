package Algorithmes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import Formation.Agence;
import Formation.CentreFormation;
import Formation.Lieux;

public class Solution {

	private Map<CentreFormation, List<Agence>> disposition;
	private double fitness;

	public Solution () {
		disposition = new LinkedHashMap<CentreFormation, List<Agence>>();
	}


	public List<Solution> genererVoisinage(int nombreDeVoisins) {
		List<Solution> voisinage = new ArrayList<Solution>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			Solution s = ContruireVoisinAleatoire();
			voisinage.add(s);
		}
		return voisinage;
	}



	public Map<CentreFormation, List<Agence>> getDisposition() {
		return disposition;
	}

	public void setDisposition(Map<CentreFormation, List<Agence>> disposition) {
		this.disposition = disposition;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public List<Agence> getValueForEntry(int id){

		Iterator<Entry<CentreFormation, List<Agence>>> iterator = disposition.entrySet().iterator();
		int n = 0;
		while(iterator.hasNext()){
			if(n == id){
				return iterator.next().getValue();
			}
			n ++;
		}
		return null;
	}
	
	public void echangerAgenceAvecSolution(List<Agence> l , CentreFormation c) {
		
	}

	/**
	 * permet d'ajouter un centre de formation et les villes associées
	 * @param cle
	 * @param valeur
	 */
	public void ajouterUneDisposition(CentreFormation cle , List<Agence> valeur){
		disposition.put(cle, valeur);
	}

	public int getTotalEmployes () {

		int total=0;
		for (CentreFormation c : disposition.keySet() ) {
			total+=c.getNbEmployes();
		}
		return total;
	}

	public int getNb_Villes() {
		int total=0;
		for (List<Agence>l : disposition.values()){
			total+=l.size();
		}
		return total;
	}

	public void calculerFitness() {
		int coutFitness = 0;

		for (Entry<CentreFormation, List<Agence>> entry : disposition.entrySet()) {

			for (Agence agence : entry.getValue()) {
				CentreFormation lieu = entry.getKey();
				int cout_transport = (int) (agence.getNbEmploye() * lieu.distance(agence)* Lieux.COUT_EMP);
				coutFitness += cout_transport;
			}
			coutFitness+=+Lieux.COUT_TOTAL;
		}
		this.fitness=coutFitness;
	}

	/**
	 * conrtuit un voisin
	 * consiste à échanger des villes entre centres
	 * @return
	 */
	private Solution ContruireVoisinAleatoire() {

		Random random = new Random();
		Solution aleatoire = new Solution();
		aleatoire.setDisposition(disposition);

		int tailleCentre= disposition.keySet().size();

		//on choisit un centre de la solution au hasard
		int indexCentre1= random.nextInt(tailleCentre);
		//on en choisit une deuxieme
		int indexCentre2 = random.nextInt(tailleCentre);

		// tant que ce ne sont pas des centres différents on regénérère
		while ( indexCentre1 == indexCentre2) {
			indexCentre1= random.nextInt(tailleCentre);
			indexCentre2= random.nextInt(tailleCentre);
		}

		//on choisit ensuite deux agences à echanger entre centres
		Map <Integer,Agence> a1 = tirageAleatoireAgence(indexCentre1);
		Map <Integer,Agence> a2 = tirageAleatoireAgence(indexCentre2);

		while( a1 == null || a2 == null) {
			a1 = tirageAleatoireAgence(indexCentre1);
			a2 = tirageAleatoireAgence(indexCentre2);
		}

		//echange dans la solution aleatoire
		aleatoire.echangerAgencesEntreCentre(indexCentre1,indexCentre2,a1,a2,aleatoire);


		//pour finir on calcul la nouvelle valeur de la fitness
		aleatoire.calculerFitness();
		return aleatoire;
	}


	private void echangerAgencesEntreCentre(int indexCentre1, int indexCentre2, Map<Integer, Agence> a1,
			Map<Integer, Agence> a2,Solution aleatoire) {

		List <Agence> l1 ,l2;
		l1=l2=null;

		try {
			l1 = getValueForEntry(indexCentre1) ;
			l2 = getValueForEntry(indexCentre2) ;

			for ( Entry<Integer, Agence> set1 : a1.entrySet()) {
				l2.set(set1.getKey(), set1.getValue());
			}

			for ( Entry<Integer, Agence> set2 : a2.entrySet()) {
				l1.set(set2.getKey(), set2.getValue());
			}

			CentreFormation c1=getCentreFormationFromMap(indexCentre1);
			CentreFormation c2=getCentreFormationFromMap(indexCentre2);

			disposition.put(c1, l1);
			disposition.put(c2, l2);

		}
		catch ( IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}


	private CentreFormation getCentreFormationFromMap(int indexCentre) {
		Iterator<Entry<CentreFormation, List<Agence>>> iterator = disposition.entrySet().iterator();
		int n = 0;
		while(iterator.hasNext()){
			if(n == indexCentre){
				return iterator.next().getKey();
			}
			n ++;
		}
		return null;
	}


	private Map<Integer, Agence> tirageAleatoireAgence(int indexCentre) {

		Map<Integer, Agence> agence= new HashMap<Integer, Agence>();

		//on  prends les agences associées au centre de formation
		List<Agence> agencesAssocieesAUnCentre = getValueForEntry(indexCentre);

		if (agencesAssocieesAUnCentre != null) {
			Random villeRandom = new Random();
			int indexVille = villeRandom.nextInt(agencesAssocieesAUnCentre.size());
			Agence valeur = agencesAssocieesAUnCentre.get(indexVille);
			agence.put(indexVille, valeur);
			return agence;	
		}
		else {
			return null;
		}
	}

	private Map<Integer, Agence> tirageAleatoireAgence(int indexCentre, int nbechanges) {

		Map<Integer, Agence> agence= new HashMap<Integer, Agence>();

		//on  prends les agences associées au centre de formation
		List<Agence> agencesAssocieesAUnCentre = getValueForEntry(indexCentre);


		if (agencesAssocieesAUnCentre != null) {
			for ( int i=0;i<nbechanges;i++) {
				Random villeRandom = new Random();
				int indexVille = villeRandom.nextInt(agencesAssocieesAUnCentre.size());
				Agence valeur = agencesAssocieesAUnCentre.get(indexVille);
				agence.put(indexVille, valeur);
			}
			return agence;	
		}
		else {
			return null;
		}
	}

}
