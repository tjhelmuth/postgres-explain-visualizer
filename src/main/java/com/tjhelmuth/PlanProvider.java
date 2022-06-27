package com.tjhelmuth;

import com.intellij.database.Dbms;
import com.intellij.database.dialects.base.plan.AbstractPlanModelBuilder;
import com.intellij.database.dialects.postgres.plan.PgExplainPlanProvider;
import com.intellij.database.util.Version;
import org.jetbrains.annotations.NotNull;

public class PlanProvider extends PgExplainPlanProvider {
    public PlanProvider() {
        super(Dbms.POSTGRES);
    }

    @Override
    public AbstractPlanModelBuilder<?, ?> createBuilder(@NotNull Version version, boolean run) {
        return new PlanModelBuilder();
    }
}
