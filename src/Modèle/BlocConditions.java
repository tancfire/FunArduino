/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BoiteDialogue.BlocBoiteDialogue;
import vue.BoiteDialogue.BlocConditionsBoiteDialogue;
import vue.Graphique.BlocConditionsGraphique;

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
        super(TypeBloc.programmation, Color.decode("#4D589A"), new BlocConditionsGraphique(), ctrl);
        initialisation(objet1, objet2, comparateur);
        init();
    }
    
    
     public BlocConditions(int id, Object objet1, Object objet2, Comparateur comparateur, Controleur ctrl) {
        super(id, TypeBloc.programmation, Color.decode("#4D589A"), new BlocConditionsGraphique(), ctrl);
        initialisation(objet1, objet2, comparateur);
        init();
    }
     
     
     private void initialisation(Object objet1, Object objet2, Comparateur comparateur)
     {
        this.objet1 = objet1;
        this.objet2 = objet2;
        this.comparateur = comparateur;
        param1 = "";
        param2 = "";
        
        this.setAutoriserFils(true);

     }

     


    @Override
    public void mettreAjourCode(){
        TraductionParametre trad1 = new TraductionParametre(objet1);
        TraductionParametre trad2 = new TraductionParametre(objet2);
        param1 = trad1.traduire();
        param2 = trad2.traduire();
        
        sonCodeDebut = tab()+"if("+param1+comparateur+param2+"){\n";
        sonCodeFin = tab()+"}\n";
       
        if(objet1!=null && objet2!=null){
        switch (trad1.getType()) {
            case "composant":
                acces.setParametre(id, "composant", "param1", String.valueOf(((Composant)objet1).getId()));
                break;
            case "int":
                acces.setParametre(id, "int", "param1", param1);
                break;
            case "variable":
                acces.setParametre(id, "variable", "param1", String.valueOf(((Variable)objet1).getId()));
                if(ctrl.getVariableById(((Variable)objet1).getId())==null) //On vérifie que la variable existe
                    delete(); //si elle ne l'ai pas, on détruit ce bloc
                break;
            case "string":
                 acces.setParametre(id, "string", "param2", objet1.toString());
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
                 if(ctrl.getVariableById(((Variable)objet2).getId())==null) //On vérifie que la variable existe
                    delete(); //si elle ne l'ai pas, on détruit ce bloc
                break;
            case "string":
                 acces.setParametre(id, "string", "param2", objet2.toString());
                break;
        }
        }else{
            delete();
        }
        
    }
    
    public void setParam1(Object objet1) //en fait, ici, on ne définit pas le paramètre, mais l'objet
    {
        this.objet1 = objet1;
    }
    
    public void setParam2(Object objet2)
    {
        this.objet2 = objet2;
    }

    public void setComparateur(Comparateur comparateur) {
        this.comparateur = comparateur;
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
    
    @Override
    public BlocBoiteDialogue getBoiteDialogue() {
        return new BlocConditionsBoiteDialogue(ctrl);
    }    
    
}
