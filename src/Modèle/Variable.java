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
    
    private TypeVariable typeParam;
    private String nom;
    private String valeurDepart;
    private static int nbID=0;
    protected final int id;

    public Variable(TypeVariable typeParam, String nom, String valeurDepart, Controleur ctrl) {
        this.typeParam = typeParam;
        this.nom = nom;
        this.valeurDepart = valeurDepart;
        
        id= nbID;
        nbID++;
        
        ctrl.ajouterAuInit(new BlocInitVariable(this, ctrl.getAcces()));
        ctrl.getAcces().creerVariable(id, typeParam.getType(), nom, valeurDepart);
    }
    
    
     public Variable(int id, TypeVariable typeParam, String nom, String valeurDepart, Controleur ctrl) {
        this.typeParam = typeParam;
        this.nom = nom;
        this.valeurDepart = valeurDepart;
        
        this.id= id;
        ctrl.ajouterAuInitSansSauvegarde(ctrl.getAcces().recupererBlocInitVariable(this, ctrl));

    }
     
     
     
    

    public TypeVariable getTypeParam() {
        return typeParam;
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
    
    
    
}
