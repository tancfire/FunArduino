/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocInitGraphique;

/**
 * C'est un bloc racine qui regroupe toutes les initialisations de variables globales.
 * @author tancfire
 */
public class BlocInit extends Bloc {

    public BlocInit(Controleur ctrl) {
        super(TypeBloc.programmation, Color.lightGray, ctrl);
        setSupprimable(false);
        setAutoriserFils(true);
        
        this.blocGraph = new BlocInitGraphique(this);
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = "// Déclaration des variables\n";
        sonCodeFin = "\n";
    }
    
    
    
}
