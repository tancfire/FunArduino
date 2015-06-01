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
 * @author tancfire
 */
public class BlocChangerVariableGraphique extends BlocGraphique {

    public BlocChangerVariableGraphique(BlocChangerVariable bloc) {
        super(bloc, bloc.getVariable().getNom()+":", bloc.getValeur(), new ImageIcon("src/images/BlocChangerVariable.png"));
    }
    
    @Override
    protected void mettreAjourTexte() {
        this.setTexte(((BlocChangerVariable)bloc).getVariable().getNom()+":", ((BlocChangerVariable)bloc).getValeur());
    }
    
}
