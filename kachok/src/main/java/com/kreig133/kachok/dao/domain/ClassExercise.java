package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class ClassExercise implements Serializable {

    private static final long serialVersionUID = 6665222270118315478L;

    @DatabaseField
    private Integer id;

    @DatabaseField( foreign = true, canBeNull = false )
    private Clazz clazz;

    @DatabaseField( foreign = true, canBeNull = false )
    private Exercise exercise;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz( Clazz clazz ) {
        this.clazz = clazz;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise( Exercise exercise ) {
        this.exercise = exercise;
    }
}
