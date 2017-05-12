package com.example.Kev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "oiseauApi",
        version = "v1",
        resource = "oiseau",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Kev.example.com",
                ownerName = "backend.myapplication.Kev.example.com",
                packagePath = ""
        )
)
public class OiseauEndpoint {

    private static final Logger logger = Logger.getLogger(OiseauEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Oiseau.class);
    }

    /**
     * Returns the {@link Oiseau} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Oiseau} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "oiseau/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Oiseau get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Oiseau with ID: " + id);
        Oiseau oiseau = ofy().load().type(Oiseau.class).id(id).now();
        if (oiseau == null) {
            throw new NotFoundException("Could not find Oiseau with ID: " + id);
        }
        return oiseau;
    }

    /**
     * Inserts a new {@code Oiseau}.
     */
    @ApiMethod(
            name = "insert",
            path = "oiseau",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Oiseau insert(Oiseau oiseau) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that oiseau.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(oiseau).now();
        logger.info("Created Oiseau with ID: " + oiseau.getId());

        return ofy().load().entity(oiseau).now();
    }

    /**
     * Updates an existing {@code Oiseau}.
     *
     * @param id     the ID of the entity to be updated
     * @param oiseau the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Oiseau}
     */
    @ApiMethod(
            name = "update",
            path = "oiseau/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Oiseau update(@Named("id") long id, Oiseau oiseau) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(oiseau).now();
        logger.info("Updated Oiseau: " + oiseau);
        return ofy().load().entity(oiseau).now();
    }

    /**
     * Deletes the specified {@code Oiseau}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Oiseau}
     */
    @ApiMethod(
            name = "remove",
            path = "oiseau/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Oiseau.class).id(id).now();
        logger.info("Deleted Oiseau with ID: " + id);
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
            path = "oiseau",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Oiseau> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Oiseau> query = ofy().load().type(Oiseau.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Oiseau> queryIterator = query.iterator();
        List<Oiseau> oiseauList = new ArrayList<Oiseau>(limit);
        while (queryIterator.hasNext()) {
            oiseauList.add(queryIterator.next());
        }
        return CollectionResponse.<Oiseau>builder().setItems(oiseauList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Oiseau.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Oiseau with ID: " + id);
        }
    }
}