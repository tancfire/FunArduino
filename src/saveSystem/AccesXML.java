/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saveSystem;

import Controleur.Controleur;
import Modèle.Bloc;
import Modèle.BlocAllumerPin;
import Modèle.BlocAttendre;
import Modèle.BlocConditions;
import Modèle.BlocInitVariable;
import Modèle.Comparateur;
import Modèle.Composant;
import Modèle.ComposantLed;
import Modèle.EtatPin;
import Modèle.TypeVariable;
import Modèle.Variable;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *  AccesXML permet de gérer le fichier xml de  sauvegarde (ici, c'est le fichier
 * project.fun).
 * 
 * Note: Améliorations et optimisations prévues.
 * @author tancfire
 */
public class AccesXML {
      private Document saSave;
      private String chemin;
      private DocumentBuilderFactory factory;
     

      public AccesXML(){
        this.factory = DocumentBuilderFactory.newInstance(); 
    }
      
      public void setChemin(String cheminDuFichier)
      {
        try{
        DocumentBuilder leDoc = factory.newDocumentBuilder();
        this.chemin = cheminDuFichier;
        saSave = leDoc.parse(chemin);
        }catch(Exception e)
        {
            System.err.println("erreur d'accès XML: "+e.getMessage());
        } 
      }
      
           
      /*========================================================================
      --------------- CREATION DES ELEMENTS DANS LE FICHIER --------------------
      ========================================================================*/
      
      
      public void creerBloc(int id, String label)
     { 
         System.out.println("id:"+id+", label: "+label);
           if(!blocExiste(id)){ //si le bloc n'existe pas
         Element e = saSave.createElement("bloc");
         e.setAttribute("id", Integer.toString(id));
         e.setAttribute("label", label);
         saSave.getDocumentElement().appendChild(e);
         }
     }
     
     
     public void creerComposant(int id, String label)
     { 
           if(!composantExiste(id)){ //si le composant n'existe pas
         Element e = saSave.createElement("composant");
         e.setAttribute("id", Integer.toString(id));
         e.setAttribute("label", label);
         
         saSave.getDocumentElement().appendChild(e);
         }
     }
     
     
     public void creerVariable(int id, String type, String nom, String valeur)
     { 
           if(!variableExiste(id)){ //si la variable n'existe pas
         Element e = saSave.createElement("variable");
         e.setAttribute("id", Integer.toString(id));
         e.setAttribute("type", type);
         e.setAttribute("nom", nom);
         e.setAttribute("valeur", valeur);
         
         saSave.getDocumentElement().appendChild(e);
         }
     }
     
     
          
     public void setParametre(int idBloc, String type, String nom, String valeur)
     {
         if(blocExiste(idBloc)) //Si le bloc parent existe
         {
         Element bloc = getBlocById(idBloc); //on récupère le bloc dans le fichier
         
             if(!parametreExiste(idBloc, nom)){ //si le paramètre n'existe pas dans le fichier
              //Créer l'élément parametre à ajouter
              Element e = saSave.createElement("parametre");
              e.setAttribute("type", type);
              e.setAttribute("nom", nom);
              e.setAttribute("valeur", valeur);
              
              //on ajoute le parametre au bloc du père.
              bloc.appendChild(e);
             }else{
              //si le paramètre existe déjà dans le fichier, on ne fait que le modifier
              Element param = getParamByNom(idBloc, nom);
              param.setAttribute("type", type);
              param.setAttribute("nom", nom);
              param.setAttribute("valeur", valeur);
              bloc.appendChild(param);
             }
         }else{
             System.err.println("XML: Erreur lors de l'ajout du parametre "+nom+": le bloc possédant l'id "+idBloc+" n'existe pas.");
         }
     }
     
     

     public void setSlot(int idComposant, String type, String couleur, String pinConnectee)
     {
         if(composantExiste(idComposant)) //si le composant existe dans le fichier
         {
         Element composant = getComposantById(idComposant); //On récupère le composant dans le fichier
         
         if(!slotExiste(idComposant, couleur)){ //si le slot que l'on veut modifier n'existe pas
            //Créer l'élément slot à ajouter
            Element e = saSave.createElement("slot");
            e.setAttribute("type", type);
            e.setAttribute("couleur", couleur);
            e.setAttribute("pin", pinConnectee);
         
            //on ajoute le slot au composant
            composant.appendChild(e);
         }else{
            //si le composant n'existe pas dans le fichier:
            Element slot = getSlotByNom(idComposant, couleur);
            slot.setAttribute("type", type);
            slot.setAttribute("couleur", couleur);
            slot.setAttribute("pin", pinConnectee);
            composant.appendChild(slot);
         }
         }else{
             System.err.println("Erreur lors de l'ajout du slot ");
         }
     }
     
     
     /*========================================================================
     ---------DIVERSES MODIFICATIONS DES ELEMENTS DANS LE FICHIER -------------
     ========================================================================*/
     
     
     public void setPositionToBloc(int id, int position)
     {
         if(blocExiste(id)){ //si le bloc existe
             Element bloc = getBlocById(id); //On récupère le bloc
             bloc.setAttribute("position", Integer.toString(position)); //On lui donne sa position
         }else{
             System.err.println("xml: impossible de définir une position pour le bloc "+id+", car il est inexistant.");
         }
     }
     
     
     
     public void ajouterBlocDansBloc(int idPere, int id)
     {
         if(blocExiste(id) &&blocExiste(idPere)) //si le bloc fils et son père existent
         {
          //on récupère les deux blocs:
          Element pere = getBlocById(idPere);
          Element fils = getBlocById(id);
          
          pere.appendChild(fils); //On ajoute le fils au père
             
         }else{
             System.err.println("xml: Les blocs "+id+" et/ou "+idPere+" n'existent pas.");
         }
     }
     
     
     /* ========================================================================
     --------------- RECUPERER LES ELEMENTS DANS LE FICHIER --------------------
     ======================================================================== */
     
     private Element getParamByNom(int idBloc, String nom)
     {
         NodeList lesNoeuds = getBlocById(idBloc).getElementsByTagName("parametre"); //On récupère la liste de tout les paramètres du bloc sélectionné (via l'id)
         Element param = null;
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++) //On parcours tout ces paramètres
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(e3.getAttribute("nom").equals(nom))
                param = e3;
            }
        }
         return param;
     }
     
     
     private Element getBlocById(int id)
     {
         NodeList lesNoeuds = saSave.getElementsByTagName("bloc"); //On récupère la liste de tout les blocs du fichier
          Element e2 = null;
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++) //On parcourt la liste des blocs
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(Integer.parseInt(e3.getAttribute("id"))==id)//Si l'id correspond
                    e2 = e3; //On récupère pour le retourner
            }
        }
         return e2;
     }
     
     
          
     private Element getComposantById(int id)
     {
         Element composant = null;
         NodeList lesNoeuds = saSave.getElementsByTagName("composant"); //On récupère tout les composants du fichier
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++) //On parcourt tout les composants
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(Integer.parseInt(e3.getAttribute("id"))==id)//Si l'id correspond
                composant = e3; //On récupère l'élément pour le retourner
            }
        }
         return composant;
     }
     
     
                  
    private Element getSlotByNom(int idComposant, String couleur) {
         NodeList lesNoeuds = getComposantById(idComposant).getElementsByTagName("slot");
         Element slot = null;
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(e3.getAttribute("couleur").equals(couleur))
                slot = e3;
            }
        }
         return slot;
    }
     
     
    /* ========================================================================
     ----------- TESTER L'EXISTANCE DES ELEMENTS DANS LE FICHIER --------------
     ======================================================================== */
     
     // /!\ Algorithme à changer
     private boolean parametreExiste(int idBloc, String nom)
     {
         boolean val = false;
         NodeList lesNoeuds = getBlocById(idBloc).getElementsByTagName("parametre");
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(e3.getAttribute("nom").equals(nom))
                val = true;
            }
        }
         return val;
     }
 

    // /!\ Algorithme à changer 
     private boolean composantExiste(int id)
     {
         boolean val = false;
         NodeList lesNoeuds = saSave.getElementsByTagName("composant");
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(Integer.parseInt(e3.getAttribute("id"))==id)
                val = true;
            }
        }
         return val;
     }
     
     
     // /!\ Algorithme à changer
     private boolean slotExiste(int id, String couleur)
     {
         boolean val = false;
         NodeList lesNoeuds = getComposantById(id).getElementsByTagName("slot");
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(e3.getAttribute("couleur").equals(couleur))
                val = true;
            }
        }
         return val;
     }
     
     
     // /!\ Algorithme à changer
      private boolean variableExiste(int id)
     {
         boolean val = false;
         NodeList lesNoeuds = saSave.getElementsByTagName("variable");
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(Integer.parseInt(e3.getAttribute("id"))==id)
                val = true;
            }
        }
         return val;
     }
     
     
      // /!\ Algorithme à changer
     private boolean blocExiste(int id)
     {
         boolean val = false;
         NodeList lesNoeuds = saSave.getElementsByTagName("bloc");
         
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                Element e3 = (Element)lesNoeuds.item(i);
                if(Integer.parseInt(e3.getAttribute("id"))==id)
                val = true;
            }
        }
         return val;
     }

     
     /*========================================================================
     -------- RECUPERER LES ELEMENTS DU MODELE A PARTIR DU FICHIER ------------
     ========================================================================*/
        
     public BlocInitVariable recupererBlocInitVariable(Variable var, Controleur ctrl)
     {
           NodeList lesNoeuds = saSave.getElementsByTagName("parametre");
           int id=-1;

         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                if(lesNoeuds.item(i) instanceof Element){
                Element e3 = (Element)lesNoeuds.item(i);
                    if(e3.getAttribute("nom").equals("idVariable") && e3.getAttribute("valeur").equals(String.valueOf(var.getId())))
                    {
                        if(e3.getParentNode() instanceof Element)
                        {
                             Element e = (Element)e3.getParentNode();
                             id = Integer.parseInt(e.getAttribute("id"));
                        }
                    }
            }}
        }
         BlocInitVariable bloc = new BlocInitVariable(id, var, ctrl);
         
         return bloc;
     }
        
        
     
     public ArrayList<Variable> recupererVariables(Controleur ctrl)
     {
         ArrayList<Variable> desVariables = new ArrayList<Variable>();
         
         
         NodeList lesNoeuds = saSave.getDocumentElement().getChildNodes();

         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                if(lesNoeuds.item(i) instanceof Element){
                Element e3 = (Element)lesNoeuds.item(i);
                 if(e3.getTagName().equals("variable")) 
                desVariables.add(convertirVariable(e3,ctrl));
            }}
        }
         return desVariables;
     }
         
        
     
     public ArrayList<Composant> recupererComposants(Controleur ctrl)
     {
         ArrayList<Composant> desComposants = new ArrayList<Composant>();
         
         
         NodeList lesNoeuds = saSave.getDocumentElement().getChildNodes();

         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                if(lesNoeuds.item(i) instanceof Element){
                Element e3 = (Element)lesNoeuds.item(i);
                    if(e3.getTagName().equals("composant"))
                    desComposants.add(convertirComposant(e3,ctrl));
            }}
        }
         return desComposants;
     }
     
     
     public HashMap<Integer,Bloc> recupererFilsBlocsById(int id, Controleur ctrl)
     {
         return  recupererFilsBlocs("id", Integer.toString(id), ctrl);
     }
     
     
     public HashMap<Integer,Bloc> recupererFilsBlocsByLabel(String label, Controleur ctrl)
     {
         return  recupererFilsBlocs("label", label, ctrl);
     }
        
           
     
     private HashMap<Integer,Bloc> recupererFilsBlocs(String attribut, String valeur, Controleur ctrl)
     {
         HashMap<Integer,Bloc> desBlocs = new HashMap<Integer,Bloc>();
         
         NodeList lesNoeuds = saSave.getElementsByTagName("bloc"); //On récupère tout les éléments blocs
         if(lesNoeuds != null){
            for(int i=0; i<lesNoeuds.getLength();i++)
            {
                if(lesNoeuds.item(i) instanceof Element){
                Element e3 = (Element)lesNoeuds.item(i);
                
                        if(e3.getAttribute(attribut).equals(valeur)){ //On vérifie que l'élément correspond aux critères recherchés
                            NodeList lesNoeuds2 = e3.getChildNodes(); //On sélectionne tout les fils du bloc sélectionné
                            
                            for(int i2=0; i2<lesNoeuds2.getLength();i2++)
                            {
                               if(lesNoeuds2.item(i2) instanceof Element){
                                Element e = (Element)lesNoeuds2.item(i2);
                                if(e.getTagName().equals("bloc")){ //On vérifie que l'élément récupéré un bloc
                                Bloc bloc = convertirBloc(e, ctrl); //On récupère le bloc fils
                                        if(bloc!=null){ //si il n'est pas nul (il est reconnu)
                                            System.out.println("charge du bloc:"+Integer.parseInt(e.getAttribute("position"))+": "+bloc.getClass().getSimpleName());
                                            desBlocs.put(Integer.parseInt(e.getAttribute("position")),bloc); // on l'ajoute
                                        }
                               }
                               }
                            }
                        }
                }
            }
        }
         return desBlocs;
     }
     
    
     // /!\ OPTIMISATIONS et CORRECTIONS A EFFECTUER
        private Bloc convertirBloc(Element e, Controleur ctrl)
        {
           int id = Integer.parseInt(e.getAttribute("id"));
           
           Bloc bloc = null;
           
           if(e.getAttribute("label").equals("BlocAttendre")){
                Element param = getParamByNom(id, "delai");
                bloc = new BlocAttendre(id,Integer.parseInt(param.getAttribute("valeur")), ctrl);
           }else if (e.getAttribute("label").equals("BlocAllumerPin")){
                 EtatPin etat = EtatPin.BAS;
                 Element param = getParamByNom(id, "etatPin");
                 Element param2 = getParamByNom(id, "idComposant");
                 int id2 = Integer.valueOf(param2.getAttribute("valeur"));
                    if(param.getAttribute("valeur").equals("HAUT"))
                    {
                        etat = EtatPin.HAUT;
                    }
                 bloc = new BlocAllumerPin(id, ctrl.getComposantById(id2),  etat, ctrl); //Problème: si le composant est supprimé
                 
           }else if (e.getAttribute("label").equals("BlocConditions")){
                 Element param1 = getParamByNom(id, "param1");
                 Element param2 = getParamByNom(id, "param2");
                 Object o1 = null;
                 Object o2 = null;
                 
                 if(param1.getAttribute("type").equals("int"))
                 {
                     o1 = Integer.parseInt(param1.getAttribute("valeur"));
                 }else if (param1.getAttribute("type").equals("variable"))
                 {
                     o1 = ctrl.getVariableById(Integer.parseInt(param1.getAttribute("valeur")));
                 }else if(param1.getAttribute("type").equals("composant"))
                 {
                     o1 = ctrl.getComposantById(Integer.parseInt(param1.getAttribute("valeur")));
                 }
                 
                 if(param2.getAttribute("type").equals("int"))
                 {
                     o2 = Integer.parseInt(param2.getAttribute("valeur"));
                 }else if (param2.getAttribute("type").equals("variable"))
                 {
                     o2 = ctrl.getVariableById(Integer.parseInt(param2.getAttribute("valeur")));
                 }else if(param2.getAttribute("type").equals("composant"))
                 {
                     o2 = ctrl.getComposantById(Integer.parseInt(param2.getAttribute("valeur")));
                 }
                 
                 bloc = new BlocConditions(id, o1, o2, Comparateur.superieur, ctrl);
           }
           
           
            return bloc;
        }
        
        
        
        private Composant convertirComposant(Element e, Controleur ctrl)
        {
           int id = Integer.parseInt(e.getAttribute("id"));
           Composant composant = null;
           
           if(e.getAttribute("label").equals("led"))
           {
           composant = new ComposantLed(id, ctrl);
           }
            
            return composant;
        }
        
        
        
        private Variable convertirVariable(Element e, Controleur ctrl)
        {
           int id = Integer.parseInt(e.getAttribute("id"));
           Variable var = null;
           TypeVariable typeVar = TypeVariable.texte;
           
           if(e.getAttribute("type").equals("int"))
           {
               typeVar = TypeVariable.nombreEntier;
           }
           
           var = new Variable(id, typeVar, e.getAttribute("nom"), e.getAttribute("valeur"), ctrl);
           
            
            return var;
        }
      
    
      /**
       * Permet d'écrire dans le fichier toutes les modifications effectuées
       */
      public void sauvegarde()
    {
        try{
         // on sauvegarde
         OutputFormat leFormat = new OutputFormat(saSave);
         XMLSerializer leGenerateurXML = new XMLSerializer(new FileOutputStream(new File(chemin)), leFormat);
         leGenerateurXML.serialize(saSave);
 
         System.out.println("sauvegarde réussite !");
        }catch(Exception e)
        {
            System.err.println("Problème lors de la sauvegarde: "+e.getMessage());
        }
    }
    
    
}
