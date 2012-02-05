package com.kreig133.kachok.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kreig133.kachok.dao.domain.*;

import java.sql.SQLException;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class KachokDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "kachok1.db";

    private static final int DATABASE_VERSION = 1;
    
    private static final Class[] objects = new Class[]{ Clazz.class, Complex.class, 
            Exercise.class, Sportsman.class, Type.class };

    public KachokDatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase database, ConnectionSource connectionSource ) {
        try {
            for( Class clazz : objects ){
                TableUtils.createTable( connectionSource, clazz );
            }

            final Type type = new Type( "Упражнения на трицепс" );

            getTypeDao().create( type );

            final Complex complex = new Complex( "День 1" );

            getComplexDao().create( complex );

            final TypeComplex typeComplex = new TypeComplex( complex, type );

            getTypeComplexDao().create( typeComplex );

            final Complex complex2 = new Complex( "День 2" );

            getComplexDao().create( complex2 );

        } catch ( SQLException e ) {
            Log.e( KachokDatabaseHelper.class.getName(), "Unable to create datbases", e );
        }
    }

    @Override
    public void onUpgrade( SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion ) {
        try {
            for( Class clazz: objects ){
                TableUtils.dropTable( connectionSource, clazz, true );
            }
            onCreate( database, connectionSource );
        } catch ( SQLException e ) {
            Log.e( KachokDatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVersion +
                    " to new " + newVersion, e );
        }
    }

    private Dao<Clazz, Integer> clazzDao;
    private Dao<Complex, Integer> complexDao;
    private Dao<Exercise,Integer> exerciseDao;
    private Dao<Sportsman,Integer> sportsmanDao;
    private Dao<Type,Integer> typeDao;
    private Dao<TypeComplex, Integer> typeComplexDao;

    public Dao<Clazz, Integer> getClazzDao() throws SQLException {
        if ( clazzDao == null ) {
            clazzDao = getDao( Clazz.class );
        }
        return clazzDao;
    }

    public Dao<Complex, Integer> getComplexDao() throws SQLException {
        if ( complexDao == null ) {
            complexDao = getDao( Complex.class );
        }
        return complexDao;
    }

    public Dao<Exercise, Integer> getExercizeDao() throws SQLException{
        if ( exerciseDao == null ) {
            exerciseDao = getDao( Exercise.class );
        }
        return exerciseDao;
    }
    
    public Dao<Sportsman, Integer> getSportsmanDao() throws SQLException{
        if ( sportsmanDao == null ) {
            sportsmanDao = getDao( Sportsman.class );
        }
        return sportsmanDao;
    }
    
    public Dao<Type, Integer> getTypeDao() throws SQLException{
        if ( typeDao == null ) {
            typeDao = getDao( Type.class );
        }
        return typeDao;
    }

    public Dao<TypeComplex, Integer> getTypeComplexDao() throws SQLException{
        if ( typeComplexDao == null ) {
            typeComplexDao = getDao( TypeComplex.class );
        }

        return typeComplexDao;
    }
}
