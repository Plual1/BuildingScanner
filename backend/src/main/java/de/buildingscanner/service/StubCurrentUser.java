package de.buildingscanner.service;

import org.springframework.stereotype.Component;

@Component
class StubCurrentUser implements CurrentUser {

    @Override
    public String name() {
        return "demo-surveyor";
    }
}
