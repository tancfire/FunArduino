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

    public BlocInitVariableGraphique() {
        super(new ImageIcon("src/images/BlocInitVariable.png"));
    }
    
    @Override
    protected void mettreAjourTexte() {
        System.out.println("variable: "+((BlocInitVariable)bloc).getVariable());
        this.setTexte("Variable:", ((BlocInitVariable)bloc).getVariable().getNom()+" à "+((BlocInitVariable)bloc).getVariable().getValeurDepart());
    }
}
