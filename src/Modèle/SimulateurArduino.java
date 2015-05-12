/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.util.ArrayList;

/**
 *Représente une simulation de l'Arduino, en prenant en compte les branchements
 * @author tancfire
 */
public class SimulateurArduino {
    protected ArrayList<Pin> sesPins;
    
    public SimulateurArduino()
    {
        sesPins = new ArrayList<Pin>();
        
        sesPins.add(new Pin("3", TypePin.Digital));
    }
    
    public ArrayList<Pin> getSesPins()
    {
        return sesPins;
    }
}
