package com.tjhelmuth;

import com.intellij.database.dialects.postgres.plan.PgPlanModelBuilder;
import com.intellij.database.dialects.postgres.plan.PgRawPlanData;
import com.intellij.database.plan.PlanModel;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class PlanModelBuilder extends PgPlanModelBuilder {
    private static final EnumSet<PlanModel.Feature> unsupportedFeatures = EnumSet.noneOf(PlanModel.Feature.class);
    @Override
    public @NotNull PgRawPlanData createData() {
        return new PlanData();
    }

    @Override
    public @NotNull PlanModel build(@NotNull PgRawPlanData data) {
        return new PgPlanModel(this.getRootNode(), this.myActual, unsupportedFeatures, data.json);
    }
}
