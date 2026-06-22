/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.eclipse.jxmlsamp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author frede
 */
public class User {
    
    //private String nom;
    //private String prenom;
    //private String fonction;
    
    private StringProperty propnom;
    private StringProperty propprenom;
    private StringProperty propfonction;

    public User(String nom, String prenom, String fonction) {
        this.propnom = new SimpleStringProperty(nom);
        this.propprenom = new SimpleStringProperty(prenom);
        this.propfonction =  new SimpleStringProperty(fonction);
    }

    public String getNom() {
        return propnom.get();
    }

    public String getPrenom() {
        return propprenom.get();
    }

    public String getFonction() {
        return propfonction.get();
    }

    public void setNom(String nom) {
        this.propnom.set(nom);
    }

    public void setPrenom(String prenom) {
        this.propprenom.set(prenom);
    }

    public void setFonction(String fonction) {
        this.propfonction.set(fonction);
    }
}

