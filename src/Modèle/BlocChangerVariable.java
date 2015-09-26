/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BoiteDialogue.BlocBoiteDialogue;
import vue.Graphique.BlocChangerVariableGraphique;

/**
 * Permet de changer la valeur d'une variable (c'est une affectation).
 * @author tancfire
 */
public class BlocChangerVariable extends BlocVariable {
    String valeur;

    public BlocChangerVariable(Variable var, String valeur, Controleur ctrl) {
        super(TypeBloc.programmation, new Color(197,1,1), new BlocChangerVariableGraphique(), var, ctrl);
        initialisation(valeur);
        init();
    }
    
    
    public BlocChangerVariable(int id, Variable var, String valeur, Controleur ctrl) {
        super(id, TypeBloc.programmation, new Color(197,1,1), new BlocChangerVariableGraphique(), var, ctrl);
        initialisation(valeur);
        init();
    }
    
    private void initialisation(String valeur)
    {
        this.valeur = valeur;
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut= tab()+getVariable().getNom()+"="+valeur+";\n";
         acces.setParametre(id, "variable", "id", String.valueOf(getVariable().getId()));
         if(var.getTypeVariable()==TypeVariable.texte){
            acces.setParametre(id, "String", "valeur", valeur);
         }else if (var.getTypeVariable()==TypeVariable.nombreEntier)
         {
             acces.setParametre(id, "Integer", "valeur", valeur);
         }else if (var.getTypeVariable()==TypeVariable.nombreAVirgule)
         {
              acces.setParametre(id, "Float", "valeur", valeur);
         }
    }
    
    public String getValeur()
    {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    
    @Override
    public BlocBoiteDialogue getBoiteDialogue() {
        return null;
    }    
}
