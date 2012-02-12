package com.kreig133.kachok;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.kreig133.kachok.dao.KachokDatabaseHelper;
import com.kreig133.kachok.dao.domain.Attempt;
import com.kreig133.kachok.dao.domain.Exercise;

import java.sql.SQLException;
import java.util.Date;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class ExerciseActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private static final String EXERCISE_ID = "exercise";
    private Exercise exercise;
    private Button addButton;
    private EditText countOfTrying;
    private EditText weightOfTrying;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.exercise_trying_list );

        addButton = ( Button ) findViewById( R.id.exerciseAddTrying );
        countOfTrying = ( EditText ) findViewById( R.id.countOfTrying );
        weightOfTrying = ( EditText ) findViewById( R.id.weightOfTrying );

        addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                addNewTrying();
            }
        } );
        getCurrentExercise();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillList();
    }

    private void addNewTrying() {
        try{
            if( checkIsNumberAndGreaterThenZero( countOfTrying ) && checkIsNumber( weightOfTrying ) ){
                final Attempt attempt = new Attempt();
                attempt.setDate( new Date() );
                attempt.setExercise( this.exercise );
                attempt.setNumberOfRepeat( new Integer( countOfTrying.getText().toString() ) );
                attempt.setSportsman( null /* TODO */ );
                attempt.setWeight( new Double( weightOfTrying.getText().toString() ) );

                getHelper().getAttemptDao().create( attempt );
                fillList();
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    private void fillList() {

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
