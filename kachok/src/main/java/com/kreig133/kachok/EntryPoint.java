package com.kreig133.kachok;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kreig133.kachok.dao.domain.Complex;
import com.kreig133.kachok.dao.KachokDatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class EntryPoint extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private ListView listView;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.main );

        listView = ( ListView ) findViewById( R.id.complexList );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Complex complex = (Complex) listView.getAdapter().getItem( position );
                callComplexPresenter( EntryPoint.this, complex );
            }
        } );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Complex complex = (Complex) listView.getAdapter().getItem( position );
                callComplexPresenter( EntryPoint.this, complex );
            }
        } );

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

    private void fillList() throws SQLException{
        Log.i( EntryPoint.class.getName(), "Show list again" );
        
        Dao<Complex, Integer> dao = getHelper().getComplexDao();

        QueryBuilder<Complex, Integer> builder = dao.queryBuilder();
        
        List<Complex> complexList = dao.query(  builder.prepare() );
        
        ArrayAdapter<Complex> adapter = new ComplexAdapter( this, R.layout.complex, complexList );

        listView.setAdapter( adapter );
    }

    private void callComplexPresenter( Context context, Complex complex ) {
        ComplexActivity.callMe( context, complex.getId() );
    }

    private class ComplexAdapter extends ArrayAdapter<Complex> {

        public ComplexAdapter( Context context, int textViewResourceId, List<Complex> items ) {
            super( context, textViewResourceId, items );
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {
            View v = convertView;
            if ( v == null ) {
                LayoutInflater vi = ( LayoutInflater ) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                v = vi.inflate( R.layout.complex, null );
            }

            Complex complex = getItem( position );

            fillText( v, R.id.complexName, complex.getName() );

            return v;
        }

        private void fillText( View v, int id, String text ) {
            TextView textView = ( TextView ) v.findViewById( id );
            textView.setText( text == null ? "" : text );
        }
    }
}