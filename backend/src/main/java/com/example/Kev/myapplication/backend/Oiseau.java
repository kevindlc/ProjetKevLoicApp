package com.example.Kev.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * Created by Kev on 18.04.2017.
 */

@Entity

public class Oiseau {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String color;
    private String poids;
    private String taille;
    private String text;


    public Oiseau(long id, String nom, String color, String poids, String taille, String text) {
        this.id = id;
        this.nom = nom;
        this.color = color;
        this.poids = poids;
        this.taille = taille;
        this.text = text;
    }

    public Oiseau(String nom, String color, String poids, String taille, String text) {
        this.nom = nom;
        this.color = color;
        this.poids = poids;
        this.taille = taille;
        this.text = text;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public Oiseau() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPoids() {
        return poids;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
