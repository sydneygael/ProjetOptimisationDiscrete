package Algorithmes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Sydney
 *
 */
public class Roulette {
    
    private Population population;
    private List<Solution> meilleuresSolutions;
    private List<Float> probabilitesCumulees;
    private int kPopulation;
    private Random alea;

    public Roulette (Population population, int kPopulation) {
        this.population = population;
        this.kPopulation = kPopulation;
        meilleuresSolutions = new ArrayList<Solution>(population.getSolutions());
        probabilitesCumulees = new ArrayList<Float>();
        alea = new Random();
        calculerLesProbabilitesCumulees();
    }
    
    
    public Solution tournerRoulette() {
        float prob = alea.nextFloat();
        
        Solution solutionTiree = null;
        int index = 0;
        
        for (Float probCumulee : probabilitesCumulees) {
           
        	if (prob <= probCumulee) {
                solutionTiree = meilleuresSolutions.get(index);
                break;
            }   	
            index++;
        }
        return solutionTiree;
    }
    
    public List<Solution> getNouvelleGeneration() {
    	
        List<Solution> nouvelleGeneration = new ArrayList<Solution>();
        
        for (int i=0; i< population.getTaille(); i++) {
            Solution solution = tournerRoulette();
            nouvelleGeneration.add(solution);
        }
        
        return nouvelleGeneration;
    }
    
    private void calculerLesProbabilitesCumulees() {
        Collections.sort(meilleuresSolutions);
        meilleuresSolutions = meilleuresSolutions.subList(0, kPopulation);
        
        float totalFitness = 0;
        for (Solution solution : meilleuresSolutions) {
            totalFitness += 1 / solution.getFitness();
        }

        float fitnessCumulee = 0;
        
        for (Solution solution : meilleuresSolutions) {
            fitnessCumulee += 1 / solution.getFitness();
            probabilitesCumulees.add(fitnessCumulee / totalFitness);
        }
    }

}