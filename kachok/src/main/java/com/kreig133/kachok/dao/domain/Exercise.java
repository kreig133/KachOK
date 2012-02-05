package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.kreig133.kachok.dao.domain.Type;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class Exercise implements Serializable{

    private static final long serialVersionUID = 8386076102152945837L;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    @DatabaseField( canBeNull = false,  foreign = true )
    private Type type;

    @DatabaseField
    private Integer countOfRepeats;

    @DatabaseField
    private String nameOfRepeats;

    @ForeignCollectionField(eager = true)
    ForeignCollection<ClassExercise> clazzs;

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

    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }

    public Integer getCountOfRepeats() {
        return countOfRepeats;
    }

    public void setCountOfRepeats( Integer countOfRepeats ) {
        this.countOfRepeats = countOfRepeats;
    }

    public String getNameOfRepeats() {
        return nameOfRepeats;
    }

    public void setNameOfRepeats( String nameOfRepeats ) {
        this.nameOfRepeats = nameOfRepeats;
    }

    public ForeignCollection<ClassExercise> getClazzs() {
        return clazzs;
    }

    public void setClazzs( ForeignCollection<ClassExercise> clazzs ) {
        this.clazzs = clazzs;
    }
}