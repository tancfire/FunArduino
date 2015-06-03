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
public class BlocChangerVariable extends BlocVariable {
    String valeur;

    public BlocChangerVariable(Variable var, String valeur, Controleur ctrl) {
        super(TypeBloc.programmation, new Color(197,1,1), new BlocChangerVariableGraphique(), var, ctrl);
        this.valeur = valeur;
        init();
    }
    
    
    public BlocChangerVariable(int id, Variable var, String valeur, Controleur ctrl) {
        super(id, TypeBloc.programmation, new Color(197,1,1), new BlocChangerVariableGraphique(), var, ctrl);
        this.valeur = valeur;
        init();
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut= tab()+getVariable().getNom()+"="+valeur+";\n";
         acces.setParametre(id, "variable", "id", String.valueOf(getVariable().getId()));
         acces.setParametre(id, "String", "valeur", valeur);
    }
    
    public String getValeur()
    {
        return valeur;
    }
    
    
}
