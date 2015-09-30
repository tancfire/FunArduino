/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Controleur.Controleur;
import Modèle.BlocConditions;
import Modèle.Comparateur;
import Modèle.TraductionParametre;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author tancfire
 */
public class BlocConditionsBoiteDialogue extends BlocBoiteDialogue{

    private JComboBox cbType1;
    private JComboBox cbParam1;
    private JTextField editValeur1;
    
    private JComboBox cbType2;
    private JComboBox cbParam2;
    private JTextField editValeur2;
    
    private JComboBox cbComparateur;
    
    public BlocConditionsBoiteDialogue(Controleur ctrl) {
        super(550, 150, ctrl);
        
        cbComparateur = new JComboBox();
        cbComparateur.addItem("égal à");
        cbComparateur.addItem("non-égal à");
        cbComparateur.addItem("supérieur à");
        cbComparateur.addItem("supérieur ou égal à");
        cbComparateur.addItem("inférieur à");
        cbComparateur.addItem("inférieur ou égal à");
        
        cbParam1 = new JComboBox();
        editValeur1 = new JTextField();
       
        cbType1 = new JComboBox();
        cbType1.addItem("Variable");
        cbType1.addItem("Composant");
        cbType1.addItem("Valeur");
        
        
        cbParam2 = new JComboBox();
        editValeur2 = new JTextField();
       
        cbType2 = new JComboBox();
        cbType2.addItem("Variable");
        cbType2.addItem("Composant");
        cbType2.addItem("Valeur");
        
        
        //quand on choisit un type de paramètre à tester
        cbType1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             if(cbType1.getSelectedItem().equals("Variable")){
                 editValeur1.setEnabled(false);
                 cbParam1.setEnabled(true);
                 mettreDansListeVariables(cbParam1);
             }else if (cbType1.getSelectedItem().equals("Composant")){
                 editValeur1.setEnabled(false);
                 cbParam1.setEnabled(true);
                 mettreDansListeComposants(cbParam1);   
             }else if (cbType1.getSelectedItem().equals("Valeur")){
                 editValeur1.setEnabled(true);
                 cbParam1.setEnabled(false);
            }
            }
        });
        
        //quand on choisit un type de paramètre à tester (pour le paramètre 2)
        cbType2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             if(cbType2.getSelectedItem().equals("Variable")){
                 editValeur2.setEnabled(false);
                 cbParam2.setEnabled(true);
                 mettreDansListeVariables(cbParam2);
             }else if (cbType2.getSelectedItem().equals("Composant")){
                 editValeur2.setEnabled(false);
                 cbParam2.setEnabled(true);
                 mettreDansListeComposants(cbParam2);   
             }else if (cbType2.getSelectedItem().equals("Valeur")){
                 editValeur2.setEnabled(true);
                 cbParam2.setEnabled(false);
            }
            }
        });
        
        GridLayout layout = new GridLayout(0,4);
        layout.setHgap(5);
        layout.setVgap(5);
        panelPrincipal.setLayout(layout);
        
        panelPrincipal.add(new JLabel("Paramètre 1:"));
        panelPrincipal.add(cbType1);
        panelPrincipal.add(cbParam1);
        panelPrincipal.add(editValeur1);
        
        panelPrincipal.add(new JLabel("Comparateur:"));
        panelPrincipal.add(cbComparateur);
        panelPrincipal.add(new JLabel(""));
        panelPrincipal.add(new JLabel(""));
        
        panelPrincipal.add(new JLabel("Paramètre 2:"));
        panelPrincipal.add(cbType2);
        panelPrincipal.add(cbParam2);
        panelPrincipal.add(editValeur2);
    }

    
    
    @Override
    public void onValider() {
        Object o1;
        Object o2;
        
        if(cbType1.getSelectedItem().equals("Composant")){
            o1 = ctrl.getComposants().get(cbParam1.getSelectedIndex());
        }else if(cbType1.getSelectedItem().equals("Variable")){
            o1 = ctrl.getVariables().get(cbParam1.getSelectedIndex());
        }else{
            o1 = editValeur1.getText();
        }
        
        if(cbType2.getSelectedItem().equals("Composant")){
            o2 = ctrl.getComposants().get(cbParam2.getSelectedIndex());
        }else if(cbType2.getSelectedItem().equals("Variable")){
            o2 = ctrl.getVariables().get(cbParam2.getSelectedIndex());
        }else{
            o2 = editValeur2.getText();
        }
        
        Comparateur comp = Comparateur.egal; //valeur par défaut si il y a une erreur
        
        for(int i=0; i<Comparateur.values().length;i++) //On cherche si la valeur d'un comparateur est égal à ce qu'il y'a dans la comboBox
        {
            if(Comparateur.values()[i].getFormule().equals(cbComparateur.getSelectedItem()))
            {
                comp = Comparateur.values()[i];
                break;
            }
        }
        
        
        if(!modifier){ //Si c'est une création de  bloc
            blocCaller.ajouterBlocALaFin(new BlocConditions(o1,o2,comp, ctrl));
        }else{ //Si c'est une modification d'un bloc
            ((BlocConditions)blocCaller).setParam1(o1);
            ((BlocConditions)blocCaller).setParam2(o2);
            ((BlocConditions)blocCaller).setComparateur(comp);
        }
        
        ctrl.mettreAjourCode();
    }

    @Override
    public void onOuverture() {
        mettreDansListeVariables(cbParam1);
        editValeur1.setEnabled(false);
        
        mettreDansListeVariables(cbParam2);
        editValeur2.setEnabled(false);
        
        if(modifier){// Préremplissage des champs
            cbComparateur.setSelectedItem(((BlocConditions)blocCaller).getComparateur().getFormule());
            
            
            // à finir
        }
    }
    
}
