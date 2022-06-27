package com.tjhelmuth;

import com.intellij.database.plan.PlanModel;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class PgPlanModel extends PlanModel {
    private final String json;

    public PgPlanModel(@NotNull GenericNode root, boolean actual, @NotNull EnumSet<Feature> unsupported, String json) {
        super(root, actual, unsupported);
        this.json = json;
    }

    public String getJson(){
        return json;
    }
}
