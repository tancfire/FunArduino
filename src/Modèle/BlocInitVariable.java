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
public class BlocInitVariable extends Bloc{

    private Variable var;
    
    public BlocInitVariable(Variable var, AccesXML acces) {
        super(Color.YELLOW, acces);
        this.var = var;
    }
    
        public BlocInitVariable(int id, Variable var, AccesXML acces) {
        super(id, Color.YELLOW, acces);
        this.var = var;
        
    }

    
    @Override
    public void mettreAjourCode() {
        sonCodeDebut = tab()+var.getTypeParam().getType()+" "+var.getNom()+"="+ var.getValeurDepart() + ";\n";
        acces.setParametre(id, "int", "idVariable", String.valueOf(var.getId()));
    }
    
}
