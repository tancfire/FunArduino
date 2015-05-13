/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import saveSystem.AccesXML;
import vue.BlocGraphique.BlocStartGraphique;

/**
 * Permet de lancer du code au démarrage.
 * @author tancfire
 */
public class BlocStart extends Bloc{
    
    public BlocStart(Controleur ctrl)
    {
        super(Color.ORANGE, ctrl);
        ctrl.ajouterBlocGraphique(new BlocStartGraphique(this));
    }
    
    
    @Override
    public void mettreAjourCode()
    {
        sonCodeDebut = "\nvoid setup(){\n";
        sonCodeFin = "}\n";  
    }
 
    
}
