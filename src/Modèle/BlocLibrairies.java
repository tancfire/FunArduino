/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import vue.BlocGraphique.BlocLibrairiesGraphique;

/**
 *
 * @author tancfire
 */
public class BlocLibrairies extends Bloc{

    
    public BlocLibrairies(Controleur ctrl) {
        super(TypeBloc.programmation, Color.DARK_GRAY, new BlocLibrairiesGraphique(), ctrl);
        initialisation();
        init();
    }

    public BlocLibrairies(int id, Controleur ctrl) {
        super(id, TypeBloc.programmation, Color.DARK_GRAY, new BlocLibrairiesGraphique(), ctrl);
        initialisation();
        init();
    }
    
    private void initialisation()
    {
        setSupprimable(false);
        setAutoriserFils(true);
    }
    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = "// Les librairies:\n";
        sonCodeFin = "\n";
    }
    
}
