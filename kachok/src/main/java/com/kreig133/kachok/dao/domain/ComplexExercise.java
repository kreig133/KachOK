package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class ComplexExercise implements Serializable {

    private static final long serialVersionUID = 8781966978592262455L;

    @DatabaseField( generatedId = true )
    private Integer id;

    @DatabaseField( foreign = true, canBeNull = false )
    private Exercise exercise;

    @DatabaseField( foreign = true, canBeNull = false )
    private Complex complex;

    @DatabaseField
    private Integer order;

    @DatabaseField
    private Integer countOfRepeat;

    @DatabaseField
    private Integer countOfAttempts;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise( Exercise exercise ) {
        this.exercise = exercise;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex( Complex complex ) {
        this.complex = complex;
    }

    public Integer getCountOfRepeat() {
        return countOfRepeat;
    }

    public void setCountOfRepeat( Integer countOfRepeat ) {
        this.countOfRepeat = countOfRepeat;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder( Integer order ) {
        this.order = order;
    }

    public Integer getCountOfAttempts() {
        return countOfAttempts;
    }

    public void setCountOfAttempts( Integer countOfAttempts ) {
        this.countOfAttempts = countOfAttempts;
    }

    public ComplexExercise() {
    }

    public ComplexExercise( Exercise exercise, Complex complex, Integer order ) {
        this.exercise = exercise;
        this.complex = complex;
        this.order = order;
    }
}
