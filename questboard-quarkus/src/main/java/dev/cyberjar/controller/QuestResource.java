package dev.cyberjar.controller;

import dev.cyberjar.domain.Difficulty;
import dev.cyberjar.domain.Quest;
import dev.cyberjar.dto.AssignQuestRequest;
import dev.cyberjar.dto.AssignQuestResponse;
import dev.cyberjar.dto.CreateQuestRequest;
import dev.cyberjar.service.QuestService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/quests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestResource {

    private final QuestService service;

    @Inject
    public QuestResource(QuestService service) {
        this.service = service;
    }

    @GET
    @Path("/{id}")
    public Quest findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    public List<Quest> search(
            @QueryParam("difficulty") Difficulty difficulty,
            @QueryParam("requiredClass") String requiredClass
    ) {
        return service.search(difficulty, requiredClass);
    }

    @POST
    public Response create(@Valid CreateQuestRequest request) {
        Quest quest = service.create(request);
        return Response.status(Response.Status.CREATED).entity(quest).build();
    }

    @PUT
    @Path("/{id}")
    public Quest update(
            @PathParam("id") Long id,
            @Valid CreateQuestRequest request
    ) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/assign")
    public AssignQuestResponse assign(
            @PathParam("id") Long id,
            @Valid AssignQuestRequest request
    ) {
        return service.assign(id, request);
    }


}
