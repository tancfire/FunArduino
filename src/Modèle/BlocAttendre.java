/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocAttendreGraphique;

/**
 * Le bloc attendre permet de faire une pause dans le programme.
 * @author tancfire
 */
public class BlocAttendre extends Bloc {
    private int delai;

    public BlocAttendre(int delai, Controleur ctrl) {
        super(Color.gray, ctrl);
        this.delai = delai;
        
        mettreAjourCode();
        blocGraph= new BlocAttendreGraphique(this);
        ctrl.ajouterBlocGraphique(blocGraph);
    }
    
    
    public BlocAttendre(int id, int delai, Controleur ctrl) {
        super(id, Color.MAGENTA, ctrl);
        this.delai = delai;
        
        mettreAjourCode();
        blocGraph= new BlocAttendreGraphique(this);
        ctrl.ajouterBlocGraphique(blocGraph);
    }
    
    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+"delay("+delai+");\n";
        acces.setParametre(id, "int", "delai", String.valueOf(delai));
    }

    public int getDelai() {
        return delai;
    }
    
    
    
}
