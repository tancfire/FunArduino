/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocConditionsGraphique extends BlocGraphique {

    public BlocConditionsGraphique(Bloc bloc) {
        super(bloc, "Si", new ImageIcon("src/images/BlocStart.png"));
    }
    
}
