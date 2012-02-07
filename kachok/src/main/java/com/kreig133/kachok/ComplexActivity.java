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
import com.kreig133.kachok.dao.domain.Exercise;
import com.kreig133.kachok.dao.domain.Type;
import com.kreig133.kachok.dao.domain.TypeComplex;

import java.sql.SQLException;
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
//            kostyl( complex );
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

        for ( TypeComplex typeComplex : complex.getTypes() ) {
            final ListView child = new ListView( this );

            final List<Exercise> query = getHelper().getExercizeDao().query(
                    getHelper().getExercizeDao().queryBuilder().
                            where().eq( "type_id", typeComplex.getType().getId() ).prepare()
            );

            child.setAdapter( new ExerciseListAdapter( this, R.layout.exercise, query ) );

            LayoutInflater vi = ( LayoutInflater ) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View v = vi.inflate( R.layout.type_header, null );
            fillText( v, R.id.typeHeader,  getHelper().getTypeDao().queryForId( typeComplex.getType().getId() ).getName() );

            layout.addView( v );

            layout.addView( child );

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

    private void kostyl( Complex complex ) {
        try {
            final List<Type> list =
                    getHelper().getTypeDao().query( getHelper().getTypeDao().queryBuilder().prepare() );
            for ( Type type : list ) {
                getHelper().getTypeComplexDao().create( new TypeComplex( complex, type ) );
            }
        } catch ( SQLException e ) {
            new RuntimeException( e );
        }
    }
}
