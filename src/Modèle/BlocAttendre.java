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
        super(TypeBloc.programmation, Color.gray, ctrl);
        init(delai);
    }
    
    
    public BlocAttendre(int id, int delai, Controleur ctrl) {
        super(id, TypeBloc.programmation, Color.MAGENTA, ctrl);
        init(delai);
    }
    
    
    
    private void init(int delai)
    {
        this.delai = delai;
        
        mettreAjourCode();
        blocGraph= new BlocAttendreGraphique(this);
    }

    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+"delay("+delai+");\n";
        acces.setParametre(id, "int", "delai", String.valueOf(delai));
    }
    
    
    public void setDelai(int delai) {
        this.delai = delai;
    }

    public int getDelai() {
        return delai;
    }
    
    
    
}
