/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import vue.BlocGraphique.SimulateurGraphique;

/**
 *Représente une simulation de l'Arduino, en prenant en compte les branchements
 * @author tancfire
 */
public class SimulateurArduino {
    protected ArrayList<Pin> sesPins;
    private SimulateurGraphique simuGraph;
    
    public SimulateurArduino()
    {
        sesPins = new ArrayList<Pin>();
        sesPins.add(new Pin("2", TypePin.Digital, 251,0));
        sesPins.add(new Pin("3", TypePin.Digital, 241,0));
        
        simuGraph = new SimulateurGraphique(new ImageIcon("src/images/Leonardo.png"), 250,80);
    }
    
    public ArrayList<Pin> getSesPins()
    {
        return sesPins;
    }

    public SimulateurGraphique getSimuGraph() {
        return simuGraph;
    }
    
    
}
