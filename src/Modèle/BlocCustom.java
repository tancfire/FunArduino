/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;
import java.awt.Color;
import saveSystem.AccesXML;

/**
 * Ce bloc permet de créer son propre code.
 * @author tancfire
 */
public class BlocCustom extends Bloc{
    String code;

    public BlocCustom(String code, Controleur ctrl) {
        super(Color.PINK, ctrl);
        this.code = code;
    }
    
        public BlocCustom(int id, String code, Controleur ctrl) {
        super(id, Color.PINK, ctrl);
        this.code = code;
    }

    @Override
    public void mettreAjourCode() {
        sonCodeDebut = code;
        acces.setParametre(id, "String", "code", code);
    }
    
}
