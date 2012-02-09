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

    private static final String DATABASE_NAME = "kachok4.db";

    private static final int DATABASE_VERSION = 1;
    
    private static final Class[] objects = new Class[]{ Attempt.class, Complex.class, ComplexExercise.class,
            Exercise.class, Sportsman.class, Type.class };

    public KachokDatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase database, ConnectionSource connectionSource ) {
        try {
            for( Class clazz : objects ){
                TableUtils.createTableIfNotExists( connectionSource, clazz );
            }

            createData();

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

    private Dao<Attempt, Integer> attemptDao;
    private Dao<Complex, Integer> complexDao;
    private Dao<ComplexExercise, Integer> complexExerciseDao;
    private Dao<Exercise,Integer> exerciseDao;
    private Dao<Sportsman,Integer> sportsmanDao;
    private Dao<Type,Integer> typeDao;


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

    public Dao<Attempt, Integer> getAttemptDao() throws SQLException{
        if ( attemptDao == null ) {
            attemptDao = getDao( Attempt.class );
        }

        return attemptDao;
    }

    public Dao<ComplexExercise, Integer> getComplexExerciseDao() throws SQLException{
        if ( complexExerciseDao == null ) {
            complexExerciseDao = getDao( ComplexExercise.class );
        }

        return complexExerciseDao;
    }


    private void createData() throws SQLException {
        final Type type1 = new Type( "Трицепс, епте" );
        getTypeDao().create( type1 );
        final Type type2 = new Type( "Бицуха, понл" );
        getTypeDao().create( type2 );
        final Type type3 = new Type( "Спина, красава" );
        getTypeDao().create( type3 );

        final Complex complex = new Complex( "День 1" );
        getComplexDao().create( complex );

        final Complex complex2 = new Complex( "День 2" );
        getComplexDao().create( complex2 );

        final Exercise exercise  = new Exercise( "Француский жим", type1 );
        final Exercise exercise1 = new Exercise( "С гантелями стоя", type2 );
        final Exercise exercise2 = new Exercise( "Гиперэкстензия", type3 );
        getExercizeDao().create( exercise );
        getExercizeDao().create( exercise1 );
        getExercizeDao().create( exercise2 );

        getComplexExerciseDao().create( new ComplexExercise( exercise, complex ) );
        getComplexExerciseDao().create( new ComplexExercise( exercise, complex2 ) );
        getComplexExerciseDao().create( new ComplexExercise( exercise1, complex ) );
        getComplexExerciseDao().create( new ComplexExercise( exercise1, complex2 ) );
        getComplexExerciseDao().create( new ComplexExercise( exercise2, complex ) );
        getComplexExerciseDao().create( new ComplexExercise( exercise2, complex2 ) );

    }
}
