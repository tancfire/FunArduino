/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

/**
 * Représente une pin de l'arduino.
 * @author tancfire
 */
public class Pin {
    private String nom;
    private TypePin typePin;
    private boolean occupee;
    
    public Pin(String nom, TypePin typePin)
    {
        this.nom = nom;
        this.typePin = typePin;
        occupee = false;
    }
    

    public String getNom() {
        return nom;
    }

    public TypePin getTypePin() {
        return typePin;
    }
    
    public void setOccupee(boolean occupee)
    {
        this.occupee = occupee;
    }
    
    public boolean estOccupee()
    {
        return occupee;
    }
    
    
    
}
