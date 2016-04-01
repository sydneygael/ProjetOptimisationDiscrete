/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formation;

/**
 *
 * @author Corentin
 */
public class Employe {
    
    private Agence agence;
    private CentreFormation centre;
    
    public Employe(Agence agence){
        this.agence=agence;
        centre=null;
    }
    
    public void setCentre(CentreFormation centre){
        this.centre=centre;
    }
}
