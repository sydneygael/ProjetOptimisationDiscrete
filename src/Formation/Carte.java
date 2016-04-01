package Formation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Carte  {
    
    private List<Agence> listeAgences;
    private List<CentreFormation> lieuxFormation;

    public Carte() {
        listeAgences = new ArrayList<Agence>();
        lieuxFormation = new ArrayList<CentreFormation>();
    }
    
    public void getAgencesFromFile(String fileName) throws IOException {
        BufferedReader file;
        String line;

        file = new BufferedReader(new FileReader(fileName));
        
        line = file.readLine();
        
        List<Agence> listeAgences = new ArrayList<Agence>();
        
        while ((line = file.readLine()) != null) {
            listeAgences.add(transformerAgence(line));
        }
        
        this.listeAgences = listeAgences;
        
        file.close();
    }
    
    private Agence transformerAgence(String line) throws IOException {
        String[] splitedLine = line.split(";");
        if (splitedLine.length != 6) {
            throw new IOException("mauvais format");
        }
        String id = splitedLine[0].substring(1, splitedLine[0].length() - 1);
        String name = splitedLine[1].substring(1, splitedLine[1].length() - 1);
        String codePostal = splitedLine[2].substring(1, splitedLine[2].length() - 1);
        float longitude = Float.parseFloat(splitedLine[3]);
        float latitude = Float.parseFloat(splitedLine[4]);
        int nbPersons = Integer.parseInt(splitedLine[5]);
        System.out.println(name);
        return new Agence(id, name, codePostal, longitude, latitude, nbPersons);
    }
    
    public void getCentreFormationFromFile(String fileName) throws IOException {
        BufferedReader file;
        String line;

        file = new BufferedReader(new FileReader(fileName));
        line = file.readLine();
        
        List<CentreFormation> listPlaces = new ArrayList<CentreFormation>();
        
        while ((line = file.readLine()) != null) {
            listPlaces.add(transformerCentreFormation(line));
        }
        
        this.lieuxFormation = listPlaces;
        
        file.close();
    }
    
    private CentreFormation transformerCentreFormation(String line) throws IOException {
        String[] splitedLine = line.split(";");
        if (splitedLine.length != 5) {
            throw new IOException("mauvais format");
        }
        String id = splitedLine[0].substring(1, splitedLine[0].length() - 1);
        String name = splitedLine[1].substring(1, splitedLine[1].length() - 1);
        String postalCode = splitedLine[2].substring(1, splitedLine[2].length() - 1);
        float longitude = Float.parseFloat(splitedLine[3]);
        float latitude = Float.parseFloat(splitedLine[4]);
        
        System.out.println(name);

        
        return new CentreFormation(id, name, postalCode, longitude, latitude);
    }

    public List<Agence> getListAgences() {
        return listeAgences;
    }

    public List<CentreFormation> getListCentreFormation() {
        return lieuxFormation;
    }
    
    //PREMIERE SOLUTION
    

}