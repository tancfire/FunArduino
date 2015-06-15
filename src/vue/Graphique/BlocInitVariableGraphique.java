/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import Modèle.BlocInitVariable;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocInitVariableGraphique extends BlocGraphique{

    public BlocInitVariableGraphique() {
        super(new ImageIcon("src/images/BlocInitVariable.png"));
    }

    @Override
    protected String getTexte1() {
        return "Variable:";
    }

    @Override
    protected String getTexte2() {
        return ((BlocInitVariable)bloc).getVariable().getNom()+" à "+((BlocInitVariable)bloc).getVariable().getValeurDepart();
    }
}
