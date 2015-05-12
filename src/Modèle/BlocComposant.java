/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import java.awt.Color;
import saveSystem.AccesXML;

/**
 * Le BlocComposant est un bloc lié à un composant.
 * @author tancfire
 */
public abstract class BlocComposant extends Bloc{
    protected Composant composant;

    public BlocComposant(Color couleur, Composant composant, AccesXML acces) {
        super(couleur, acces);
        this.composant = composant;
        acces.setParametre(id, "int", "idComposant", String.valueOf(composant.getId()));
    }
    
        public BlocComposant(int id, Color couleur, Composant composant, AccesXML acces) {
        super(id, couleur, acces);
        this.composant = composant;
        acces.setParametre(id, "int", "idComposant", String.valueOf(composant.getId()));
    }


    @Override
    public abstract void mettreAjourCode();
    

    
}
