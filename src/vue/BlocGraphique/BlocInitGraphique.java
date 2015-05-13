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
public class BlocInitGraphique extends BlocGraphique{

    public BlocInitGraphique(Bloc bloc) {
        super(bloc, "Initialisation", new ImageIcon("src/images/BlocStart.png"));
    }
    
}
