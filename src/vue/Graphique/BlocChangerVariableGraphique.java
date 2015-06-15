/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.Bloc;
import Modèle.BlocChangerVariable;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocChangerVariableGraphique extends BlocGraphique {

    public BlocChangerVariableGraphique() {
        super(new ImageIcon("src/images/BlocChangerVariable.png"));
    }
       
    @Override
    protected String getTexte1() {
        return ((BlocChangerVariable)bloc).getVariable().getNom()+":";
    }

    @Override
    protected String getTexte2() {
        return ((BlocChangerVariable)bloc).getValeur();
    } 
}
