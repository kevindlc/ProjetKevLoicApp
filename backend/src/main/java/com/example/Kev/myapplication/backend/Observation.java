package com.example.Kev.myapplication.backend;

import com.googlecode.objectify.annotation.Id;

/**
 * Created by Kev on 26.04.2017.
 */

public class Observation {


    @Id
    private int id;
    private int orni;
    private int oiseau;
    private String text;
    private String orniN;
    private String oiseauN;

    public String getOrniN() {
        return orniN;
    }

    public void setOrniN(String orniN) {
        this.orniN = orniN;
    }

    public String getOiseauN() {
        return oiseauN;
    }

    public void setOiseauN(String oiseauN) {
        this.oiseauN = oiseauN;
    }

    public Observation(int orni, int oiseau, String text) {
        this.orni = orni;
        this.oiseau = oiseau;
        this.text = text;
    }

    public Observation() {}

    public Observation(int id, int orni, int oiseau, String text) {
        this.id = id;
        this.orni = orni;
        this.oiseau = oiseau;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrni() {
        return orni;
    }

    public void setOrni(int orni) {
        this.orni = orni;
    }

    public int getOiseau() {
        return oiseau;
    }

    public void setOiseau(int oiseau) {
        this.oiseau = oiseau;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}