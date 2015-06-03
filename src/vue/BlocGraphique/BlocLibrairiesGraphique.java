/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.BlocLibrairies;
import javax.swing.ImageIcon;

/**
 *
 * @author tancfire
 */
public class BlocLibrairiesGraphique extends BlocGraphique{

    public BlocLibrairiesGraphique() {
        super(new ImageIcon("src/images/BlocStart.png"));
    }

    @Override
    protected void mettreAjourTexte() {
        setTexte("Librairies", "");
    }
    
}
