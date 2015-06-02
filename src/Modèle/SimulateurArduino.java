/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
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
    private Controleur ctrl;
    
    public SimulateurArduino(Controleur ctrl)
    {
        sesPins = new ArrayList<Pin>();
        this.ctrl = ctrl;
        sesPins.add(new Pin("2", TypePin.Digital, 251,0));
        sesPins.add(new Pin("3", TypePin.Digital, 241,0));
        sesPins.add(new Pin("4", TypePin.Digital, 231,0));
        
        simuGraph = new SimulateurGraphique(new ImageIcon("src/images/Leonardo.png"), 250,80, this);
    }
    
    public ArrayList<Pin> getSesPins()
    {
        return sesPins;
    }

    public SimulateurGraphique getSimuGraph() {
        return simuGraph;
    }

    public void mettreAJourBranchements() {
        ctrl.mettreAJourBranchements();
    }
    
}
