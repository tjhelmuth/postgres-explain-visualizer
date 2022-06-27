package com.tjhelmuth;

import com.intellij.database.dataSource.DatabaseConnectionCore;
import com.intellij.database.dataSource.connection.statements.ReusableSmartStatement;
import com.intellij.database.dialects.postgres.plan.PgRawPlanData;
import com.intellij.database.plan.PlanRetrievalException;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PlanData extends PgRawPlanData {
    @Override
    public void load(@NotNull DatabaseConnectionCore connection, @NotNull String statement, boolean run) {
        this.json = null;
        //analyze, verbose, costs, buffers, format json
        this.useStatementWithPreserved(connection, (ResourceUser<ReusableSmartStatement<String>>) s ->
                s.noisy().execute("EXPLAIN (FORMAT JSON, VERBOSE, COSTS, BUFFERS" + (run ? ", ANALYSE" : "") + ") " + statement,
        PgRawPlanData.processing((rs) -> {
            if (!rs.next()) {
                PgRawPlanData.failWithEmptyResultSetError();
            }

            if (rs.getMetaData().getColumnCount() != 1) {
                throw new PlanRetrievalException("Database returned data in unknown format");
            } else {
                String res = rs.getString(1);
                if (res == null) {
                    throw new PlanRetrievalException("Database returned null plan");
                } else if (rs.next()) {
                    throw new PlanRetrievalException("Database returned too many data");
                } else {
                    PlanData.this.json = res;
                }
            }
        })), new StateSaver[0]);
    }
}
