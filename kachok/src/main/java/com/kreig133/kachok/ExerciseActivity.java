package com.kreig133.kachok;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.kreig133.kachok.dao.KachokDatabaseHelper;
import com.kreig133.kachok.dao.domain.Attempt;
import com.kreig133.kachok.dao.domain.Exercise;
import com.kreig133.kachok.dao.domain.Sportsman;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class ExerciseActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private static final String EXERCISE_ID = "exercise";
    private static final String HEADER = "header";
    private static final String TIME = "time";
    private static final String COUNT_OF_TRYING = "countOfTrying";
    private static final String WEIGHT = "weight";
    private Exercise exercise;
    private Button addButton;
    private EditText countOfTrying;
    private EditText weightOfTrying;
    private ExpandableListView tryingList;
    private DateFormat dateFormat = new SimpleDateFormat( "dd MMMM yyyy" );
    private DateFormat timeFormat = new SimpleDateFormat( "hh:mm" );

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.attempt_list );

        addButton = ( Button ) findViewById( R.id.exerciseAddTrying );
        countOfTrying = ( EditText ) findViewById( R.id.countOfTrying );
        weightOfTrying = ( EditText ) findViewById( R.id.weightOfTrying );
        tryingList = ( ExpandableListView ) findViewById( R.id.exerciseTryingList );

        addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                addNewTrying();
            }
        } );

        getCurrentExercise();

        ( ( TextView ) findViewById( R.id.exerciseName ) ).setText( exercise.getName() );
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            fillList();
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    private void addNewTrying() {
        try{
            if( checkIsNumberAndGreaterThenZero( countOfTrying ) && checkIsNumber( weightOfTrying ) ){
                final Attempt attempt = new Attempt();
                attempt.setDate( new Date() );
                attempt.setExercise( this.exercise );
                attempt.setNumberOfRepeat( new Integer( countOfTrying.getText().toString() ) );
                attempt.setSportsman( getSportsman() );
                attempt.setWeight( new Double( weightOfTrying.getText().toString() ) );

                getHelper().getAttemptDao().create( attempt );
                fillList();
                tryingList.expandGroup( 0 );
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    private Sportsman getSportsman() {
        //TODO поменять на нормальную реализацию
        Sportsman result;

        try{
            final List<Sportsman> query =
                getHelper().getSportsmanDao().query( getHelper().getSportsmanDao().queryBuilder().prepare() );
            result = query.isEmpty() ? null : query.get( 0 );

            if( result == null ){
                result = new Sportsman( "Я" );
                getHelper().getSportsmanDao().create( result );
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }

        return result;
    }

    private void fillList() throws SQLException {
        List<Attempt> attemptList = getAttemptList();

        List<String> dates = new ArrayList<String>();
        List<List<Attempt>> listOfListOfAttempt = new ArrayList<List<Attempt>>();
        
        
        for ( int i = 0; i < attemptList.size();  ) {
            String date = dateFormat.format( attemptList.get( i ).getDate() );
            
            dates.add( date );
            
            List<Attempt> listOfAttempts = new ArrayList<Attempt>();
            
            while ( i < attemptList.size() && dateFormat.format( attemptList.get( i ).getDate() ).equals( date ) ) {
                listOfAttempts.add( attemptList.get( i ) );
                i++;
            }
            
            listOfListOfAttempt.add( listOfAttempts );
        }

        tryingList.setAdapter( new SimpleExpandableListAdapter(
                ExerciseActivity.this,
                getGroupsData( dates ),
                R.layout.type_header,
                new String[] { HEADER },
                new int[] { R.id.typeHeader },
                getListDate( listOfListOfAttempt ),
                R.layout.attempt,
                new String[]{ TIME, COUNT_OF_TRYING, WEIGHT },
                new int[]{ R.id.attemptTime, R.id.attemptCountOfRepeat, R.id.attemptWeight}
        ) );

    }

    private List<List<Map<String, String>>> getListDate( List<List<Attempt>> listOfListOfAttempt ) {
        List<List<Map<String, String>>> result = new ArrayList<List<Map<String, String>>>();

        for ( List<Attempt> list : listOfListOfAttempt ) {
            List<Map<String, String>> listOfMap = new ArrayList<Map<String, String>>();
            for ( Attempt attempt : list ) {
                Map<String, String> map = new HashMap<String, String>();
                map.put( TIME, timeFormat.format( attempt.getDate() ) );
                map.put( COUNT_OF_TRYING, attempt.getNumberOfRepeat().toString() );
                map.put( WEIGHT, attempt.getWeight().toString() );
                listOfMap.add( map );
            }
            result.add( listOfMap );
        }
        return result;
    }

    private List<Map<String, String>> getGroupsData( List<String> dates ) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for ( String date : dates ) {
            Map<String, String> map = new HashMap<String, String>( 1 );
            map.put( HEADER, date );
            result.add( map );
        }
        return result;
    }

    private List<Attempt> getAttemptList() throws SQLException {
        return getHelper().getAttemptDao().query( getHelper().getAttemptDao().queryBuilder().orderBy( "date", false )
                .limit( 10L ).where().eq( "exercise_id", exercise.getId() ).prepare() );
    }

    private boolean checkIsNumber( EditText weightOfTrying ) {
        if( "".equals( countOfTrying.getText().toString() ) ) {
            new AlertDialog.Builder( ExerciseActivity.this ).setMessage( "Введите данные" ).create().show();
            return false;
        }

        return true;
    }

    private boolean checkIsNumberAndGreaterThenZero( EditText countOfTrying ) {
        if ( checkIsNumber( countOfTrying ) ) {
            //TODO  проверить что не ноль
        }

        return true;
    }

    private void getCurrentExercise() {
        try{
            final Dao<Exercise, Integer> exercizeDao = getHelper().getExercizeDao();
            exercise = exercizeDao.queryForId( getIntent().getIntExtra( EXERCISE_ID, - 1 ) );
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    public static void callMe( Context c, Integer id ) {
        Intent intent = new Intent( c, ExerciseActivity.class );
        intent.putExtra( EXERCISE_ID, id );
        c.startActivity( intent );
    }
}