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
/**
 * 
 * @author Sydney
 *
 */
public class Solution {

	private Map<CentreFormation, List<Agence>> disposition;
	private double fitness;

	public Solution () {
		disposition = new LinkedHashMap<CentreFormation, List<Agence>>();
	}


	public List<Solution> genererVoisinage(int nombreDeVoisins) {
		List<Solution> voisinage = new ArrayList<Solution>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			Solution s = contruireVoisinAleatoire();
			
			while (s.dispositionPossible()==false) {
				s = contruireVoisinAleatoire();
			}
			voisinage.add(s);
		}
		return voisinage;
	}
	
	public List<Solution> genererVoisinage(int nombreDeVoisins,Solution s2) {
		List<Solution> voisinage = new ArrayList<Solution>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			Solution s = construireVoisinAleatoire(s2);
			
			while (s.dispositionPossible()==false) {
				s = construireVoisinAleatoire(s2);
			}
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

	public boolean dispositionPossible () {

		for ( Entry<CentreFormation, List<Agence>> entree : disposition.entrySet()) {

			int cpt =0;
			List<Agence> agences = entree.getValue();

			for ( Agence a : agences ) {
				cpt+=a.getNbEmploye();
			}

			if (cpt>60) return false;
		}
		return true;
	} 
	public Solution construireVoisinAleatoire(Solution s2) {

		Solution alea = new Solution();

		// tirage aléatoire
		Random r = new Random();
		int indexCentreSolution1 = r.nextInt(this.disposition.size());
		int indexCentreSolution2 = r.nextInt(s2.getDisposition().size());

		Map<Integer, Agence> coupleAgenceS1 = this.tirageAleatoireAgence(indexCentreSolution1);
		Map<Integer, Agence> coupleAgenceS2 = s2.tirageAleatoireAgence(indexCentreSolution2);

		CentreFormation c1=getCentreFormationFromMap(indexCentreSolution1);

		List <Agence> l1 = this.disposition.get(c1);

		int index1 = -1,index2= -1 ;
		Agence agenceAleatoire = null;

		// on récupère les valeurs du tirage
		for (Entry<Integer, Agence> entry1 : coupleAgenceS1.entrySet() ) {
			index1 = entry1.getKey();
		}

		for (Entry<Integer, Agence> entry2 : coupleAgenceS2.entrySet() ) {
			index2 = entry2.getKey();
			agenceAleatoire = entry2.getValue();
		}

		// on construit la nouvelle solution
		if ( index1 != -1 && index2 != -1 && agenceAleatoire != null) {
			l1.set(index1, agenceAleatoire);
		}

		alea.setDisposition(disposition);
		alea.ajouterUneDisposition(c1, l1);
		alea.calculerFitness();

		return alea;

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
	private Solution contruireVoisinAleatoire() {

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
