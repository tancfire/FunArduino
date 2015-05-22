/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue.BlocGraphique;

import Modèle.Bloc;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * En phase de test. Représentera le bloc sur la partie graphique.
 * @author tancfire
 */
public class BlocGraphique extends JLabel{
    private Bloc bloc;
    private JLabel label;
    private JLabel label2;
    private static int nbrPosition = 0;
    private int position;
    
    public BlocGraphique(final Bloc bloc, String texte, String texte2, ImageIcon image)
    {
        super(image);
        this.bloc = bloc;
        setSize(120,45);
        
        label = new JLabel(texte);
        label.setSize(120, 45);
        label.setHorizontalAlignment(CENTER);
        
        label2 = new JLabel(texte2);
        label2.setSize(100, 45);
        label2.setHorizontalAlignment(CENTER);
        
        // position "par défaut"
        position = 0;
        
        mettreAjour();
        
        
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
               // System.out.println("coucou");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("clic");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getX()>=0 && e.getX()<=120){
                    if(e.getY()>=45){
                        bloc.move((e.getY()/45));
                      //  System.out.println("deplacement vers le bas");
                     }else if(e.getY()<=0)
                     {
                       bloc.move((e.getY()/45)-1);
                    //   System.out.println("deplacement vers le haut");
                     }
                }else if(e.getX()<0){
                    bloc.descendreNiveau();
                 //   System.out.println("descendre d'un niveau");
                }else{
                    bloc.monterNiveau();
                  //  System.out.println("monter d'un niveau");
                }
              //  System.out.println("relaché: "+e.getX()+","+e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
              //  System.out.println("La souris rentre");
            }

            @Override
            public void mouseExited(MouseEvent e) {
               //  System.out.println("La souris sort.");
            }
        }); 
    }
    
      
    public void setPosition(int position)
    {
        this.position = position;
        mettreAjour();
    }
    
    
    public void mettreAjour()
    {
        setLocation(bloc.getNiveau()*40, position*45);
        label.setLocation((bloc.getNiveau()*40), (position*45)-10);
        label2.setLocation((bloc.getNiveau()*40)+10, (position*45)+10);
    }
    
    
    public void attacher(JPanel panel)
    {
        panel.add(label);
        panel.add(label2);
        panel.add(this);
    }
    
    
     public void detacher(JPanel panel)
    {
        panel.remove(label);
        panel.remove(label2);
        panel.remove(this);
    }
            
    public int getId()
    {
        return bloc.getId();
    }

    public int getPosition() {
        return position;
    }
}
