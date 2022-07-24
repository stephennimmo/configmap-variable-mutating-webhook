package com.snimmo.kube.cvmw;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.ws.rs.Produces;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Startup
public class ConfigMapVariableResolver {

    private static final Logger log = LoggerFactory.getLogger(ConfigMapVariableResolver.class);

    private final Properties properties = new Properties();

    private void onStart(@Observes StartupEvent ev) {
        log.info("The application is starting...");
        try (InputStream inputStream = this.getClass().getResourceAsStream("/example.properties")) {
            properties.load(inputStream);
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
    }

    public Optional<String> find(String variable) {
        return properties.containsKey(variable) ? Optional.of(properties.getProperty(variable)) : Optional.empty();
    }

}
