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
public class BlocInitVariable extends BlocVariable{

    
    public BlocInitVariable(Variable var, Controleur ctrl) {
        super(TypeBloc.initialisation, new Color(255,242,0), var, ctrl);
        
        this.blocGraph = new BlocInitVariableGraphique(this);
    }
    
        public BlocInitVariable(int id, Variable var, Controleur ctrl) {
        super(id, TypeBloc.initialisation, new Color(255,242,0), var, ctrl);
        this.setSupprimable(false);
        
        this.blocGraph = new BlocInitVariableGraphique(this);
    }

          
    @Override
    public void mettreAjourCode() {
        if(getVariable().getTypeVariable()== TypeVariable.texte){
        sonCodeDebut = tab()+getVariable().getTypeVariable().getType()+" "+getVariable().getNom()+"=\""+ getVariable().getValeurDepart() + "\";\n";
        }else{
        sonCodeDebut = tab()+getVariable().getTypeVariable().getType()+" "+getVariable().getNom()+"="+ getVariable().getValeurDepart() + ";\n";
        }
        acces.setParametre(id, "int", "idVariable", String.valueOf(getVariable().getId()));
    }

    
    @Override
    public void delete()
    {
        super.delete();
        getVariable().delete();
    }
    
}
