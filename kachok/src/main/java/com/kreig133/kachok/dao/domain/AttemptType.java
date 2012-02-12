package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class AttemptType implements Serializable{

    private static final long serialVersionUID = 3346202352862610856L;

    @DatabaseField( generatedId = true )
    private Integer id;

    @DatabaseField
    private String name;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public AttemptType() {
    }

    public AttemptType( String name ) {
        this.name = name;
    }
}
