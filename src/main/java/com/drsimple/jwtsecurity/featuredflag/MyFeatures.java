package com.drsimple.jwtsecurity.featuredflag;

import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;

public enum MyFeatures implements Feature {

    DISCOUNT_APPLIED;

    public boolean isActive() {
        return FeatureContext
                .getFeatureManager()
                .isActive(this);
    }
}