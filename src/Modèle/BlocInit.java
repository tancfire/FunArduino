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
public class BlocInit extends Bloc {

    public BlocInit(AccesXML acces) {
        super(Color.lightGray, acces);
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = "// Déclaration des variables\n";
        sonCodeFin = "\n";
    }
    
    
    
}
