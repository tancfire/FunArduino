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
        this.vue = vue;
        composants = new ArrayList<>();
        variables = new ArrayList<>();
        factory = DocumentBuilderFactory.newInstance();
        
        acces = new AccesXML();
        
        creerProjet(new File("saves").getAbsolutePath(), "GenerateByFunArduino");
       
        
        //On initialise tout à zéro
        remettreAZero();
        
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
        
       // blocUpdate.ajouterBlocALaFin(new BlocConditions((Object)varEtat,(Object)5, Comparateur.egal, this));
        
        conditions.ajouterBlocALaFin( new BlocAllumerPin(led, EtatPin.HAUT,this));
      //  conditions.ajouterBloc(1, new BlocChangerVariable(varEtat, "1"));
     //   conditions2.ajouterBloc(0, new BlocAllumerPin(led, EtatPin.BAS));
    //    conditions2.ajouterBloc(1, new BlocChangerVariable(varEtat, "0"));
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
     * Cette méthode permet de (re)mettre à zéro tout ce qui est nécessaire.
     */
    public void remettreAZero()
    {  
       if(this.simulateur!=null)
       vue.supprimerSimulateur(this.simulateur.getSimuGraph());
       
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
       this.simulateur = new SimulateurArduino(this);
       vue.ajouterSimulateur(this.simulateur.getSimuGraph());
       
        assemblage = new AssemblageBlocs(acces);
        blocLibrairies = new BlocLibrairies(this);
        blocStart = new BlocStart(this);
        blocUpdate = new BlocUpdate(this);
        blocInit = new BlocInit(this);
        
        assemblage.ajouterBloc(0, blocLibrairies);
        assemblage.ajouterBloc(1, blocInit);
        assemblage.ajouterBloc(2, blocStart);
        assemblage.ajouterBloc(3, blocUpdate);
        
        mettreAjourCode();
    }
      
    
    public void ouvrirMenuModifier(Bloc blocCaller, int x, int y)
    {
        vue.ouvrirMenuModifier(blocCaller,x,y);
    }
    
    
    public void ouvrirChoixBlocsAAjouter(Bloc blocCaller)
    {
        vue.ouvrirChoixBlocsAAjouter(blocCaller);
    }

    public void mettreAJourBranchements()
    {
        vue.mettreAJourBranchements(composants);
    }
    
    
    public void ajouterBloc(Bloc blocParent, Bloc blocFils)
    {
        blocParent.ajouterBlocALaFin(blocFils);
        mettreAjourCode();
    }
    
     
    public void ajouterVariable(Variable variable)
    {
        variables.add(variable);
        mettreAjourCode();
    }
        
    
    
    public void ajouterComposant(Composant composant)
    {
        composants.add(composant);
        if(composant.getCompGraph()!=null)
        vue.ajouterComposantGraphique(composant.getCompGraph());
        mettreAjourCode();
    }
    
    
    public void supprimerComposant(Composant composant)
    {
        composants.remove(composant);
        if(composant.getCompGraph()!=null)
        vue.supprimerComposantGraphique(composant.getCompGraph());
        mettreAjourCode();
    }
    
    
    
    
    public SimulateurArduino getSimulateur()
    {
        return simulateur;
    }
    
    
    public void ajouterAuSetup(Bloc unBloc)
    {
        blocStart.ajouterBlocALaFin(unBloc);
    }
    
    public void ajouterAuSetupSansSauvegarde(Bloc unBloc)
    {
        blocStart.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
    
        public void ajouterAuLoopSansSauvegarde(Bloc unBloc)
    {
        blocUpdate.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
    public void ajouterAuLoop(Bloc unBloc)
    {
        blocUpdate.ajouterBlocALaFin(unBloc);
    }
    
    
        public void ajouterAuInitSansSauvegarde(Bloc unBloc)
    {
        blocInit.ajouterBlocALaFinSansSauvegarde(unBloc);
    }
    
    
    public void ajouterAuInit(Bloc unBloc)
    {
        blocInit.ajouterBlocALaFin(unBloc);
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

    public void supprimerVariable(Variable var) {
        variables.remove(var);
    }
        
}