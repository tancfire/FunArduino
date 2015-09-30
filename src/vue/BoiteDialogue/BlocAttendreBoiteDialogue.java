/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Controleur.Controleur;
import Modèle.BlocAttendre;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author tancfire
 */
public class BlocAttendreBoiteDialogue extends BlocBoiteDialogue {
      protected JSpinner editTpsAttente;
    
    public BlocAttendreBoiteDialogue(Controleur ctrl)
    {
        super(280,80, ctrl);
        
        editTpsAttente = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
        
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(5);
        layout.setVgap(5);
        panelPrincipal.setLayout(layout);
        panelPrincipal.add(new JLabel("Temps (en secondes):"));
        panelPrincipal.add(editTpsAttente);
    }

    @Override
    public void onValider() {
        
        if(!modifier) //si c'est une création d'un bloc
        {
            blocCaller.ajouterBlocALaFin(new BlocAttendre((int)editTpsAttente.getValue(), ctrl));
        }else{ //si c'est une modification d'un bloc
            ((BlocAttendre) blocCaller).setDelai((int)editTpsAttente.getValue());
        }
        ctrl.mettreAjourCode();
    }

    @Override
    public void onOuverture() {
        
        if(modifier){
            editTpsAttente.setValue(((BlocAttendre)blocCaller).getDelai());
        }
    }
    
}
