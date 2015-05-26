/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import Modèle.BlocChangerVariable;
import javax.swing.ImageIcon;

/**
 *
 * @author tgeant
 */
public class BlocChangerVariableGraphique extends BlocGraphique {

    public BlocChangerVariableGraphique(BlocChangerVariable bloc, String valeur) {
        super(bloc, bloc.getVariable().getNom()+":", valeur, new ImageIcon("src/images/BlocChangerVariable.png"));
    }
    
}
