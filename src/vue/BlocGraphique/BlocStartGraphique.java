/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import javax.swing.ImageIcon;

/**
 * C'est le bloc graphique qui représentera le blocStart.
 * @author tancfire
 */
public class BlocStartGraphique extends BlocGraphique {

    public BlocStartGraphique(Bloc bloc) {
        super(bloc, "START", new ImageIcon("src/images/BlocStart.png"));
    }
    
}