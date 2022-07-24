package com.snimmo.kube.cvmw;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Expression;
import io.quarkus.qute.TemplateInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mutate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MutateResource {

    private static final Logger log = LoggerFactory.getLogger(MutateResource.class);

    private final Engine engine;
    private final VariableService variableService;

    @Inject
    public MutateResource(Engine engine, VariableService variableService) {
        this.engine = engine;
        this.variableService = variableService;
    }

    @POST
    public Response post(String manifest) {
        TemplateInstance templateInstance = engine.parse(manifest).instance();
        for (Expression expression : templateInstance.getTemplate().getExpressions()) {
            this.variableService.find(expression.toOriginalString()).map(s -> templateInstance.data(expression.toOriginalString(), s));
        }
        return Response.ok(templateInstance.render()).build();
    }

}