/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocAllumerPinGraphique;

/**
 * Ce bloc permet d'allumer une Pin d'un composant.
 * @author tancfire
 */
public class BlocAllumerPin extends BlocComposant {
    private EtatPin etatPin;
    
    public BlocAllumerPin(Composant composant, EtatPin etatPin, Controleur ctrl)
    {
        super(Color.BLUE, composant, ctrl);
        this.etatPin = etatPin;
        
        mettreAjourCode();
        blocGraph= new BlocAllumerPinGraphique(this);
        ctrl.ajouterBlocGraphique(blocGraph);
    }
    
    
    public BlocAllumerPin(int id, Composant composant, EtatPin etatPin, Controleur ctrl)
    {
        super(id, Color.BLUE, composant, ctrl);
        this.etatPin = etatPin;
        
        mettreAjourCode();
        blocGraph= new BlocAllumerPinGraphique(this);
        ctrl.ajouterBlocGraphique(blocGraph);
    }
    
    @Override
    public void mettreAjourCode()
    {
        if(etatPin == EtatPin.BAS)
        {
            sonCodeDebut = tab()+"digitalWrite("+composant.getPin().getNom()+",LOW);\n";
        }else{
            sonCodeDebut = tab()+"digitalWrite("+composant.getPin().getNom()+",HIGH);\n";
        }
        acces.setParametre(id, "etatPin", "etatPin", etatPin.toString());
    }

    
    
    
}
