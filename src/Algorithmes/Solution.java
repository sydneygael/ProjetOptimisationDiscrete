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
public class Solution implements Comparable<Solution> {

	protected Map<CentreFormation, List<Agence>> disposition;
	protected double fitness;
	protected List<CentreFormation> list; // stocker les clees de la map

	public Solution () {
		disposition = new LinkedHashMap<CentreFormation, List<Agence>>();
	}

	/**
	 * permet de générer un voisinage
	 * @param nombreDeVoisins
	 * @return
	 */
	public List<Solution> genererVoisinage(int nombreDeVoisins) {
		List<Solution> voisinage = new ArrayList<Solution>();

		for ( int i=0 ; i < nombreDeVoisins ; i++) {
			Solution s = contruireVoisinAleatoire();

			while (!s.dispositionPossible()) {
				s = contruireVoisinAleatoire();
			}
			voisinage.add(s);
		}
		return voisinage;
	}

	/**
	 * operation génétique de la mutation
	 * @param centreMuteur
	 * @return 
	 */
	public CentreFormation mutation(CentreFormation centreMuteur) {
		Random r = new Random();
		int indexAmuter = r.nextInt(list.size());
		CentreFormation aMuter = getCentreFormationFromMap(indexAmuter);
		List <Agence> listeDuCentre = disposition.get(aMuter);

		if ( disposition.remove(aMuter, listeDuCentre)) {
			disposition.put(centreMuteur, listeDuCentre);
		}

		calculerFitness();
		return aMuter;
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
		list = new ArrayList<CentreFormation>(disposition.keySet());
		for ( CentreFormation c: list){
			if (!c.verifierCentre()) return false;
		}
		
		return true;
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
		int nbcentres = disposition.keySet().size();
		double coutFitness = nbcentres * Lieux.COUT_TOTAL;

		for (Entry<CentreFormation, List<Agence>> entry : disposition.entrySet()) {

			List<Agence> values = entry.getValue();

			for (Agence agence : values) {
				CentreFormation lieu = entry.getKey();
				coutFitness+=Lieux.COUT_EMP*2*lieu.distance(agence);
			}
		}
		this.fitness=coutFitness;
	}
	

	public  Map<Integer, Agence> tirageAleatoireAgence(int indexCentre, int nbechanges) {

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

	@Override
	public int compareTo(Solution o) {
		return (int) (getFitness() - o.getFitness());
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

		list = new ArrayList<CentreFormation>(disposition.keySet());
		CentreFormation c1 = getCentreFormationFromMap(indexCentre1);
		CentreFormation c2 = getCentreFormationFromMap(indexCentre2);
		//echange dans la solution aleatoire
		aleatoire.echangerAgenceEntreCentreDeFormation(c1, c2);

		//pour finir on calcul la nouvelle valeur de la fitness
		aleatoire.calculerFitness();
		return aleatoire;
	}

	private void echangerAgenceEntreCentreDeFormation(CentreFormation c1 ,CentreFormation c2) {

		List<Agence> associeesAC1 = c1.getAgencesAssociees();
		List<Agence> associeesAC2 = c2.getAgencesAssociees();
		Random alea = new Random();
		int indexVilleChoisie1 = alea.nextInt(associeesAC1.size());
		int indexVilleChoisie2 = alea.nextInt(associeesAC2.size());

		Agence duCentre1 = associeesAC1.get(indexVilleChoisie1);
		Agence duCentre2 = associeesAC2.get(indexVilleChoisie2);

		associeesAC1.set(indexVilleChoisie1, duCentre2);
		associeesAC2.set(indexVilleChoisie2, duCentre1);

		c1.setAgencesAssociees(associeesAC1);
		c2.setAgencesAssociees(associeesAC2);

		int testtaille = disposition.entrySet().size();
		disposition.put(c1, associeesAC1);
		disposition.put(c2, associeesAC2);
		int testtaille2 = disposition.entrySet().size();

		if (testtaille != testtaille2) {
			System.out.println();
			System.out.println();
			System.out.println("*****************");
			System.out.println("ERROR!!!!!!!!!!!");
			System.out.println("*****************");
			System.out.println();
			System.out.println();

		}
	}

	private CentreFormation getCentreFormationFromMap(int indexCentre) {
		list = new ArrayList<CentreFormation>(disposition.keySet());
		return list.get(indexCentre);
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
}
