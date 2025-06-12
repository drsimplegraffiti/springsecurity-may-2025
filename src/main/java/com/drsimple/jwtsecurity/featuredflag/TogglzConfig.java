package com.drsimple.jwtsecurity.featuredflag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.user.FeatureUser;
import org.togglz.core.user.NoOpUserProvider;
import org.togglz.core.user.UserProvider;

@Configuration
public class TogglzConfig {



    @Bean
    public UserProvider userProvider() {
        return () -> new FeatureUser() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public boolean isFeatureAdmin() {
                return false;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }
        }; // username, isAdmin
    }
}