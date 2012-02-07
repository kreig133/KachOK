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
@SuppressWarnings( "UnusedDeclaration" )
public class Clazz implements Serializable{

    private static final long serialVersionUID = - 2829671578413052604L;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private Date date;

    @DatabaseField( canBeNull = false,  foreign = true )
    private Sportsman sportsman;

    @DatabaseField( canBeNull = false,  foreign = true )
    private Complex complex;


    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public Sportsman getSportsman() {
        return sportsman;
    }

    public void setSportsman( Sportsman sportsman ) {
        this.sportsman = sportsman;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex( Complex complex ) {
        this.complex = complex;
    }
}
