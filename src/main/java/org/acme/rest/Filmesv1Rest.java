package org.acme.rest;

import com.google.gson.Gson;
import org.acme.dto.IntervalProducer;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.opentracing.Traced;
import org.jboss.logging.Logger;
import services.FilmesServices;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Path("/films")
@Traced
@RequestScoped
public class Filmesv1Rest {

    @Inject
    Logger LOG;

    @Inject
    FilmesServices filmesServices;

    @GET
    // Anotações para documentação no Swagger
    @Operation(summary = "Obter o produtor com maior intevalo entre dois premios consecutivos e o que obteve dois " +
                         "premios mais rapido",
            description = "Obter o produtor com maior intevalo entre dois premios consecutivos")
    @APIResponse(responseCode = "200", description = "IntervalProducer",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = IntervalProducer.class)) })
    public Response getIntervalProducer(){




        IntervalProducer resposta = new IntervalProducer();
                filmesServices
                .getIntervalProducer();

        Gson gson = new Gson();
        String json = gson.toJson(resposta);
        LOG.debug("Resposta: " + json);

        return Response.status(Response.Status.OK).entity(resposta).build();

    }

}
