/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocConditionsGraphique;

/**
 * Permet d'ajouter des conditions (ex: si a>b).
 * @author tancfire
 */
public class BlocConditions extends Bloc{
    Object objet1;
    Object objet2;
    Comparateur comparateur;
    String param1;
    String param2;

    public BlocConditions(Object objet1, Object objet2, Comparateur comparateur, Controleur ctrl) {
        super(Color.decode("#4D589A"), ctrl);
        this.objet1 = objet1;
        this.objet2 = objet2;
        this.comparateur = comparateur;
        param1 = "";
        param2 = "";
        
        mettreAjourCode();
        this.blocGraph = new BlocConditionsGraphique(this);
    }
    
    
     public BlocConditions(int id, Object objet1, Object objet2, Comparateur comparateur, Controleur ctrl) {
        super(id, Color.decode("#4D589A"), ctrl);
        this.objet1 = objet1;
        this.objet2 = objet2;
        this.comparateur = comparateur;
        param1 = "";
        param2 = "";
        
        mettreAjourCode();
        this.blocGraph = new BlocConditionsGraphique(this);
    }



    @Override
    public void mettreAjourCode(){
        TraductionParametre trad1 = new TraductionParametre(objet1);
        TraductionParametre trad2 = new TraductionParametre(objet2);
        param1 = trad1.traduire();
        param2 = trad2.traduire();
        
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
            case "string":
                 acces.setParametre(id, "string", "param2", objet2.toString());
                break;
        }
        
    }

    public Object getParam1() {
        return param1;
    }

    public Object getParam2() {
        return param2;
    }

    public Comparateur getComparateur() {
        return comparateur;
    }
    
    
    
}
