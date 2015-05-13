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
 * Le bloc attendre permet de faire une pause dans le programme.
 * @author tancfire
 */
public class BlocAttendre extends Bloc {
    private int delai;

    public BlocAttendre(int delai, Controleur ctrl) {
        super(Color.MAGENTA, ctrl);
        this.delai = delai;
        
        mettreAjourCode();
        ctrl.ajouterBlocGraphique(new BlocAllumerPinGraphique(this));
    }
    
    
    public BlocAttendre(int id, int delai, Controleur ctrl) {
        super(id, Color.MAGENTA, ctrl);
        this.delai = delai;
        
        mettreAjourCode();
        ctrl.ajouterBlocGraphique(new BlocAllumerPinGraphique(this));
    }
    
    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+"delay("+delai+");\n";
        acces.setParametre(id, "int", "delai", String.valueOf(delai));
    }
    
}
