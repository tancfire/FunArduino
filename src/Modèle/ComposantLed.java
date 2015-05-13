/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;

/**
 * Ce composant initialise une Led.
 * @author tancfire
 */
public class ComposantLed extends Composant {
    
    public ComposantLed(SimulateurArduino simulateur, Controleur ctrl)
    {
        super("led",simulateur, ctrl);
        sesSlots.add(new Slot(TypePin.Digital, Color.YELLOW, simulateur));
        ctrl.getAcces().setSlot(id, TypePin.Digital.toString(), Color.YELLOW.toString().substring(14), sesSlots.get(1).getPinConnectee().getNom());
        
        ctrl.ajouterAuSetup(new BlocInitialisationComp(this, ctrl));
    }
    
        public ComposantLed(int id, Controleur ctrl)
    {
        super(id, "led", ctrl.getSimulateur());
        sesSlots.add(new Slot(TypePin.Digital, Color.YELLOW, ctrl.getSimulateur()));
         ctrl.ajouterAuSetup(new BlocInitialisationComp(id, this, ctrl));
    }

    @Override
    public Pin getPin() {
         return sesSlots.get(1).getPinConnectee();
    }
}
