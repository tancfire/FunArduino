/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocConditions;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocConditionsGraphique extends BlocGraphique {

    public BlocConditionsGraphique(BlocConditions bloc) {
        super(bloc, "Si "+bloc.getParam1(), bloc.getComparateur().getFormule()+" "+bloc.getParam2(), new ImageIcon("src/images/BlocConditions.png"));
    }
    
}
