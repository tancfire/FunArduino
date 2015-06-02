/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vue;

import vue.BlocGraphique.BlocGraphique;
import Controleur.Controleur;
import Modèle.Bloc;
import Modèle.BlocAttendre;
import Modèle.BlocChangerVariable;
import Modèle.Composant;
import Modèle.ComposantLed;
import Modèle.TypeVariable;
import Modèle.Variable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import vue.BlocGraphique.ComposantGraphique;
import vue.BlocGraphique.SimulateurGraphique;
import vue.BlocGraphique.StockCouleurTexte;


/**
 * 
 * Prochaines améliorations: faire une liste des blocs graphiques et créer des
 * méthodes pour pouvoir la gérer (Pour le positionnement en Y).
 * @author tancfire
 */
public class KitArduinoFrame extends javax.swing.JFrame {
    private Controleur ctrl;
    private ArrayList<BlocGraphique> sesBlocsGraphs;
    private Bloc blocCaller;
    private boolean modifier;
    
    /**
     * Creates new form KitArduinoFrame
     */
    public KitArduinoFrame() {
            initComponents();
            modifier = true;
                       
            nouveauFichier.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                   if(nouveauFichier.getSelectedFile()!=null){
                    ctrl.creerProjet(nouveauFichier.getSelectedFile().getPath().substring(0, nouveauFichier.getSelectedFile().getPath().length()-nouveauFichier.getSelectedFile().getName().length()), nouveauFichier.getSelectedFile().getName());
                    actualiserTitre();
                    ctrl.remettreAZero();
                   }
                }
            });
            
            enregistrerFichier.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                  if(enregistrerFichier.getSelectedFile()!=null){ 
                    ctrl.changerSauvegardeProjet(enregistrerFichier.getSelectedFile().getPath().substring(0, enregistrerFichier.getSelectedFile().getPath().length()-enregistrerFichier.getSelectedFile().getName().length()), enregistrerFichier.getSelectedFile().getName());
                    actualiserTitre();
                  }
                }
            });
            
            ouvrirFichier.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                 if(ouvrirFichier.getSelectedFile()!=null){
                    ctrl.charger(ouvrirFichier.getSelectedFile().getPath().substring(0, ouvrirFichier.getSelectedFile().getPath().length()-ouvrirFichier.getSelectedFile().getName().length()), ouvrirFichier.getSelectedFile().getName());
                    actualiserTitre();
                 }
                }
            });
            
            /*=================================
            ----CLIC SUR LE PANEL GRAPHIQUE----
            ==================================*/
            
             this.panelGraphique.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                  
                }

                @Override
                public void mousePressed(MouseEvent e) {
                     if(e.getButton()==BUTTON1)//clic gauche
                 {
                   menuModifier.setVisible(false);
                   menuAjoutVarComp.setVisible(false);
                 }else if(e.getButton()==BUTTON3)//clic droit
                 {
                    menuModifier.setVisible(false);
                    menuAjoutVarComp.setVisible(true);
                    menuAjoutVarComp.setLocation(e.getXOnScreen(), e.getYOnScreen());
                 }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                   
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    
                }
            });
            
            
            sesBlocsGraphs = new ArrayList<BlocGraphique>();
            ctrl = new Controleur(this);
            ctrl.mettreAjourCode();
            
            
            actualiserTitre();
    }
    
    
    public void mettreAJourBlocsGraphiques(ArrayList<BlocGraphique> blocsGraphs)
    {
        //effacer tout les anciens blocs graphiques
        for(int i=0; i<sesBlocsGraphs.size();i++)
        {
            supprimerBlocGraphique(sesBlocsGraphs.get(i));
        }
        sesBlocsGraphs = blocsGraphs;
        
        for(int i=0; i<sesBlocsGraphs.size();i++)
        {
            sesBlocsGraphs.get(i).setPosition(i);
            ajouterBlocGraphique(sesBlocsGraphs.get(i));
        }
    }
    
    
    
    public void actualiserTitre()
    {
      this.setTitle("FunArduino ("+ctrl.getNomProjet()+")");
    }
    
    // A mettre dans le controleur
    public void setCode(String code)
    {
     ArrayList<StockCouleurTexte> stock = new ArrayList<StockCouleurTexte>();
     String codeFinal="";
     boolean bool = true;
        
     //Avec cet algorithme, on cherche à trouver toutes les balises [color rXXX bXXX gXXX] et [/color]
     String txtP = "\\[color r\\d{1,3} b\\d{1,3} g\\d{1,3}\\](.|$|\\n)*?\\[/color\\]";
     Pattern p = Pattern .compile(txtP);
    String entree = code;
     Matcher m = p.matcher(entree);
     
        while (m.find()){ //Lorsque l'on tombe sur une des balises
        if(bool==true)
        {
            codeFinal+= entree.substring(0,m.start()); // on ajoute tout ce qui se trouve avant le premier [color]
            bool = false;
        }
      
      String expression = entree.substring(m.start(), m.end()); //On récupère l'expression [color ...] ... [/color]
                //Cette fois, on regarde DANS la balise pour récupérer le code couleur
                //Il y a surement possibilité de l'optimiser
                 String patternBaliseE = "\\[color r\\d{1,3} b\\d{1,3} g\\d{1,3}\\]";
                 Pattern p2 = Pattern .compile(patternBaliseE);
                 Matcher m2 = p2.matcher(expression);
                 if(m2.find()) //Si on tombe sur la balise (normalement, il ne devrait pas y avoir de problèmes ...)
                 {
                     String baliseE = expression.substring(m2.start(), m2.end()); //On récupère que la balise du début
                     String txtCode= expression.substring(m2.end(),expression.length()-8);//On récupère l'expression entre les deux balises
                     codeFinal+=txtCode; //On ajoute l'expression contenu entre les deux balises dans le code final.
                     
                      int c1 =  decoderCouleur(baliseE, "r"); //on récupère la valeur du rouge pour le code couleur
                      int c2 =  decoderCouleur(baliseE, "b"); //on récupère la valeur du bleu pour le code couleur
                      int c3 =  decoderCouleur(baliseE, "g"); //on récupère la valeur du vert pour le code couleur
                     
                     stock.add(new StockCouleurTexte(new Color(c1,c3,c2), codeFinal.length()-txtCode.length(), txtCode.length()));//On ajoute un nouveau StockCouleurTexte (qui stocke où se trouve les couleurs) dans le stock.
                 }
        }
          editCode.setText(codeFinal); //On ajoute le code final au Panel.
          
          for(int i=0; i<stock.size(); i++) //On parcourt le stock (où sont sotcké les couleurs avec leurs positions).
          {
              StockCouleurTexte stockI = stock.get(i);
              setJTextPaneFont(editCode, stockI.getCouleur(), stockI.getDebut(), stockI.getLongueur()); //On définit la couleur du stockI pour l'appliquer au panel.
          }
    }
    
    /*============================================================================================
    --------------------------------Pour la couleur dans le texte -------------------------------
    ============================================================================================*/
    
    
    private int decoderCouleur(String balise, String cCouleur)
    {
       int couleur=0; //entre 0 et 255
       
       String patternC1 = cCouleur+"\\d{1,3}";
       Pattern p3 = Pattern.compile(patternC1);
       Matcher m3 = p3.matcher(balise);
       if(m3.find())
       couleur = Integer.parseInt(balise.substring(m3.start()+1,m3.end()));
        
        return couleur;
    }
    
    
    private void setJTextPaneFont(JTextPane jtp, Color c, int from, int length) {
            StyleContext sc = StyleContext.getDefaultStyleContext();

            AttributeSet attrs = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

            StyledDocument doc = jtp.getStyledDocument();
            doc.setCharacterAttributes(from, length, attrs, true);
        }
    
    /*============================================================================================
    -------------------------------Fin pour la couleur dans le texte------------------------------
    ============================================================================================*/
    
    public void ajouterComposantGraphique(ComposantGraphique compGraph)
    {
        compGraph.attacher(panelGraphique);
        panelGraphique.repaint();
    }
    
        public void supprimerComposantGraphique(ComposantGraphique compGraph)
    {
        compGraph.detacher(panelGraphique);
        panelGraphique.repaint();
    }
    
    
    private void ajouterBlocGraphique(BlocGraphique blocGraph)
    {
        blocGraph.attacher(panelGraphique);
        panelGraphique.repaint();
    }
    
    private void supprimerBlocGraphique(BlocGraphique blocGraph)
    {
        blocGraph.detacher(panelGraphique);
       panelGraphique.repaint();
    }
    
    
    public void ouvrirMenuModifier(Bloc blocCaller, int x, int y)
    {
        menuModifier.setLocation(x-scrollPanelGraphique.getHorizontalScrollBar().getValue()+this.getX(), y-scrollPanelGraphique.getVerticalScrollBar().getValue()+this.getY());
        menuModifier.setVisible(true);
        this.blocCaller = blocCaller;
    }
    
    
    public void ouvrirChoixBlocsAAjouter(Bloc blocCaller)
    {
        choixBlocsAAjouter.setVisible(true);
        this.blocCaller = blocCaller;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        choixArduinoGroupe = new javax.swing.ButtonGroup();
        nouveauFichier = new javax.swing.JFileChooser();
        enregistrerFichier = new javax.swing.JFileChooser();
        ouvrirFichier = new javax.swing.JFileChooser();
        choixBlocsAAjouter = new javax.swing.JDialog();
        btnAjouterBloc = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listeBlocsAAjouter = new javax.swing.JList();
        modifierBlocAttendre = new javax.swing.JDialog();
        btnBlocAttendre = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        editDelais = new javax.swing.JSpinner();
        modifierBlocChangerVariable = new javax.swing.JDialog();
        btnBlocChangerVar = new javax.swing.JButton();
        listeVariablesBlocChangerVar = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        editValeurChangerVar = new javax.swing.JTextField();
        menuModifier = new javax.swing.JPopupMenu();
        itemModifier = new javax.swing.JMenuItem();
        menuAjoutVarComp = new javax.swing.JPopupMenu();
        itemAjoutVariable = new javax.swing.JMenuItem();
        itemAjoutComposant = new javax.swing.JMenuItem();
        ajoutVariable = new javax.swing.JDialog();
        editAjouterVariable = new javax.swing.JTextField();
        btnAjoutVariable = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        listeTypeVarAjout = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        editValeurDefautVariable = new javax.swing.JTextField();
        ajoutComposant = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeCompAAjouter = new javax.swing.JList();
        btnAjouterComp = new javax.swing.JButton();
        scrollPanelGraphique = new javax.swing.JScrollPane();
        panelGraphique = new javax.swing.JPanel();
        scrollEditCode = new javax.swing.JScrollPane();
        editCode = new javax.swing.JTextPane();
        btnTeleverser = new javax.swing.JButton();
        paneHistorique = new javax.swing.JTabbedPane();
        scrollListeObjets = new javax.swing.JScrollPane();
        listeHistorique = new javax.swing.JList();
        menuBarre = new javax.swing.JMenuBar();
        menuFichier = new javax.swing.JMenu();
        itemNouveau = new javax.swing.JMenuItem();
        itemOuvrir = new javax.swing.JMenuItem();
        itemSauvegarder = new javax.swing.JMenuItem();
        itemSauvegarderSous = new javax.swing.JMenuItem();
        itemQuitter = new javax.swing.JMenuItem();
        menuEdition = new javax.swing.JMenu();
        menuOutils = new javax.swing.JMenu();
        menuChoixArduino = new javax.swing.JMenu();
        itemLeonardo = new javax.swing.JRadioButtonMenuItem();
        itemUno = new javax.swing.JRadioButtonMenuItem();

        nouveauFichier.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        enregistrerFichier.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        ouvrirFichier.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        choixBlocsAAjouter.setTitle("Choisir un Bloc");
        choixBlocsAAjouter.setAlwaysOnTop(true);
        choixBlocsAAjouter.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        choixBlocsAAjouter.setMinimumSize(new java.awt.Dimension(480, 180));

        btnAjouterBloc.setText("Ajouter");
        btnAjouterBloc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterBlocActionPerformed(evt);
            }
        });

        listeBlocsAAjouter.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Attendre", "Changer la valeur d'une variable" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listeBlocsAAjouter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listeBlocsAAjouter.setSelectedIndex(0);
        jScrollPane1.setViewportView(listeBlocsAAjouter);

        javax.swing.GroupLayout choixBlocsAAjouterLayout = new javax.swing.GroupLayout(choixBlocsAAjouter.getContentPane());
        choixBlocsAAjouter.getContentPane().setLayout(choixBlocsAAjouterLayout);
        choixBlocsAAjouterLayout.setHorizontalGroup(
            choixBlocsAAjouterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixBlocsAAjouterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, choixBlocsAAjouterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAjouterBloc)
                .addContainerGap())
        );
        choixBlocsAAjouterLayout.setVerticalGroup(
            choixBlocsAAjouterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, choixBlocsAAjouterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAjouterBloc)
                .addContainerGap())
        );

        modifierBlocAttendre.setTitle("Choisir un Bloc");
        modifierBlocAttendre.setAlwaysOnTop(true);
        modifierBlocAttendre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        modifierBlocAttendre.setMinimumSize(new java.awt.Dimension(480, 180));

        btnBlocAttendre.setText("valider");
        btnBlocAttendre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBlocAttendreActionPerformed(evt);
            }
        });

        jLabel1.setText("Delais:");

        javax.swing.GroupLayout modifierBlocAttendreLayout = new javax.swing.GroupLayout(modifierBlocAttendre.getContentPane());
        modifierBlocAttendre.getContentPane().setLayout(modifierBlocAttendreLayout);
        modifierBlocAttendreLayout.setHorizontalGroup(
            modifierBlocAttendreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, modifierBlocAttendreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(modifierBlocAttendreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modifierBlocAttendreLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBlocAttendre)
                        .addContainerGap())
                    .addGroup(modifierBlocAttendreLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDelais, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(163, Short.MAX_VALUE))))
        );
        modifierBlocAttendreLayout.setVerticalGroup(
            modifierBlocAttendreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifierBlocAttendreLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(modifierBlocAttendreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(editDelais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBlocAttendre)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        modifierBlocChangerVariable.setTitle("Choisir un Bloc");
        modifierBlocChangerVariable.setAlwaysOnTop(true);
        modifierBlocChangerVariable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        modifierBlocChangerVariable.setMinimumSize(new java.awt.Dimension(480, 180));

        btnBlocChangerVar.setText("valider");
        btnBlocChangerVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBlocChangerVarActionPerformed(evt);
            }
        });

        jLabel2.setText("Variable:");

        jLabel3.setText("Valeur:");

        javax.swing.GroupLayout modifierBlocChangerVariableLayout = new javax.swing.GroupLayout(modifierBlocChangerVariable.getContentPane());
        modifierBlocChangerVariable.getContentPane().setLayout(modifierBlocChangerVariableLayout);
        modifierBlocChangerVariableLayout.setHorizontalGroup(
            modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifierBlocChangerVariableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modifierBlocChangerVariableLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBlocChangerVar)
                        .addContainerGap())
                    .addGroup(modifierBlocChangerVariableLayout.createSequentialGroup()
                        .addGroup(modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listeVariablesBlocChangerVar, 0, 135, Short.MAX_VALUE)
                            .addComponent(editValeurChangerVar))
                        .addGap(91, 91, 91))))
        );
        modifierBlocChangerVariableLayout.setVerticalGroup(
            modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modifierBlocChangerVariableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listeVariablesBlocChangerVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(modifierBlocChangerVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(editValeurChangerVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBlocChangerVar)
                .addContainerGap())
        );

        itemModifier.setText("Modifier");
        itemModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemModifierActionPerformed(evt);
            }
        });
        menuModifier.add(itemModifier);

        itemAjoutVariable.setText("Ajouter variable");
        itemAjoutVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAjoutVariableActionPerformed(evt);
            }
        });
        menuAjoutVarComp.add(itemAjoutVariable);

        itemAjoutComposant.setText("Ajouter composant");
        itemAjoutComposant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAjoutComposantActionPerformed(evt);
            }
        });
        menuAjoutVarComp.add(itemAjoutComposant);

        ajoutVariable.setMinimumSize(new java.awt.Dimension(395, 200));

        btnAjoutVariable.setText("ajouter");
        btnAjoutVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjoutVariableActionPerformed(evt);
            }
        });

        jLabel4.setText("nom de la variable:");

        jLabel5.setText("Type de la variable:");

        listeTypeVarAjout.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "nombre entier" }));

        jLabel6.setText("valeur par défaut:");

        javax.swing.GroupLayout ajoutVariableLayout = new javax.swing.GroupLayout(ajoutVariable.getContentPane());
        ajoutVariable.getContentPane().setLayout(ajoutVariableLayout);
        ajoutVariableLayout.setHorizontalGroup(
            ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajoutVariableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ajoutVariableLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editValeurDefautVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(btnAjoutVariable))
                    .addComponent(editAjouterVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ajoutVariableLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listeTypeVarAjout, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        ajoutVariableLayout.setVerticalGroup(
            ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ajoutVariableLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(listeTypeVarAjout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editAjouterVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ajoutVariableLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjoutVariable)
                        .addContainerGap())
                    .addGroup(ajoutVariableLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(ajoutVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(editValeurDefautVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );

        ajoutComposant.setMinimumSize(new java.awt.Dimension(379, 180));

        listeCompAAjouter.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "led" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listeCompAAjouter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listeCompAAjouter);

        btnAjouterComp.setText("ajouter");
        btnAjouterComp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterCompActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ajoutComposantLayout = new javax.swing.GroupLayout(ajoutComposant.getContentPane());
        ajoutComposant.getContentPane().setLayout(ajoutComposantLayout);
        ajoutComposantLayout.setHorizontalGroup(
            ajoutComposantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajoutComposantLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(ajoutComposantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAjouterComp)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        ajoutComposantLayout.setVerticalGroup(
            ajoutComposantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajoutComposantLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnAjouterComp)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FunArduino Project");
        setMinimumSize(new java.awt.Dimension(1024, 600));
        setPreferredSize(new java.awt.Dimension(1028, 600));

        panelGraphique.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelGraphiqueLayout = new javax.swing.GroupLayout(panelGraphique);
        panelGraphique.setLayout(panelGraphiqueLayout);
        panelGraphiqueLayout.setHorizontalGroup(
            panelGraphiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1111, Short.MAX_VALUE)
        );
        panelGraphiqueLayout.setVerticalGroup(
            panelGraphiqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );

        scrollPanelGraphique.setViewportView(panelGraphique);

        editCode.setEditable(false);
        scrollEditCode.setViewportView(editCode);

        btnTeleverser.setText("téléverser");
        btnTeleverser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTeleverserActionPerformed(evt);
            }
        });

        listeHistorique.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollListeObjets.setViewportView(listeHistorique);

        paneHistorique.addTab("Historique", scrollListeObjets);

        menuFichier.setText("Fichier");

        itemNouveau.setText("Nouveau");
        itemNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemNouveauActionPerformed(evt);
            }
        });
        menuFichier.add(itemNouveau);

        itemOuvrir.setText("Ouvrir");
        itemOuvrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOuvrirActionPerformed(evt);
            }
        });
        menuFichier.add(itemOuvrir);

        itemSauvegarder.setText("Enregistrer");
        itemSauvegarder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSauvegarderActionPerformed(evt);
            }
        });
        menuFichier.add(itemSauvegarder);

        itemSauvegarderSous.setText("Enregistrer-sous");
        itemSauvegarderSous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSauvegarderSousActionPerformed(evt);
            }
        });
        menuFichier.add(itemSauvegarderSous);

        itemQuitter.setText("Quitter");
        itemQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemQuitterActionPerformed(evt);
            }
        });
        menuFichier.add(itemQuitter);

        menuBarre.add(menuFichier);

        menuEdition.setText("Edition");
        menuBarre.add(menuEdition);

        menuOutils.setText("Outils");

        menuChoixArduino.setText("Arduino");

        choixArduinoGroupe.add(itemLeonardo);
        itemLeonardo.setSelected(true);
        itemLeonardo.setText("Leonardo");
        itemLeonardo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemLeonardoActionPerformed(evt);
            }
        });
        menuChoixArduino.add(itemLeonardo);

        choixArduinoGroupe.add(itemUno);
        itemUno.setText("Uno");
        menuChoixArduino.add(itemUno);

        menuOutils.add(menuChoixArduino);

        menuBarre.add(menuOutils);

        setJMenuBar(menuBarre);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(paneHistorique, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPanelGraphique, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollEditCode, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTeleverser, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollEditCode)
                    .addComponent(scrollPanelGraphique, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                    .addComponent(paneHistorique))
                .addGap(18, 18, 18)
                .addComponent(btnTeleverser)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemQuitterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemQuitterActionPerformed

    private void btnTeleverserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTeleverserActionPerformed
      ctrl.compilerEtTeleverser(editCode.getText(), getSelectedButtonText(choixArduinoGroupe));
            
    }//GEN-LAST:event_btnTeleverserActionPerformed

    private void itemLeonardoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLeonardoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_itemLeonardoActionPerformed

    private void itemSauvegarderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSauvegarderActionPerformed
        // TODO add your handling code here:
        ctrl.sauvegarder();
    }//GEN-LAST:event_itemSauvegarderActionPerformed

    private void itemOuvrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOuvrirActionPerformed
        // TODO add your handling code here:
       ouvrirFichier.setCurrentDirectory(new File("./saves"));
       ouvrirFichier.showOpenDialog(this);
    }//GEN-LAST:event_itemOuvrirActionPerformed

    private void itemSauvegarderSousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSauvegarderSousActionPerformed
       enregistrerFichier.setCurrentDirectory(new File("./saves")); 
       enregistrerFichier.showSaveDialog(this);
    }//GEN-LAST:event_itemSauvegarderSousActionPerformed

    private void itemNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemNouveauActionPerformed
        // TODO add your handling code here:
       nouveauFichier.setCurrentDirectory(new File("./saves")); 
       nouveauFichier.showSaveDialog(this);
    }//GEN-LAST:event_itemNouveauActionPerformed

    
    
    
    private void btnAjouterBlocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterBlocActionPerformed
        // TODO add your handling code here:
        if(blocCaller!=null){ 
            modifier = false;
            String selection = (String) listeBlocsAAjouter.getSelectedValue();
            choixBlocsAAjouter.setVisible(false);
            switch (selection) {
                case "Attendre":
                    modifierBlocAttendre.setVisible(true);
                    break;
                case "Changer la valeur d'une variable":
                    listeVariablesBlocChangerVar.removeAllItems();
                     for(int i=0; i<ctrl.getVariables().size();i++)
                     {
                          listeVariablesBlocChangerVar.addItem(ctrl.getVariables().get(i).getNom());
                    }
                    modifierBlocChangerVariable.setVisible(true);
                    break;
            }
             }
    }//GEN-LAST:event_btnAjouterBlocActionPerformed

    
    private void btnBlocChangerVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBlocChangerVarActionPerformed
        // TODO add your handling code here:
        if((ctrl.getVariables().get(listeVariablesBlocChangerVar.getSelectedIndex()).getTypeVariable()== TypeVariable.texte) 
                ||(ctrl.getVariables().get(listeVariablesBlocChangerVar.getSelectedIndex()).getTypeVariable()== TypeVariable.nombreEntier && isInteger(editValeurChangerVar.getText()))){
        ctrl.ajouterBloc(blocCaller, new BlocChangerVariable(ctrl.getVariables().get(listeVariablesBlocChangerVar.getSelectedIndex()), editValeurChangerVar.getText(), ctrl));
        modifierBlocChangerVariable.setVisible(false);
        blocCaller = null;
        }
    }//GEN-LAST:event_btnBlocChangerVarActionPerformed

    
    private static boolean isInteger(String s) {
    return isInteger(s,10);
    }

    private static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
        return true;
    }
    
    
    private void btnBlocAttendreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBlocAttendreActionPerformed
        // TODO add your handling code here:
        if(blocCaller!=null){ 
            if(modifier==false){
                ctrl.ajouterBloc(blocCaller, new BlocAttendre((Integer) editDelais.getValue(), ctrl));
            }else{
                ((BlocAttendre)blocCaller).setDelai((Integer) editDelais.getValue());
                ctrl.mettreAjourCode();
            }
        modifierBlocAttendre.setVisible(false);
        blocCaller = null;
        }
    }//GEN-LAST:event_btnBlocAttendreActionPerformed

    private void itemModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemModifierActionPerformed
        // TODO add your handling code here:
        modifier = true;
        menuModifier.setVisible(false);
        if(blocCaller instanceof BlocAttendre)
        {
             modifierBlocAttendre.setVisible(true);
        }
    }//GEN-LAST:event_itemModifierActionPerformed

    private void itemAjoutVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAjoutVariableActionPerformed
        // TODO add your handling code here:
        menuAjoutVarComp.setVisible(false);
        listeTypeVarAjout.removeAllItems();
        for(int i=0; i<TypeVariable.values().length;i++)
        {
                listeTypeVarAjout.addItem(TypeVariable.values()[i]);
        }
        ajoutVariable.setVisible(true);
    }//GEN-LAST:event_itemAjoutVariableActionPerformed

    private void btnAjoutVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjoutVariableActionPerformed
        // TODO add your handling code here:
        if(!editValeurDefautVariable.getText().equals("") && !editAjouterVariable.getText().equals("")){ //On vérfiie que les champs de texte ne sont pas vides
                if((isInteger(editValeurDefautVariable.getText())&&listeTypeVarAjout.getSelectedItem()==TypeVariable.nombreEntier) //si le texte est un nombre et que type est "nombre entier'
                        || listeTypeVarAjout.getSelectedItem()==TypeVariable.texte){ //ou le type est "texte"
                ctrl.ajouterVariable(new Variable((TypeVariable)listeTypeVarAjout.getSelectedItem(), editAjouterVariable.getText(), editValeurDefautVariable.getText(), ctrl)); //On ajoute la variable
                ajoutVariable.setVisible(false); //on ferme la boite de dialogue
                }
        }
    }//GEN-LAST:event_btnAjoutVariableActionPerformed

    private void itemAjoutComposantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAjoutComposantActionPerformed
        // TODO add your handling code here:
        menuAjoutVarComp.setVisible(false);
        ajoutComposant.setVisible(true);
    }//GEN-LAST:event_itemAjoutComposantActionPerformed

    private void btnAjouterCompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterCompActionPerformed
        // TODO add your handling code here:
        if(listeCompAAjouter.getSelectedValue().equals("led"))
        {
          ctrl.ajouterComposant(new ComposantLed(ctrl));
        }
        ajoutComposant.setVisible(false);
    }//GEN-LAST:event_btnAjouterCompActionPerformed

    
     private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KitArduinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KitArduinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KitArduinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KitArduinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KitArduinoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ajoutComposant;
    private javax.swing.JDialog ajoutVariable;
    private javax.swing.JButton btnAjoutVariable;
    private javax.swing.JButton btnAjouterBloc;
    private javax.swing.JButton btnAjouterComp;
    private javax.swing.JButton btnBlocAttendre;
    private javax.swing.JButton btnBlocChangerVar;
    private javax.swing.JButton btnTeleverser;
    private javax.swing.ButtonGroup choixArduinoGroupe;
    private javax.swing.JDialog choixBlocsAAjouter;
    private javax.swing.JTextField editAjouterVariable;
    private javax.swing.JTextPane editCode;
    private javax.swing.JSpinner editDelais;
    private javax.swing.JTextField editValeurChangerVar;
    private javax.swing.JTextField editValeurDefautVariable;
    private javax.swing.JFileChooser enregistrerFichier;
    private javax.swing.JMenuItem itemAjoutComposant;
    private javax.swing.JMenuItem itemAjoutVariable;
    private javax.swing.JRadioButtonMenuItem itemLeonardo;
    private javax.swing.JMenuItem itemModifier;
    private javax.swing.JMenuItem itemNouveau;
    private javax.swing.JMenuItem itemOuvrir;
    private javax.swing.JMenuItem itemQuitter;
    private javax.swing.JMenuItem itemSauvegarder;
    private javax.swing.JMenuItem itemSauvegarderSous;
    private javax.swing.JRadioButtonMenuItem itemUno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listeBlocsAAjouter;
    private javax.swing.JList listeCompAAjouter;
    private javax.swing.JList listeHistorique;
    private javax.swing.JComboBox listeTypeVarAjout;
    private javax.swing.JComboBox listeVariablesBlocChangerVar;
    private javax.swing.JPopupMenu menuAjoutVarComp;
    private javax.swing.JMenuBar menuBarre;
    private javax.swing.JMenu menuChoixArduino;
    private javax.swing.JMenu menuEdition;
    private javax.swing.JMenu menuFichier;
    private javax.swing.JPopupMenu menuModifier;
    private javax.swing.JMenu menuOutils;
    private javax.swing.JDialog modifierBlocAttendre;
    private javax.swing.JDialog modifierBlocChangerVariable;
    private javax.swing.JFileChooser nouveauFichier;
    private javax.swing.JFileChooser ouvrirFichier;
    private javax.swing.JTabbedPane paneHistorique;
    private javax.swing.JPanel panelGraphique;
    private javax.swing.JScrollPane scrollEditCode;
    private javax.swing.JScrollPane scrollListeObjets;
    private javax.swing.JScrollPane scrollPanelGraphique;
    // End of variables declaration//GEN-END:variables

    public void ajouterSimulateur(SimulateurGraphique simuGraph) {
       panelGraphique.add(simuGraph);
       panelGraphique.repaint();
    }

    public void mettreAJourBranchements(ArrayList<Composant> listeComp) {
        for(int i=0; i<listeComp.size();i++)
        {
            listeComp.get(i).getCompGraph().mettreAJour();
        }
    }
}
