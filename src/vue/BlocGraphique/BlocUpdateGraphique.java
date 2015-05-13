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
public class BlocUpdateGraphique extends BlocGraphique {

    public BlocUpdateGraphique(Bloc bloc) {
        super(bloc, "Update", new ImageIcon("src/images/BlocStart.png"));
    }
    
}
