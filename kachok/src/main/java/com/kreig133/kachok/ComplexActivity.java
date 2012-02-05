package com.kreig133.kachok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.kreig133.kachok.dao.KachokDatabaseHelper;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class ComplexActivity extends OrmLiteBaseActivity<KachokDatabaseHelper> {

    private static final String COMPLEX_ID = "complexId";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );


    }

    public static void callMe( Context c, Integer id ) {
        Intent intent = new Intent( c, ComplexActivity.class );
        intent.putExtra( COMPLEX_ID, id );
        c.startActivity( intent );
    }
}
