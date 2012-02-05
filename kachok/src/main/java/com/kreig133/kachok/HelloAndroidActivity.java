package com.kreig133.kachok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.kreig133.kachok.dao.KachokDatabaseHelper;
import de.akquinet.android.androlog.Log;

public class HelloAndroidActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializes the logging
        Log.init();

        // Log a message (only on dev platform)
        Log.i(this, "onCreate");

        setContentView(R.layout.main);

        final View view = findViewById( R.id.createComplex );

        view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                final Intent intent = new Intent();
                intent.setClass( HelloAndroidActivity.this, EntryPoint.class );
                startActivity( intent );
            }
        } );
    }

}

