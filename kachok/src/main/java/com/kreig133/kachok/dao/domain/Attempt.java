package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class Attempt implements Serializable{

    private static final long serialVersionUID = 5750828428972780060L;

    @DatabaseField( generatedId = true )
    private Integer id;

    @DatabaseField
    private Integer numberOfRepeat;

    @DatabaseField
    private Integer weight;

    @DatabaseField
    private Date date;

    @DatabaseField( foreign = true, canBeNull = false )
    private Exercise exercise;

    @DatabaseField( foreign = true, canBeNull = false )
    private Sportsman sportsman;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getNumberOfRepeat() {
        return numberOfRepeat;
    }

    public void setNumberOfRepeat( Integer numberOfRepeat ) {
        this.numberOfRepeat = numberOfRepeat;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight( Integer weight ) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise( Exercise exercise ) {
        this.exercise = exercise;
    }

    public Sportsman getSportsman() {
        return sportsman;
    }

    public void setSportsman( Sportsman sportsman ) {
        this.sportsman = sportsman;
    }
}
