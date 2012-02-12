package com.kreig133.kachok.dao.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author C.C.-fag
 * @version 1.0
 */
@DatabaseTable
public class WeightType implements Serializable{

    private static final long serialVersionUID = - 2052173230122867207L;

    @DatabaseField( generatedId = true )
    private Integer id;

    @DatabaseField
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public WeightType() {
    }

    public WeightType( String name ) {
        this.name = name;
    }
}
