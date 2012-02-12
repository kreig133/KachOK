package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
@SuppressWarnings( "UnusedDeclaration" )
public class Sportsman implements Serializable{

    private static final long serialVersionUID = 33359619416598472L;

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

    public Sportsman() {
    }

    public Sportsman( String name ) {
        this.name = name;
    }
}
