/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.Graphique.BlocComposantGraphique;

/**
 * Le BlocComposant est un bloc lié à un composant. Cela permettra aux classes filles
 * d'intéragir avec.
 * @author tancfire
 */
public abstract class BlocComposant extends Bloc{
    protected Composant composant;

    public BlocComposant(TypeBloc typeBloc, Color couleur, BlocComposantGraphique blocGraph, Composant composant, Controleur ctrl) {
        super(typeBloc, couleur, blocGraph, ctrl);
        initialisation(composant);
    }
    
    public BlocComposant(int id, TypeBloc typeBloc, Color couleur, BlocComposantGraphique blocGraph, Composant composant, Controleur ctrl) {
        super(id, typeBloc, couleur, blocGraph, ctrl);
        initialisation(composant);
    }
        
    
    private void initialisation(Composant composant)
    {
        this.composant = composant;
    }


    @Override
    public void mettreAjourCode()
    {
       acces.setParametre(id, "int", "idComposant", String.valueOf(composant.getId()));
       if(ctrl.getComposantById(composant.getId())==null && getParent()!=null)
            {
                this.delete();
            }
    }
    
    public void changerComposant(Composant comp)
    {
        this.composant = comp;
        ((BlocComposantGraphique) blocGraph).changerComposantGraphique(composant.getCompGraph());
    }

    
}
