/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Controleur.Controleur;
import java.awt.GridLayout;

/**
 *
 * @author tancfire
 */
public class BlocConditionsBoiteDialogue extends BlocBoiteDialogue{

    
    public BlocConditionsBoiteDialogue(Controleur ctrl) {
        super(300, 200, ctrl);
        
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(5);
        layout.setVgap(5);
        panelPrincipal.setLayout(layout);
        
    }

    
    
    @Override
    public void onValider() {
        
    }

    @Override
    public void onOuverture() {
        
    }
    
}
