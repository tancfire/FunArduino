/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocInitVariable;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocInitVariableGraphique extends BlocGraphique{

    public BlocInitVariableGraphique(BlocInitVariable bloc) {
        super(bloc, "Variable:", bloc.getVariable().getNom()+" à "+bloc.getVariable().getValeurDepart(), new ImageIcon("src/images/BlocInitVariable.png"));
    }
    
}
