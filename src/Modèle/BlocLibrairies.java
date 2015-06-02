/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;

/**
 *
 * @author tancfire
 */
public class BlocLibrairies extends Bloc{

    
    public BlocLibrairies(TypeBloc typeBloc, Color couleur, Controleur ctrl) {
        super(typeBloc, couleur, ctrl);
    }

    public BlocLibrairies(int id, TypeBloc typeBloc, Color couleur, Controleur ctrl) {
        super(id, typeBloc, couleur, ctrl);
    }
    
    

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = "//Les librairies:";
    }
    
}
