/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import javax.swing.ImageIcon;
import vue.BlocGraphique.ComposantGraphique;
import vue.BlocGraphique.ComposantLedGraphique;

/**
 * Ce composant initialise une Led.
 * @author tancfire
 */
public class ComposantLed extends Composant {
    
    public ComposantLed(Controleur ctrl)
    {
        super("led",ctrl.getSimulateur(), ctrl);
        sesSlots.add(new Slot(TypePin.Digital, Color.YELLOW, ctrl.getSimulateur()));
        ctrl.getAcces().setSlot(id, TypePin.Digital.toString(), Color.YELLOW.toString().substring(14), sesSlots.get(1).getPinConnectee().getNom());
        
        ctrl.ajouterAuSetup(new BlocInitialisationComp(this, ctrl));
        
        compGraph = new ComposantLedGraphique(this,ctrl.getSimulateur().getSimuGraph());
    }
    
        public ComposantLed(int id, Controleur ctrl)
    {
        super(id, "led", ctrl.getSimulateur());
        sesSlots.add(new Slot(TypePin.Digital, Color.YELLOW, ctrl.getSimulateur()));
        ctrl.ajouterAuSetup(new BlocInitialisationComp(id, this, ctrl));
         
        compGraph = new ComposantLedGraphique(this,ctrl.getSimulateur().getSimuGraph());
    }

    @Override
    public Pin getPin() {
         return sesSlots.get(1).getPinConnectee();
    }
}
