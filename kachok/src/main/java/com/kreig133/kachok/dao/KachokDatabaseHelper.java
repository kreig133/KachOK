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
import java.util.ArrayList;
import java.util.List;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class KachokDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "kachok5.db";

    private static final int DATABASE_VERSION = 1;
    
    private static final Class[] objects = new Class[]{
            Attempt.class,
            AttemptType.class,
            Complex.class,
            ComplexExercise.class,
            Exercise.class,
            Sportsman.class,
            Type.class,
            WeightType.class
    };

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
    private Dao<AttemptType, Integer> attemptTypesDao;
    private Dao<Complex, Integer> complexDao;
    private Dao<ComplexExercise, Integer> complexExerciseDao;
    private Dao<Exercise,Integer> exerciseDao;
    private Dao<Sportsman,Integer> sportsmanDao;
    private Dao<Type,Integer> typeDao;
    private Dao<WeightType, Integer> weightTypesDao;


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

    public Dao<AttemptType, Integer> getAttemptTypeDao() throws SQLException {
        if ( attemptTypesDao == null ) {
            attemptTypesDao = getDao( AttemptType.class );
        }
        return attemptTypesDao;
    }

    public Dao<WeightType, Integer> getWeightTypeDao() throws SQLException {
        if ( weightTypesDao == null ) {
            weightTypesDao = getDao( WeightType.class );
        }

        return weightTypesDao;
    }

    private void createData() throws SQLException {
        final Type press = new Type( "Пресс" );
        getTypeDao().create( press );
        final Type shoulders = new Type( "Плечи" );
        getTypeDao().create( shoulders );
        final Type trisepts = new Type( "Трицепс" );
        getTypeDao().create( trisepts );
        final Type foots = new Type( "Ноги" );
        getTypeDao().create( foots );
        final Type breasts = new Type( "Грудь" );
        getTypeDao().create( breasts );
        final Type back = new Type( "Спина" );
        getTypeDao().create( back );
        final Type bicepts = new Type( "Бицепс" );
        getTypeDao().create( bicepts );

        final Complex complex = new Complex( "День 1" );
        getComplexDao().create( complex );

        final Complex complex2 = new Complex( "День 2" );
        getComplexDao().create( complex2 );

        final AttemptType counts = new AttemptType( "раз" );
        getAttemptTypeDao().create( counts );

        final WeightType kg = new WeightType( "кг" );
        getWeightTypeDao().create( kg );

        List<Exercise> day2 = new ArrayList<Exercise>( 11 );
        day2.add( new Exercise( "Подьем ног к груди", press, counts, kg, - 1 ) );
        day2.add( new Exercise( "Разводка на тренажере на заднюю дельту", shoulders, counts, kg, 12 ) );
        day2.add( new Exercise( "Жим штанги за голову стоя", shoulders, counts, kg, 10 ) );
        day2.add( new Exercise( "Шраги с гантелями на трапецию", shoulders, counts, kg, 10 ) );
        day2.add( new Exercise( "Француский жим штангой лежа", trisepts, counts, kg, 10 ) );
        day2.add( new Exercise( "Француский жим гантелей стоя", trisepts, counts, kg, 10 ) );
        day2.add( new Exercise( "Разгибание рук на блоке", trisepts, counts, kg, 12 ) );
        day2.add( new Exercise( "Подъем на носки стоя", foots, counts, kg, 15 ) );
        day2.add( new Exercise( "Жим ногами лежа", foots, counts, kg, 10 ) );
        day2.add( new Exercise( "Разгибание ног на тренажере сидя", foots, counts, kg, 12 ) );
        day2.add( new Exercise( "Сгибание ног на тренажере сидя", foots, counts, kg, 12 ) );
//        final Exercise exercise2 = new Exercise( "Гиперэкстензия", trisepts );
        
        List<Exercise> day1 = new ArrayList<Exercise>( 11 );
        day2.add( new Exercise( "Подъем туловища", press, counts, kg, - 1 ) );
        day2.add( new Exercise( "Жим штанги лежа", breasts, counts, kg, 10 ) );
        day2.add( new Exercise( "Жим гантелей на скамье под углом 30", breasts, counts, kg, 10 ) );
        day2.add( new Exercise( "Отжимание на брусьях", breasts, counts, kg, -1 ) );
        day2.add( new Exercise( "Подтягивание к груди широким хватом", back, counts, kg, -1 ) );
        day2.add( new Exercise( "Тяга штанги в наклоне к груди", back, counts, kg, 10 ) );
        day2.add( new Exercise( "Тяга на блоке к животу сидя", back, counts, kg, 10 ) );
        day2.add( new Exercise( "Гиперэкстензия", back, counts, kg, 12 ) );
        day2.add( new Exercise( "Подьъем штанги стоя ( гриф прямой )", bicepts, counts, kg, 10 ) );
        day2.add( new Exercise( "Подъем гантелей", bicepts, counts, kg, 10 ) );
        day2.add( new Exercise( "Подъем гантелей ( с упором )", bicepts, counts, kg, 10 ) );

        for ( Exercise exercise : day1 ) {
            getExercizeDao().create( exercise );
            getComplexExerciseDao().create( new ComplexExercise( exercise, complex ) );
        }

        for ( Exercise exercise : day2 ) {
            getExercizeDao().create( exercise );
            getComplexExerciseDao().create( new ComplexExercise( exercise, complex2 ) );
        }
   }
}