/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import java.util.ArrayList;
import vue.Graphique.ComposantGraphique;

/**
 * Le Composant est tout le matériel que l'on pourra brancher sur le kit.
 * @author tancfire
 */
public abstract class Composant {
    protected ArrayList<Slot> sesSlots;
    private String nom;
    protected final int id;
    private static int nbID=0;
    protected ComposantGraphique compGraph;
    private Controleur ctrl;
    
    public Composant(String nom, Controleur ctrl)
    {
        this(nbID,nom, ctrl);
        nbID++;
        
        ctrl.getAcces().creerComposant(id, nom);
    }
    
    
        public Composant(int id, String nom, Controleur ctrl) //Lorsque le composant est chargé à partir du xml
    {
        sesSlots = new ArrayList<Slot>();
        sesSlots.add(new Slot(TypePin.GND, Color.BLACK, ctrl.getSimulateur())); // on ajoute la masse
        this.nom = nom;
        this.id = id;
        
        if(id>nbID)
            nbID = id;
        this.ctrl = ctrl;
    }
    
    
    public abstract Pin getPin();
    
    
    public void delete()
    {
        for(int i=0; i<sesSlots.size();i++)
        {
            if(sesSlots.get(i).getPinConnectee()!=null)
            sesSlots.get(i).getPinConnectee().setOccupee(false);
        }
        ctrl.supprimerComposant(this);
        ctrl.getAcces().supprimerComposant(id);
    }
    
    
    public String getNom()
    {
        return nom;
    }

    public int getId() {
        return id;
    }

    public ComposantGraphique getCompGraph() {
        return compGraph;
    }

    public void changerSlot(int x, int y) {
        sesSlots.get(1).changerPin(x, y);
        ctrl.mettreAJourBranchements();
    }

    public void mettreAJourBlocsGraphiques()
    {
        ctrl.mettreAJourBlocsGraphiques();
    }

    
    
}
