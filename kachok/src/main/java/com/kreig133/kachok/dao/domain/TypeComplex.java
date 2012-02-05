package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class TypeComplex implements Serializable{

    private static final long serialVersionUID = - 1437119930256837483L;

    @DatabaseField ( id = true )
    private Integer id;

    @DatabaseField( foreign = true )
    private Complex complex;

    @DatabaseField( foreign = true )
    private Type type;

    public Integer getId() {
        return id;
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex( Complex complex ) {
        this.complex = complex;
    }

    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }

    public TypeComplex() {
    }

    public TypeComplex( Complex complex, Type type ) {
        this.complex = complex;
        this.type = type;
    }
}
