/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocInitialisationCompGraphique;

/**
 * Ce bloc permet d'initialiser un composant.
 * @author tancfire
 */
public class BlocInitialisationComp extends BlocComposant{

    
    public BlocInitialisationComp(Composant composant, Controleur ctrl) {
        super(TypeBloc.initStart, Color.red, new BlocInitialisationCompGraphique(), composant, ctrl);
        this.setSupprimable(false);
        init();
    }
    
        public BlocInitialisationComp(int id, Composant composant,  Controleur ctrl) {
        super(id, TypeBloc.initStart, Color.red, new BlocInitialisationCompGraphique(), composant, ctrl);
        this.setSupprimable(false);
        init();
    }
    
    @Override
    public void mettreAjourCode()
    {       
        // On récupère la pin du composant lié à ce bloc.
        //Pour l'instant, le composant à gérer ne peut être qu'en OUTPUT. Les prochaines
        //màj règleront le problème.
        sonCodeDebut = tab()+"pinMode("+composant.getPin().getNom()+",OUTPUT);\n";
        super.mettreAjourCode();
    }
    
    
    public String getEntreeSortie()
    {
        return "sortie";
    }
    
}
