package com.smen.Config;

import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionConfig {
    @JacksonInject
    private String car;
}


