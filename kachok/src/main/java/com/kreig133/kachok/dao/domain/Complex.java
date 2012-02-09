package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
@SuppressWarnings( "UnusedDeclaration" )
public class Complex implements Serializable{



    private static final long serialVersionUID = - 5284700892006135192L;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    @ForeignCollectionField(eager = true)
    ForeignCollection<ComplexExercise> exercises;

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

    public ForeignCollection<ComplexExercise> getExercises() {
        return exercises;
    }

    public void setExercises( ForeignCollection<ComplexExercise> exercises ) {
        this.exercises = exercises;
    }

    public Complex() {
    }

    public Complex( String name ) {
        this.name = name;
    }
}
