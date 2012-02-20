package com.kreig133.kachok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.kreig133.kachok.dao.KachokDatabaseHelper;
import com.kreig133.kachok.dao.domain.Attempt;
import com.kreig133.kachok.dao.domain.Complex;
import com.kreig133.kachok.dao.domain.ComplexExercise;
import com.kreig133.kachok.dao.domain.Exercise;
import com.kreig133.kachok.service.AttemptService;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class ComplexActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private static final String COMPLEX_ID = "complexId";
    private static final String TYPE_NAME = "typeName";
    private static final String EXERCISE_NAME = "exerciseName";
    private static final String EXERCISE_DATE = "exerciseDate";
    private static final String EXERCISE_WEIGHT = "exerciseWeight";
    private static final String EXERCISE_REPEAT_COUNT = "exerciseRepeatCount";

    private Complex complex;
    private ViewGroup layout;
    private List<String> types;
    private List<List<Exercise>> listOfListsOfExercise;
    private ExpandableListView expandableListView;
    private ViewGroup currentView;
    private Exercise currentExercise;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy" );

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.complex_list );

        layout = ( ViewGroup ) findViewById( R.id.complexListLayout );
        expandableListView = ( ExpandableListView ) findViewById( R.id.expandableListOfExercise );

        expandableListView.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick( ExpandableListView parent, View v, int groupPosition, int childPosition, long id ) {
                currentView = (ViewGroup) v;
                currentExercise = listOfListsOfExercise.get( groupPosition ).get( childPosition );
                ExerciseActivity.callMe(
                        ComplexActivity.this,
                        currentExercise.getId(),
                        complex.getId()
                );
                return true;
            }
        } );

        getCurrentComplex();

        ( (TextView) findViewById( R.id.complexName ) ).setText( complex.getName() );

        try {
            fillList();
        } catch ( SQLException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( currentExercise != null ) {
            updateSelectedExercise();
        }
    }

    private void updateSelectedExercise() {
        Attempt lastAttempt = AttemptService.getLatestAttemptByExercise( currentExercise, getHelper() );
        ( ( TextView ) currentView.findViewById( R.id.exercizeDate   ) ).setText( extractLastDate  ( lastAttempt ) );
        ( ( TextView ) currentView.findViewById( R.id.exercizeWeight ) ).setText( extractLastWeight( lastAttempt ) );
        ( ( TextView ) currentView.findViewById( R.id.exercizeRepeatCount ) )
                .setText( extractLastRepeatTime( lastAttempt ) );

    }

    private void getCurrentComplex() {
        try {
            Dao<Complex, Integer> dao = getHelper().getComplexDao();
            complex = dao.queryForId( getIntent().getIntExtra( COMPLEX_ID, - 1 ) );
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    private void fillList() throws SQLException{

        final List<ComplexExercise> list = getComplexExerciseList();

//        sortComplexExerciesByExerciseName( list );

        types = new LinkedList<String>();
        listOfListsOfExercise = new ArrayList<List<Exercise>>();

        String previousType = "";
        List<Exercise> exercises = null;
        for ( int i = 0; i < list.size(); i++ ) {
            if(  ! list.get( i ).getExercise().getType().getName().equals( previousType ) ) {

                String type = ( previousType = list.get( i ).getExercise().getType().getName() );
                types.add( type );

                if ( exercises != null ) {
                    listOfListsOfExercise.add( exercises );
                }
                exercises = new LinkedList<Exercise>();
            }

            assert exercises != null;
            exercises.add( list.get( i ).getExercise() );
        }

        if ( exercises != null ) {
            listOfListsOfExercise.add( exercises );
        }

        expandableListView.setAdapter( new SimpleExpandableListAdapter(
                ComplexActivity.this,
                getTypeHeaders( types ),
                R.layout.type_header,
                new String[] { TYPE_NAME },
                new int[] { R.id.typeHeader },
                getData( listOfListsOfExercise ),
                R.layout.exercise,
                new String[] { EXERCISE_NAME, EXERCISE_DATE, EXERCISE_WEIGHT, EXERCISE_REPEAT_COUNT },
                new int[] { R.id.exercizeItem, R.id.exercizeDate, R.id.exercizeWeight, R.id.exercizeRepeatCount }
        ) );
    }

    private List<List<Map<String, String>>> getData( List<List<Exercise>> listListsOfExercise ) throws SQLException {
        List<List<Map<String, String>>> result = new ArrayList<List<Map<String, String>>>();

        for ( List<Exercise> list : listListsOfExercise ) {
            List<Map<String, String>> listOfMap = new ArrayList<Map<String, String>>();
            for ( Exercise exercise : list ) {
                final Attempt lastAttempt = AttemptService.getLatestAttemptByExercise( exercise, getHelper() );

                Map<String, String> map = new HashMap<String, String>();
                map.put( EXERCISE_NAME, exercise.getName() );
                map.put( EXERCISE_DATE, extractLastDate( lastAttempt ) );
                map.put( EXERCISE_WEIGHT, extractLastWeight( lastAttempt ) );
                map.put( EXERCISE_REPEAT_COUNT, extractLastRepeatTime( lastAttempt ) );
                listOfMap.add( map );
            }
            result.add( listOfMap );
        }
        return result;
    }

    private String extractLastRepeatTime( Attempt lastAttempt ) {
        if ( lastAttempt == null || lastAttempt.getNumberOfRepeat() == null ) {
            return "--";
        }
        return lastAttempt.getNumberOfRepeat().toString();
    }

    private String extractLastWeight( Attempt lastAttempt ) {
        if ( lastAttempt == null || lastAttempt.getWeight() == null ) {
            return "--";
        }

        return lastAttempt.getWeight().toString();
    }

    private String extractLastDate( Attempt lastAttempt ) {
        if ( lastAttempt == null || lastAttempt.getDate() == null ) {
            return "Ни разу";
        }
        return simpleDateFormat.format( lastAttempt.getDate() );
    }

    private List<Map<String, String>> getTypeHeaders( List<String> types ) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for ( String type : types ) {
            Map<String, String> map = new HashMap<String, String>( 1 );
            map.put( TYPE_NAME, type );
            result.add( map );
        }
        return result;
    }

    private List<ComplexExercise> getComplexExerciseList() throws SQLException {
        final List<ComplexExercise> list = getHelper().getComplexExerciseDao().query(
                getHelper().getComplexExerciseDao().queryBuilder().orderBy( "order", true ).
                        where().eq( "complex_id", complex.getId() ).prepare()
        );

        for ( ComplexExercise complexExercise : list ) {
            getHelper().getExercizeDao().refresh( complexExercise.getExercise() );
            getHelper().getTypeDao().refresh( complexExercise.getExercise().getType() );
        }
        return list;
    }

    public static void callMe( Context c, Integer id ) {
        Intent intent = new Intent( c, ComplexActivity.class );
        intent.putExtra( COMPLEX_ID, id );
        c.startActivity( intent );
    }
}
