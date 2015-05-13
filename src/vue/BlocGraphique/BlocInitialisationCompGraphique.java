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
 * @author Utilisateur
 */
public class BlocInitialisationCompGraphique extends BlocGraphique {

    public BlocInitialisationCompGraphique(Bloc bloc) {
        super(bloc, "Init composant", new ImageIcon("src/images/BlocStart.png"));
    }
    
}
