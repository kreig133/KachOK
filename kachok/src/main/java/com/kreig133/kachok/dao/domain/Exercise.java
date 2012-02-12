package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
@SuppressWarnings( "UnusedDeclaration" )
public class Exercise implements Serializable{

    private static final long serialVersionUID = 8386076102152945837L;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField( canBeNull = false )
    private String name;

    @DatabaseField( canBeNull = false,  foreign = true )
    private Type type;

    @DatabaseField( foreign = true, canBeNull = false )
    private AttemptType attemptType;

    @DatabaseField( foreign = true, canBeNull = false )
    private WeightType weightType;

    @DatabaseField
    private Integer countOfRepeats;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }

    public AttemptType getAttemptType() {
        return attemptType;
    }

    public void setAttemptType( AttemptType attemptType ) {
        this.attemptType = attemptType;
    }

    public WeightType getWeightType() {
        return weightType;
    }

    public void setWeightType( WeightType weightType ) {
        this.weightType = weightType;
    }

    public Integer getCountOfRepeats() {
        return countOfRepeats;
    }

    public void setCountOfRepeats( Integer countOfRepeats ) {
        this.countOfRepeats = countOfRepeats;
    }

    public Exercise() {
    }

    public Exercise( String name, Type type, AttemptType attemptType, WeightType weightType, Integer countOfRepeats ) {
        this.name = name;
        this.type = type;
        this.attemptType = attemptType;
        this.weightType = weightType;
        this.countOfRepeats = countOfRepeats;
    }
}
