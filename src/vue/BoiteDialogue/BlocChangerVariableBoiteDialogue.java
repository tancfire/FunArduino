/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Autre.CompInt;
import Controleur.Controleur;
import Modèle.BlocChangerVariable;
import Modèle.TypeVariable;
import Modèle.Variable;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author tancfire
 */
public class BlocChangerVariableBoiteDialogue extends BlocBoiteDialogue{
    protected JComboBox cbVariables;
    protected JTextField editValeur;

    public BlocChangerVariableBoiteDialogue(Controleur ctrl) {
        super(280, 120, ctrl);
        
        cbVariables = new JComboBox();
        editValeur = new JTextField();
        
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(5);
        layout.setVgap(5);
        panelPrincipal.setLayout(layout);
        panelPrincipal.add(new JLabel("Variables:"));
        panelPrincipal.add(cbVariables);
        panelPrincipal.add(new JLabel("Valeur:"));
        panelPrincipal.add(editValeur);
    }

    @Override
    public void onValider() {
         Variable var =  ctrl.getVariables().get(cbVariables.getSelectedIndex());
         CompInt cInt = new CompInt();
         
         if((var.getTypeVariable()==TypeVariable.texte)||
                 cInt.isInteger(editValeur.getText())&&var.getTypeVariable()==TypeVariable.nombreEntier){       
            if(!modifier) //si c'est une création d'un bloc
            {
                    blocCaller.ajouterBlocALaFin(new BlocChangerVariable(var, editValeur.getText(), ctrl));
            }else{ //si c'est une modification d'un bloc
                  ((BlocChangerVariable) blocCaller).setValeur(editValeur.getText());
            }
            ctrl.mettreAjourCode();
         }
    }

    @Override
    public void onOuverture() {
         mettreDansListeVariables(cbVariables);
         
         if(modifier){
             cbVariables.setEnabled(false);
             cbVariables.setSelectedItem(((BlocChangerVariable)blocCaller).getVariable().getNom());
             editValeur.setText(((BlocChangerVariable)blocCaller).getValeur());
         }
    }
    
}
