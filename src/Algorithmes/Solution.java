package Algorithmes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import Formation.Agence;
import Formation.CentreFormation;
import Formation.Lieux;

public class Solution {

	private Map<CentreFormation, List<Agence>> disposition;
	private double fitness;
	Random random = new Random();

	public Solution () {
		disposition = new LinkedHashMap<CentreFormation, List<Agence>>();
	}


	public Solution genererVoisinage(int nombreDeVoisins) {

		for ( int i=0 ; i < nombreDeVoisins ; i++) {

			Solution s = ContruireVoisinAleatoire();
		}

		return null;
	}

	/**
	 * consiste à échanger des villes entre centres
	 * @return
	 */
	private Solution ContruireVoisinAleatoire() {
		int tailleCentre= disposition.keySet().size();

		//on choisit une agence de la solution au hasard
		int indexCentre1= random.nextInt(tailleCentre);
		List<Agence> sousAgences1 = getValueForEntry(indexCentre1);
		//on en choisit une deuxieme
		int indexCentre2 = random.nextInt(tailleCentre);
		List<Agence> sousAgences2 = getValueForEntry(indexCentre2);

		//on choisit ensuite deux villes à echanger entre ces agences
		int indexVille1,indexVille2;
		indexVille1 = random.nextInt(sousAgences1.size());
		indexVille2 = random.nextInt(sousAgences2.size());

		Agence tmp1 = sousAgences1.get(indexVille1);
		Agence tmp2 = sousAgences2.get(indexVille2);
		setValueForEntry(indexCentre1,indexVille1,tmp2);
		setValueForEntry(indexCentre2,indexVille2,tmp1);

		return null;
	}


	private void setValueForEntry(int id, int indexVille , Agence newValue) {

		Iterator<Entry<CentreFormation, List<Agence>>> iterator = disposition.entrySet().iterator();
		int n = 0;

		while(iterator.hasNext()){
			if(n == id){
				iterator.next().getValue().set(indexVille, newValue);
			}
			n ++;
		}

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

	private List<Agence> getValueForEntry(int id){

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


}
