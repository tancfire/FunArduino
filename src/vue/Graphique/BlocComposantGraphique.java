/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.Graphique;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tancfire
 */
public abstract class BlocComposantGraphique extends BlocGraphique{
    protected ComposantGraphique compGraph;
    private JLabel labelLigne;
    private JLabel labelLigneVert;

    public BlocComposantGraphique(ImageIcon image, ComposantGraphique compGraph) {
        super(image);
        this.compGraph = compGraph;
        labelLigne = new JLabel(new ImageIcon("src/images/lignePointillee.png"));
        labelLigneVert = new JLabel(new ImageIcon("src/images/lignePointilleeVertical.png"));
    }
    
    
    @Override
    public void attacher(JPanel panel)
    {
        panel.add(labelLigne);
        panel.add(labelLigneVert);
        super.attacher(panel);
    }
    
    
    @Override
    public void detacher(JPanel panel)
    {
        panel.remove(labelLigne);
        panel.remove(labelLigneVert);
        super.detacher(panel);
    }
    
    
    @Override
    public void mettreAjour()
    {
        super.mettreAjour();
              
        int longueur = compGraph.getX()-this.getX()-120+(compGraph.getWidth()/3);
        int hauteur = compGraph.getY()-this.getY()-22+compGraph.getHeight();
        
        if(longueur>0){
            labelLigne.setLocation(this.getX()+120, this.getY()+22);
            labelLigne.setSize(longueur, 5);
        }else{
            labelLigne.setLocation(this.getX()+120+longueur, this.getY()+22);
            labelLigne.setSize(longueur*-1, 5);
        }
        
        if(hauteur>0){
            labelLigneVert.setLocation(this.getX()+longueur+120, this.getY()+22);
            labelLigneVert.setSize(5, hauteur);
        }else{
            labelLigneVert.setLocation(this.getX()+longueur+120, this.getY()+hauteur+22);
            labelLigneVert.setSize(5, hauteur*-1); 
        }
        
    }
    
    public void changerComposantGraphique(ComposantGraphique compGraph)
    {
        this.compGraph = compGraph;
    }

    
    
}
