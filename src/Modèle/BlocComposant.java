/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;

/**
 * Le BlocComposant est un bloc lié à un composant. Cela permettra aux classes filles
 * d'intéragir avec.
 * @author tancfire
 */
public abstract class BlocComposant extends Bloc{
    protected Composant composant;

    public BlocComposant(TypeBloc typeBloc, Color couleur, Composant composant, Controleur ctrl) {
        super(typeBloc, couleur, ctrl);
        this.composant = composant;
        acces.setParametre(id, "int", "idComposant", String.valueOf(composant.getId()));
    }
    
        public BlocComposant(int id, TypeBloc typeBloc, Color couleur, Composant composant, Controleur ctrl) {
        super(id, typeBloc, couleur, ctrl);
        this.composant = composant;
        acces.setParametre(id, "int", "idComposant", String.valueOf(composant.getId()));
    }


    @Override
    public abstract void mettreAjourCode();
    

    
}
