package com.snimmo.kube.cvmw;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class VariableService {

    private final ConfigMapVariableResolver configMapVariableResolver;

    @Inject
    public VariableService(ConfigMapVariableResolver configMapVariableResolver) {
        this.configMapVariableResolver = configMapVariableResolver;
    }

    public Optional<String> find(String variable) {
        return configMapVariableResolver.find(variable);
    }

}
