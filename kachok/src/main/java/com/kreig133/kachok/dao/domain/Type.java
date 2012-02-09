package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author kreig133
 * @version 1.0
 */
@DatabaseTable
@SuppressWarnings( "UnusedDeclaration" )
public class Type implements Serializable{

    private static final long serialVersionUID = 3281327869232194729L;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Exercise> exercises;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Type() {
    }

    public Type( String name ) {
        this.name = name;
    }

    public ForeignCollection<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises( ForeignCollection<Exercise> exercises ) {
        this.exercises = exercises;
    }
}
