/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Controleur.Controleur;
import Modèle.BlocAllumerPin;
import Modèle.EtatPin;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author tancfire
 */
public class BlocAllumerPinBoiteDialogue extends BlocBoiteDialogue{
    protected JComboBox cbComposants;
    protected JComboBox cbEtats;
    
    public BlocAllumerPinBoiteDialogue(Controleur ctrl)
    {
        super(230,110, ctrl);
        cbComposants = new JComboBox();
        cbEtats = new JComboBox();
        cbEtats.addItem("Allumer");
        cbEtats.addItem("Eteindre");
        
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(5);
        layout.setVgap(5);
        panelPrincipal.setLayout(layout);
        panelPrincipal.add(new JLabel("composant:"));
        panelPrincipal.add(cbComposants);
        panelPrincipal.add(new JLabel("etat:"));
        panelPrincipal.add(cbEtats);
    }

    @Override
    public void onValider() {
        EtatPin etatPin = EtatPin.BAS;
        if(cbEtats.getSelectedItem().equals("Allumer"))
            etatPin = EtatPin.HAUT;
        
        if(!modifier) //si c'est une création
        {
            blocCaller.ajouterBlocALaFin(new BlocAllumerPin(ctrl.getComposants().get(cbComposants.getSelectedIndex()),etatPin,ctrl));
        }else{ //si c'est une modification
            ((BlocAllumerPin) blocCaller).setEtatPin(etatPin);
        }
        ctrl.mettreAjourCode();
    }

    @Override
    public void onOuverture() {
        mettreDansListeComposants(cbComposants);
        
        if(modifier){
             cbComposants.setEnabled(false);
             cbComposants.setSelectedItem(((BlocAllumerPin)blocCaller).getComposant().getNom());
             cbEtats.setSelectedItem(((BlocAllumerPin)blocCaller).getEtatPin());
        }
    }
    
}
