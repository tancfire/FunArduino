/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocInitVariableGraphique;

/**
 * Permet d'initialiser une variable dans le code.
 * @author tancfire
 */
public class BlocInitVariable extends Bloc{

    private Variable var;
    
    public BlocInitVariable(Variable var, Controleur ctrl) {
        super(TypeBloc.initialisation, new Color(255,242,0), ctrl);
        this.var = var;
        this.setSupprimable(false);
        
        this.blocGraph = new BlocInitVariableGraphique(this);
    }
    
        public BlocInitVariable(int id, Variable var, Controleur ctrl) {
        super(id, TypeBloc.initialisation, new Color(255,242,0), ctrl);
        this.var = var;
        this.setSupprimable(false);
        
        this.blocGraph = new BlocInitVariableGraphique(this);
    }

          
    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+var.getTypeParam().getType()+" "+var.getNom()+"="+ var.getValeurDepart() + ";\n";
        acces.setParametre(id, "int", "idVariable", String.valueOf(var.getId()));
    }

    public Variable getVariable() {
        return var;
    }
    
    
    
}
