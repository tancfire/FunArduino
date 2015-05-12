/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.awt.Color;

/**
 * Le slot représente un fil du composant que l'on branchera sur une pin.
 * @author tancfire
 */
public class Slot {
    private TypePin typePinCompatible;
    private Color couleur;
    private Pin pin;
    
    public Slot(TypePin typePinCompatible, Color couleur, SimulateurArduino simulateur)
    {
        this.typePinCompatible = typePinCompatible;
        this.couleur = couleur;
        
         //ajout automatique d'une pin au slot
        for(int i=0; i<simulateur.getSesPins().size();i++)
        {
            if(simulateur.getSesPins().get(i).getTypePin()==typePinCompatible)
            {
                if(!simulateur.getSesPins().get(i).estOccupee()){
                    pin = simulateur.getSesPins().get(i);
                    simulateur.getSesPins().get(i).setOccupee(true);
                    break;
                }
            }
       }
    }
    
    
    public Color getCouleur()
    {
        return couleur;
    }
    
    
    public Pin getPinConnectee()
    {
        return pin;
    }
    
    
    public TypePin getTypePinCompatible()
    {
        return typePinCompatible;
    }
    
}
