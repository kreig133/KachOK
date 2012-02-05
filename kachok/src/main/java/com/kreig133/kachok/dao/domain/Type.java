package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.kreig133.kachok.dao.domain.Complex;

import java.io.Serializable;

/**
 * @author kreig133
 * @version 1.0
 */
@DatabaseTable
public class Type implements Serializable{

    private static final long serialVersionUID = 3281327869232194729L;
    
    @DatabaseField(generatedId = true)
    private Integer id;
    
    @DatabaseField
    private String name;

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
}
