package com.example.Kev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.NotFoundException;
import com.example.Kev.myapplication.backend.Observation;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.annotation.Nullable;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "observationApi",
        version = "v1",
        resource = "observation",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Kev.example.com",
                ownerName = "backend.myapplication.Kev.example.com",
                packagePath = ""
        )
)
public class ObservationEndpoint {

    private static final int DEFAULT_LIST_LIMIT = 20;

    private static final Logger logger = Logger.getLogger(ObservationEndpoint.class.getName());


    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Observation.class);
    }

    /**
     * This method gets the <code>Observation</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Observation</code> associated with <code>id</code>.
     */
    @ApiMethod(
            name = "get",
            path = "observation/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Observation get(@Named("id") Long id) throws NotFoundException {
        Observation obs = ofy().load().type(Observation.class).id(id).now();
        if(obs == null){
            throw new NotFoundException();
        }
        logger.info("Calling getObservation method");
        return obs;
    }

    /**
     * This inserts a new <code>Observation</code> object.
     *
     * @param observation The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(
            name = "insert",
            path = "observation",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Observation insert(Observation observation) {

        ofy().save().entity(observation).now();
        logger.info("Created observation with ID: " + observation.getId());
        return ofy().load().entity(observation).now();
    }


    /**
     * Updates an existing {@code Person}.
     *
     * @param id     the ID of the entity to be updated
     * @param observation the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Person}
     */
    @ApiMethod(
            name = "update",
            path = "observation/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Observation update(@Named("id") long id, Observation observation) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(observation).now();
        logger.info("Updated Person: " + observation);
        return ofy().load().entity(observation).now();
    }

    /**
     * Deletes the specified {@code observation}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Person}
     */
    @ApiMethod(
            name = "remove",
            path = "observation/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Observation.class).id(id).now();
        logger.info("Deleted Observation with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "observation",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Observation> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Observation> query = ofy().load().type(Observation.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Observation> queryIterator = query.iterator();
        List<Observation> observationList = new ArrayList<Observation>(limit);
        while (queryIterator.hasNext()) {
            observationList.add(queryIterator.next());
        }
        return CollectionResponse.<Observation>builder().setItems(observationList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Observation.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException();
        }
    }
}