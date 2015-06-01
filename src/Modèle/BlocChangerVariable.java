/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocChangerVariableGraphique;

/**
 * Permet de changer la valeur d'une variable (c'est une affectation).
 * @author tancfire
 */
public class BlocChangerVariable extends Bloc {
    Variable var;
    String valeur;

    public BlocChangerVariable(Variable var, String valeur, Controleur ctrl) {
        super(TypeBloc.programmation, new Color(197,1,1), ctrl);
        this.var = var;
        this.valeur = valeur;
        
        this.blocGraph = new BlocChangerVariableGraphique(this);
       
        mettreAjourCode();
    }
    
    
    public BlocChangerVariable(int id, Variable var, String valeur, Controleur ctrl) {
        super(id, TypeBloc.programmation, new Color(197,1,1), ctrl);
        this.var = var;
        this.valeur = valeur;
       
        this.blocGraph = new BlocChangerVariableGraphique(this);
        
        mettreAjourCode();
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut= tab()+var.getNom()+"="+valeur+";\n";
         acces.setParametre(id, "variable", "id", String.valueOf(var.getId()));
         acces.setParametre(id, "String", "valeur", valeur);
    }

    public Variable getVariable() {
        return var;
    }
    
    public String getValeur()
    {
        return valeur;
    }
    
    
}
