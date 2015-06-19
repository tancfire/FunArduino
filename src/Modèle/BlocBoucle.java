/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.Graphique.BlocBoucleGraphique;

/**
 *
 * @author tancfire
 */
public class BlocBoucle extends Bloc {
    private int max;

    
    public BlocBoucle(int max, Controleur ctrl) {
        super(TypeBloc.programmation, new Color(200,191,231), new BlocBoucleGraphique(), ctrl);
        initialisation(max);
    }

    
    public BlocBoucle(int id, int max, Controleur ctrl) {
        super(id, TypeBloc.programmation, new Color(200,191,231), new BlocBoucleGraphique(), ctrl);
        initialisation(max);
    }
    
    private void initialisation(int max)
    {
        this.setAutoriserFils(true);
        this.max = max;
    }
    
    

    @Override
    public void mettreAjourCode() {
        //A modifier: Si jamais il a un parent de type boucle, la variable s'apellera i2, i3, ....
        sonCodeDebut = "for(int i=0; i<"+max+";i++){";
        sonCodeFin = "}";
    }
    
}
