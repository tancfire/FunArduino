/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;

/**
 * Permet d'ajouter des conditions (ex: si a>b).
 * @author tancfire
 */
public class BlocConditions extends Bloc{
    Object objet1;
    Object objet2;
    Comparateur comparateur;

    public BlocConditions(Object objet1, Object objet2, Comparateur comparateur, Controleur ctrl) {
        super(Color.PINK, ctrl);
        this.objet1 = objet1;
        this.objet2 = objet2;
        this.comparateur = comparateur;
        
        mettreAjourCode();
    }
    
    
     public BlocConditions(int id, Object objet1, Object objet2, Comparateur comparateur, Controleur ctrl) {
        super(id, Color.PINK, ctrl);
        this.objet1 = objet1;
        this.objet2 = objet2;
        this.comparateur = comparateur;
        
        mettreAjourCode();
    }



    @Override
    public void mettreAjourCode(){
        TraductionParametre trad1 = new TraductionParametre(objet1);
        TraductionParametre trad2 = new TraductionParametre(objet2);
        String param1 = trad1.traduire();
        String param2 = trad2.traduire();
        
        sonCodeDebut = tab()+"if("+param1+comparateur+param2+"){\n";
        sonCodeFin = tab()+"}\n";
       
        switch (trad1.getType()) {
            case "composant":
                acces.setParametre(id, "composant", "param1", String.valueOf(((Composant)objet1).getId()));
                break;
            case "int":
                acces.setParametre(id, "int", "param1", param1);
                break;
            case "variable":
                acces.setParametre(id, "variable", "param1", String.valueOf(((Variable)objet1).getId()));
                break;
        }
        switch (trad2.getType()) {
            case "composant":
                acces.setParametre(id, "composant", "param2", String.valueOf(((Composant)objet2).getId()));
                break;
            case "int":
                acces.setParametre(id, "int", "param2", param2);
                break;
            case "variable":
                acces.setParametre(id, "variable", "param2", String.valueOf(((Variable)objet2).getId()));
                break;
        }
        
        
    }
    
    
    
}
