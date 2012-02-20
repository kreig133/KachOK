package com.kreig133.kachok.service;

import com.kreig133.kachok.dao.KachokDatabaseHelper;
import com.kreig133.kachok.dao.domain.Attempt;
import com.kreig133.kachok.dao.domain.Exercise;

import java.sql.SQLException;
import java.util.List;

/**
 * @author C.C.-fag
 * @version 1.0
 */
public class AttemptService {

    public static Attempt getLatestAttemptByExercise( Exercise exercise, KachokDatabaseHelper helper ) {
        final List<Attempt> latestAttemptsByExercise = getLatestAttemptsByExercise( exercise, 1l, helper );

        return latestAttemptsByExercise.isEmpty() ? null : latestAttemptsByExercise.get( 0 );
    }

    public static List<Attempt> getLatestAttemptsByExercise(
            Exercise exercise,
            long limit,
            KachokDatabaseHelper helper
    ) {
        try {
            return helper.getAttemptDao().query( helper.getAttemptDao().queryBuilder().orderBy( "date", false )
                    .limit( limit ).where().eq( "exercise_id", exercise.getId() ).prepare() );
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
    }
}
