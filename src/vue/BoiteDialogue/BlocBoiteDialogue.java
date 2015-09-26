/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BoiteDialogue;

import Controleur.Controleur;
import Modèle.Bloc;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel; 

/**
 * La boite de dialogue pour créer un bloc
 * @author tancfire
 */
public abstract class BlocBoiteDialogue extends JDialog{  
    private final JButton btnValider;
    protected final JPanel panelPrincipal;
    protected final Controleur ctrl;
    protected Bloc blocCaller;
    protected boolean modifier;
    
    public BlocBoiteDialogue(int longueur, int largeur, Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
        Dimension dim = new Dimension(longueur, largeur);
        setMinimumSize(dim);
        setSize(dim);
        setResizable(false);
        setAlwaysOnTop(true);
        //GridLayout layout = new GridLayout(0,4);
        JPanel pane1 = new JPanel(new BorderLayout());
        panelPrincipal = new JPanel();
        
        btnValider = new JButton("Valider");
        btnValider.setSize(50, 30);
        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                onValider();
                setVisible(false); //On ferme la fenêtre de la boite de dialogue
            }
        });
        
        this.add(pane1, BorderLayout.PAGE_END);
        this.add(panelPrincipal);
        
        pane1.add(btnValider, BorderLayout.EAST);
    }
    
    
    public void ouvrir(Bloc blocCaller, boolean modifier)
    {
        onOuverture();
        setVisible(true);
        this.blocCaller = blocCaller;
        this.modifier = modifier;
    }
    
    
    
    public abstract void onValider();
    
    public abstract void onOuverture();
    
    
    
        /**
     * Permet de mettre dans une combobox la liste des variables
     * @param comboBox la combobox dans laquelle on souhaite mettre la liste des varaibles.
     */
    protected void mettreDansListeVariables(JComboBox comboBox)
    {
        comboBox.removeAllItems();
        for(int i=0; i <ctrl.getVariables().size();i++)
        {
            comboBox.addItem(ctrl.getVariables().get(i).getNom());
        }
    }
    
    /**
     * Permet de mettre dans une combobox la listes des composants
     * @param comboBox la comboboc dans laquelle on souhaite mettre la liste des composants.
     */
    protected void mettreDansListeComposants(JComboBox comboBox)
    {
        System.out.println("les comps: "+ctrl.getComposants());
        comboBox.removeAllItems();
        for(int i=0; i <ctrl.getComposants().size();i++)
        {
            comboBox.addItem(ctrl.getComposants().get(i).getNom()+" "+(i+1));
        }
    }
    
}
