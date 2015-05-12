/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.awt.Color;
import saveSystem.AccesXML;

/**
 *
 * @author Utilisateur
 */
public class BlocAttendre extends Bloc {
    private int delai;

    public BlocAttendre(int delai, AccesXML acces) {
        super(Color.MAGENTA, acces);
        this.delai = delai;
        
        mettreAjourCode();
    }
    
    
    public BlocAttendre(int id, int delai, AccesXML acces) {
        super(id, Color.MAGENTA, acces);
        this.delai = delai;
        
        mettreAjourCode();
    }
    
    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+"delay("+delai+");\n";
        acces.setParametre(id, "int", "delai", String.valueOf(delai));
    }
    
}
