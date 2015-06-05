/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modèle;

import Controleur.Controleur;

/**
 *
 * @author Utilisateur
 */
public class Variable {
    
    private TypeVariable type;
    private String nom;
    private String valeurDepart;
    private static int nbID=0;
    protected final int id;
    private Controleur ctrl;

    public Variable(TypeVariable typeParam, String nom, String valeurDepart, Controleur ctrl) {
        this.type = typeParam;
        this.nom = nom;
        this.valeurDepart = valeurDepart;
        this.ctrl = ctrl;
        
        id= nbID;
        nbID++;
        
        ctrl.ajouterAuInit(new BlocInitVariable(this, ctrl));
        ctrl.getAcces().creerVariable(id, typeParam.getType(), nom, valeurDepart);
    }
    
    
     public Variable(int id, TypeVariable type, String nom, String valeurDepart, Controleur ctrl) {
        this.type = type;
        this.nom = nom;
        this.valeurDepart = valeurDepart;
        this.ctrl = ctrl;
        
        this.id= id;
        if(id>nbID)
            nbID = id;
        ctrl.ajouterAuInitSansSauvegarde(ctrl.getAcces().recupererBlocInitVariable(this, ctrl));

    }
     

    public TypeVariable getTypeVariable() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public String getValeurDepart() {
        return valeurDepart;
    }

    public int getId() {
        return id;
    }

    void delete() {
        ctrl.supprimerVariable(this);
        ctrl.getAcces().supprimerVariable(id);
    }
      
}
