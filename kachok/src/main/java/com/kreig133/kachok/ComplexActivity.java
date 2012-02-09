package com.kreig133.kachok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.kreig133.kachok.dao.KachokDatabaseHelper;
import com.kreig133.kachok.dao.domain.Complex;
import com.kreig133.kachok.dao.domain.ComplexExercise;
import com.kreig133.kachok.dao.domain.Exercise;
import com.kreig133.kachok.dao.domain.Type;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class ComplexActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private static final String COMPLEX_ID = "complexId";

    private int complexId;
    private Complex complex;
    private ViewGroup layout;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.complex_items );


        complexId = getIntent().getIntExtra( COMPLEX_ID, - 1 );

        layout = ( ViewGroup ) findViewById( R.id.complexListLayout );

        try {
            Dao<Complex, Integer> dao = getHelper().getComplexDao();
            complex = dao.queryForId( complexId );
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            fillList();
        } catch ( SQLException ex ) {
            throw new RuntimeException( ex );
        }
    }

    private void fillList() throws SQLException {
        layout.removeAllViewsInLayout();

        final List<ComplexExercise> list =
            getHelper().getComplexExerciseDao().query(
                getHelper().getComplexExerciseDao().queryBuilder().where().eq( "complex_id",
                    complexId ).prepare()
            );

        Collections.sort( list, new Comparator<ComplexExercise>() {
            @Override
            public int compare( ComplexExercise complexExercise, ComplexExercise complexExercise1 ) {
                try {
                    if ( complexExercise.getExercise().getName() == null ) {
                        getHelper().getExercizeDao().refresh( complexExercise.getExercise() );
                    }
                    if( complexExercise1.getExercise().getName() == null ){
                        getHelper().getExercizeDao().refresh( complexExercise1.getExercise() );
                    }
                } catch ( SQLException e ) {
                    throw new RuntimeException( e );
                }

                return complexExercise.getExercise().getName().compareTo( complexExercise1.getExercise().getName() );
            }
        } );

        List<String> types = new LinkedList<String>();

        for ( ComplexExercise complexExercise : list ) {
            getHelper().getTypeDao().refresh( complexExercise.getExercise().getType() );
        }
        
        for ( int i = 0; i < list.size(); i++ ) {
            if( ! types.contains( list.get( i ).getExercise().getType().getName() ) ){
                
                String type = list.get( i ).getExercise().getType().getName();
                // Добавляем заголовок для типа
                LayoutInflater vi = ( LayoutInflater ) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                View v = vi.inflate( R.layout.type_header, null );
                fillText( v, R.id.typeHeader, type );

                layout.addView( v );
                
                List<Exercise> exercises = new LinkedList<Exercise>();
                
                for ( int j = i; j < list.size(); j++ ) {
                    if( list.get( j ).getExercise().getType().getName().equals( type ) ){
                        exercises.add( list.get( j ).getExercise() );
                    }
                }

                ListView child = new ListView( this );
                child.setAdapter( new ExerciseListAdapter( this, R.layout.exercise, exercises ) );
                layout.addView( child );
            }
        }
    }

    private class ExerciseListAdapter extends ArrayAdapter<Exercise> {

        public ExerciseListAdapter( Context context, int textViewResourceId, List<Exercise> items ) {
            super( context, textViewResourceId, items );
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {
            View v = convertView;
            if ( v == null ) {
                LayoutInflater vi = ( LayoutInflater ) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                v = vi.inflate( R.layout.exercise, null );
            }

            fillText( v, R.id.exercizeItem, getItem( position ).getName() );

            return v;
        }


    }

    private void fillText( View v, int id, String text ) {
        TextView textView = ( TextView ) v.findViewById( id );
        textView.setText( text == null ? "" : text );
    }

    public static void callMe( Context c, Integer id ) {
        Intent intent = new Intent( c, ComplexActivity.class );
        intent.putExtra( COMPLEX_ID, id );
        c.startActivity( intent );
    }
}
