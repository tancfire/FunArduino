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
public class BlocChangerVariable extends Bloc {
    Variable var;
    String valeur;

    public BlocChangerVariable(Variable var, String valeur, AccesXML acces) {
        super(Color.red, acces);
        this.var = var;
        this.valeur = valeur;
       
        mettreAjourCode();
    }
    
    
    public BlocChangerVariable(int id, Variable var, String valeur, AccesXML acces) {
        super(id, Color.red, acces);
        this.var = var;
        this.valeur = valeur;
       
        mettreAjourCode();
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut= tab()+var.getNom()+"="+valeur+";\n";
         acces.setParametre(id, "variable", "id", String.valueOf(var.getId()));
         acces.setParametre(id, "String", "valeur", valeur);
    }
    
}
