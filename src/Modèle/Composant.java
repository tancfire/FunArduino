/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Le Composant est tout le matériel que l'on pourra brancher sur le kit.
 * @author tancfire
 */
public abstract class Composant {
    protected ArrayList<Slot> sesSlots;
    private String nom;
    protected final int id;
    private static int nbID=0;
    
    public Composant(String nom, SimulateurArduino simulateur, Controleur ctrl)
    {
        sesSlots = new ArrayList<Slot>();
        sesSlots.add(new Slot(TypePin.GND, Color.BLACK, simulateur)); // on ajoute la masse
        this.nom = nom;
        
        id = nbID;
        nbID++;
        
        ctrl.getAcces().creerComposant(id, nom);
    }
    
    
        public Composant(int id, String nom, SimulateurArduino simulateur) //Lorsque le composant est chargé à partir du xml
    {
        sesSlots = new ArrayList<Slot>();
        sesSlots.add(new Slot(TypePin.GND, Color.BLACK, simulateur)); // on ajoute la masse
        this.nom = nom;
        this.id = id;
    }
    
    
    public abstract Pin getPin();
    
    
    public String getNom()
    {
        return nom;
    }

    public int getId() {
        return id;
    }

    
    
}
