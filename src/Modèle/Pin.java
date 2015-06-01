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
    private final String nom;
    private final TypePin typePin;
    private boolean occupee;
    private final int x;
    private final int y;
    
    public Pin(String nom, TypePin typePin, int x, int y)
    {
        this.nom = nom;
        this.typePin = typePin;
        occupee = false;
        this.x = x;
        this.y = y;
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
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
}
