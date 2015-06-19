/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controleur;

import Modèle.AssemblageBlocs;
import Modèle.Bloc;
import Modèle.BlocAllumerPin;
import Modèle.BlocAttendre;
import Modèle.BlocConditions;
import Modèle.BlocInit;
import Modèle.BlocLibrairies;
import Modèle.BlocStart;
import Modèle.BlocUpdate;
import Modèle.Comparateur;
import Modèle.Composant;
import Modèle.ComposantLed;
import Modèle.EtatPin;
import Modèle.SimulateurArduino;
import Modèle.TypeBloc;
import Modèle.TypeVariable;
import Modèle.Variable;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import saveSystem.AccesXML;
import vue.KitArduinoFrame;

/**
 * Le contrôleur permet de gérer tout le système interne au logiciel.
 * @author tancfire
 */
public class Controleur {
    KitArduinoFrame vue;
    
    AccesXML acces;
    String nomProjet;
    String chemin;
    
    ArrayList<Composant> composants;
    ArrayList<Variable> variables;
    AssemblageBlocs assemblage;
    SimulateurArduino simulateur;
    
    BlocLibrairies blocLibrairies;
    BlocInit blocInit;
    BlocStart blocStart;
    BlocUpdate blocUpdate;
    
    private final DocumentBuilderFactory factory;
    
    public Controleur(KitArduinoFrame vue)
    {
      //  System.out.println(System.getProperty("os.name"));
        this.vue = vue;
        composants = new ArrayList<>();
        variables = new ArrayList<>();
        factory = DocumentBuilderFactory.newInstance();
        
        acces = new AccesXML();
        
        creerProjet(new File("saves").getAbsolutePath(), "GenerateByFunArduino"); //Création du projet par défaut
       
        
        //On initialise tout à zéro
        remettreAZero();
        
        //Initialisation de divers éléments pour la zone de test
        ComposantLed led = new ComposantLed(this);
        ajouterComposant(led);
        
        Variable varEtat = new Variable(TypeVariable.nombreEntier, "etat", "0", this);
        ajouterVariable(varEtat);
        
        //============================== ZONE DE TEST ============================== //
        
        BlocConditions conditions = new BlocConditions((Object)varEtat,(Object)42,Comparateur.egal,this);
      //  BlocConditions conditions2 = new BlocConditions((Object)varEtat,(Object)1,Comparateur.egal);
        
        blocUpdate.ajouterBlocALaFin(new BlocAllumerPin(led, EtatPin.HAUT,this));
        blocUpdate.ajouterBlocALaFin(conditions);
        blocUpdate.ajouterBlocALaFin(new BlocAttendre(500,this));
        blocUpdate.ajouterBlocALaFin(new BlocAllumerPin(led, EtatPin.BAS,this));
        blocUpdate.ajouterBlocALaFin(new BlocAttendre(500,this));
        
        conditions.ajouterBlocALaFin( new BlocAllumerPin(led, EtatPin.HAUT,this));
    }
    
      
    /**
     * Cette méthode permet de mettre à jour le code généré.
     */
    public void mettreAjourCode()
    {
        vue.setCode(assemblage.getCode());
        vue.mettreAJourBlocsGraphiques(assemblage.getBlocsGraphiques());
    }
    
    
    /**
     * Cette méthode permet de mettre à jour les blocs graphiques
     */
    public void mettreAJourBlocsGraphiques() {
        vue.mettreAJourBlocsGraphiques(assemblage.getBlocsGraphiques());
    }
    
    /**
     * Cette méthode permet de (re)mettre à zéro tout ce qui est nécessaire.
     */
    public void remettreAZero()
    {  
       if(this.simulateur!=null)//si le simulateur existe
       vue.supprimerSimulateur(this.simulateur.getSimuGraph());//On le supprime
       
       //On supprime toutes les variables
       for(int i=0; i<variables.size();i++)
       {
           supprimerVariable(variables.get(i));
       }
       
       //On supprime tout les composants
       for(int i=0; i<composants.size();i++)
       {
           supprimerComposant(composants.get(i));
       }
       
       //On re-créer tout:
       this.simulateur = new SimulateurArduino(this);
       vue.ajouterSimulateur(this.simulateur.getSimuGraph());
       
        assemblage = new AssemblageBlocs(acces);
        blocLibrairies = new BlocLibrairies(this);
        blocStart = new BlocStart(this);
        blocUpdate = new BlocUpdate(this);
        blocInit = new BlocInit(this);
        
        //On ajoute les blocs racines:
        assemblage.ajouterBloc(0, blocLibrairies);
        assemblage.ajouterBloc(1, blocInit);
        assemblage.ajouterBloc(2, blocStart);
        assemblage.ajouterBloc(3, blocUpdate);
        
        mettreAjourCode();
    }
      
    
    /**
     * Cette méthode permet d'ouvrir un menu afin de pouvoir modifier un bloc
     * @param blocCaller le bloc sur lequel a été effectué le clic droit
     * @param x la position x du curseur
     * @param y la position y du curseur
     */
    public void ouvrirMenuModifier(Bloc blocCaller, int x, int y)
    {
        vue.ouvrirMenuModifier(blocCaller,x,y);
    }
    
    /**
     * Cette méthode permet d'ouvrir un menu pour choisir le bloc à selectionner
     * @param blocCaller le bloc parent qui accueillera le nouveau bloc
     */
    public void ouvrirChoixBlocsAAjouter(Bloc blocCaller)
    {
        vue.ouvrirChoixBlocsAAjouter(blocCaller);
    }

    /**
     * Cette méthode permet de mettre à jour l'affichage des branchements
     */
    public void mettreAJourBranchements()
    {
        vue.mettreAJourBranchements(composants);
    }
    
    /**
     * Cette méthode permet d'ajouter un bloc fils à un bloc parent
     * @param blocParent le bloc qui prendra le bloc fils
     * @param blocFils  le bloc qu'on souhaite ajouter au bloc parent
     */
    public void ajouterBloc(Bloc blocParent, Bloc blocFils)
    {
        blocParent.ajouterBlocALaFin(blocFils);
        mettreAjourCode();
    }
    
     /**
      * Cette méthode permet d'ajouter une variable
      * @param variable la variable qu'on souhaite ajouter
      */
    public void ajouterVariable(Variable variable)
    {
        variables.add(variable);
        mettreAjourCode();
    }
    
    
    /**
     * Cette méthode permet de supprimer une variable
     * @param var la varaible que l'on souhaite supprimer.
     */
    public void supprimerVariable(Variable var) {
        variables.remove(var);
        mettreAjourCode();
    }
        
    
    /**
     * Cette méthode permet d'ajouter un composant
     * @param composant le composant qu'on souhaite ajouter
     */
    public void ajouterComposant(Composant composant)
    {
        composants.add(composant);
        if(composant.getCompGraph()!=null)//Si le composant graphique existe
        vue.ajouterComposantGraphique(composant.getCompGraph());//On l'ajoute à la vue
        mettreAjourCode();
    }
    
    /**
     * Cette méthode permet de supprimer un composant
     * @param composant le composant qu'on souhaite supprimer
     */
    public void supprimerComposant(Composant composant)
    {
        composants.remove(composant);
        if(composant.getCompGraph()!=null)
        vue.supprimerComposantGraphique(composant.getCompGraph());
        mettreAjourCode();
    }
    
    
    /**
     * Cette méthode permet de récupérer le simulateur
     * @return le simulateur actuel
     */
    public SimulateurArduino getSimulateur()
    {
        return simulateur;
    }
    
    /**
     * Cette méthode permet de rajouter un bloc au bloc racine "setup"
     * @param unBloc 
     */
    public void ajouterAuSetup(Bloc unBloc)
    {
        blocStart.ajouterBlocALaFin(unBloc);
    }
    
    /**
     * Cette méthode permet d'ajouter un bloc au bloc racine "setup", mais sans 
     * le sauvegarder dans le fichier xml
     * @param unBloc 
     */
    public void ajouterAuSetupSansSauvegarde(Bloc unBloc)
    {
        blocStart.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
    /**
     * Cette méthode permet d'ajouter un bloc au bloc racine "loop"
     * @param unBloc 
     */
    public void ajouterAuLoop(Bloc unBloc)
    {
        blocUpdate.ajouterBlocALaFin(unBloc);
    }
    
    
    public void ajouterAuLoopSansSauvegarde(Bloc unBloc)
    {
        blocUpdate.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
         
    public void ajouterAuInit(Bloc unBloc)
    {
        blocInit.ajouterBlocALaFin(unBloc);
    }
    
    
    public void ajouterAuInitSansSauvegarde(Bloc unBloc)
    {
        blocInit.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
    
    
        /*===================================================================
        ---------------- SAUVEGARDES ET GESTION DE FICHIERS -----------------
        ===================================================================*/
        
       public void changerSauvegardeProjet(String nouveauChemin, String nouveauNomProjet)
    {
        // On sauvegarde pour s'assurer qu'il n'y aura pas de pertes
        sauvegarder();

        //Création du chemin si il n'existe pas:
        if(!new File(nouveauChemin+"/"+nouveauNomProjet).exists())
           new File(nouveauChemin+"/"+nouveauNomProjet).mkdirs();

        //Copie du fichier project
       copyFile(new File(chemin+"/"+nomProjet+"/project.fun"), new File(nouveauChemin+"/"+nouveauNomProjet+"/project.fun"));
        
        
        //On change le nom et le chemin à la fin
        this.nomProjet = nouveauNomProjet;
        this.chemin = nouveauChemin;
        acces.setChemin(chemin+"/"+nomProjet+"/project.fun");
    }
    
    
    
    public void creerProjet(String chemin, String nomProjet)
    {      
        //On initialise le projet et sa sauvegarde
        this.nomProjet = nomProjet;
        this.chemin = chemin;
        
        //Création du chemin si il n'existe pas:
        if(!new File(chemin+"/"+nomProjet).exists())
           new File(chemin+"/"+nomProjet).mkdirs();

        
        //Création du fichier de sauvegarde (.xml) si il n'existe pas:
        if(!new File(chemin+"/"+nomProjet+"/project.fun").exists())
        {
            try {
                new File(chemin+"/"+nomProjet+"/project.fun").createNewFile();
                PrintWriter out = new PrintWriter(chemin+"/"+nomProjet+"/project.fun");
                out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root/>");
                out.close();
                
            } catch (IOException ex) {
                System.err.println("Impossible de créer le fichier de sauvegarde: "+ex.getMessage());
            }
        }
      
        //On initialise la classe d'accès sur le fichier qui vient d'être créé.
        acces.setChemin(chemin+"/"+nomProjet+"/project.fun"); //--> saves/GenerateByArduino/GenerateByArduino.fun
    }
        
       
    /**
     * Cette méthode permet de compiler et téléverser du code sur l'arduino
     * @param code le code que l'on téléverse sur l'arduino
     * @param arduino l'arduino sur lequel on souhaite téléverser le code
     */
    public void compilerEtTeleverser(String code, String arduino)
    {
        //sauvegarde du projet
        sauvegarder();
      
        try {   
       //Ecriture dans le fichier .ino qui sera chargé et compilé
            PrintWriter out = new PrintWriter(chemin+"/"+nomProjet+"/"+nomProjet+".ino");// --> saves/GenerateByFunArduino/GenerateByFunArduino.ino
            out.println(code);
            out.close();
          
       //Compilation et Téléversement à partir de l'IDE Arduino
            Process p1 = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Arduino\\arduino --board arduino:avr:"+arduino+" --port COM12 --upload "+chemin+"\\"+nomProjet+"\\"+nomProjet+".ino");
            
            p1.waitFor();
            int v = p1.exitValue();
            if(v!=0)
            {
                System.err.println("erreur lors de la compilation: "+v);
            }else{
                System.out.println("La compilation a réussi: "+v);
            }
                
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
             System.err.println(ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
        
       
    /**
     * Cette méthode permet de charger un projet
     * @param chemin le chemin pour accéder au dossier du projet
     * @param nomProjet le nom du projet
     */
    public void charger(String chemin, String nomProjet)
    {
            //effacer le programme précédent
            remettreAZero();
            //on change le nom du projet et le chemin
            this.nomProjet = nomProjet;
            this.chemin = chemin;
            acces.setChemin(chemin+"/"+nomProjet+"/project.fun");
            
            
            //charger le fichier .xml (.fun)
            
            //Avant de charger les blocs, on charge les variables et les composants,
            //car certains blocs dépendantes de ces derniers.
            
            //On ajoute les variables
            ArrayList<Variable> vars = acces.recupererVariables(this);
            for(int i=0; i<vars.size();i++)
            {
                ajouterVariable(vars.get(i));
            }
            
            //On ajoute les composants
            ArrayList<Composant> comps = acces.recupererComposants(this);
            for(int i=0; i<comps.size();i++)
            {
                ajouterComposant(comps.get(i));
            }
            
            //On récupère tout les blocs à ajouter au BlocStart
            HashMap<Integer,Bloc> blocs2 = acces.recupererFilsBlocsByLabel("BlocStart",this);
            for(Map.Entry<Integer,Bloc> unBloc : blocs2.entrySet()) 
            {
                this.ajouterAuSetupSansSauvegarde(unBloc.getValue());
                recupererFils(unBloc.getValue());
            } 
             
            //On récupère tout les blocs à ajouter au BlocUpdate
            HashMap<Integer,Bloc> blocs = acces.recupererFilsBlocsByLabel("BlocUpdate",this);
            for(Map.Entry<Integer,Bloc> unBloc : blocs.entrySet()) 
            {
                 this.ajouterAuLoopSansSauvegarde(unBloc.getValue());
                 recupererFils(unBloc.getValue());
            }   
            mettreAjourCode();
    }
    
    
    /**
     * Cette méthode permet de récupérer les fils d'un bloc en particulier
     * @param bloc le bloc parent duquel on souhaite pouvoir récupérer les blocs 
     * fils.
     */
    private void recupererFils(Bloc bloc)
    {
        HashMap<Integer,Bloc> blocs = acces.recupererFilsBlocsById(bloc.getId(),this);
            for(Map.Entry<Integer,Bloc> unBloc : blocs.entrySet()) 
            {
                bloc.ajouterBlocSansSauvegarder(unBloc.getKey(), unBloc.getValue());
                recupererFils(unBloc.getValue()); //On récupère les fils du fils si y'en a
                System.out.println(bloc.getClass().getSimpleName()+": "+bloc.getPosition());
            }
    }
        
    
    /**
     * Cette méthode permet de sauvegarder le projet.
     */
    public void sauvegarder()
    {
        acces.sauvegarde();
    }
    
        
    /**
     * Permet de copier un fichier
     * @param source est le fichier source que l'on veut copier
     * @param dest est le fichier de destination
     */
    private static void copyFile(File source, File dest)
    {
        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException ex) {
            System.err.println("Le système n'a pas pu copier le fichier: "+ex.getMessage());
        }
    }


  
    
        /*===================================================================
        ------------ RECUPERATION DES ATTRIBUTS DE LA CLASSE ----------------
        ===================================================================*/

    
    public AccesXML getAcces() {
        return acces;
    }
    

    public String getNomProjet() {
        return nomProjet;
    }

    public AssemblageBlocs getAssemblage() {
        return assemblage;
    }
    
    

    
    
        /*===================================================================
        ---------------- RECUPERATION DES ELEMENTS DU MODELE ----------------
        ===================================================================*/
    
    
    public Bloc getBlocById(int id)
    {
        Bloc bloc = null;
        
          for(Map.Entry<Integer,Bloc> blocs : this.blocInit.getSesFils().entrySet()) 
          {
              if(blocs.getValue().getId()==id){
                  bloc = blocs.getValue();
                  break;
              }
          }
        return bloc;
    }
    
    
    public Composant getComposantById(int id)
    {
        Composant comp = null;
        
          for(int i=0; i<composants.size();i++) 
          {
              if(composants.get(i).getId()==id)
                  comp = composants.get(i);
          }
          
        return comp;
    }
    
    
    public ArrayList<Variable> getVariables()
    {
        return variables;
    }
    
        public Variable getVariableById(int id)
    {
        Variable var = null;
        
          for(int i=0; i<variables.size();i++) 
          {
              if(variables.get(i).getId()==id)
                  var = variables.get(i);
          }
          
        return var;
    }

    public ArrayList<Composant> getComposants() {
        return composants;
    }
    
    
    
}