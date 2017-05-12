package com.example.Kev.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Kev on 26.04.2017.
 */

@Entity
public class Observation {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orni;
    private long oiseau;
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

    public Observation(long orni, long oiseau, String text) {
        this.orni = orni;
        this.oiseau = oiseau;
        this.text = text;
    }

    public Observation() {}

    public Observation(long id, long orni, long oiseau, String text) {
        this.id = id;
        this.orni = orni;
        this.oiseau = oiseau;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrni() {
        return orni;
    }

    public void setOrni(long orni) {
        this.orni = orni;
    }

    public long getOiseau() {
        return oiseau;
    }

    public void setOiseau(long oiseau) {
        this.oiseau = oiseau;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}