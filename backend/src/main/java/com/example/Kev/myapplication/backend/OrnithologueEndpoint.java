package com.example.Kev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.NotFoundException;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.logging.Logger;

import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "ornithologueApi",
        version = "v1",
        resource = "ornithologue",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Kev.example.com",
                ownerName = "backend.myapplication.Kev.example.com",
                packagePath = ""
        )
)
public class OrnithologueEndpoint {

    private static final Logger logger = Logger.getLogger(OrnithologueEndpoint.class.getName());

    /**
     * This method gets the <code>Ornithologue</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Ornithologue</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getOrnithologue")
    public Ornithologue getOrnithologue(@Named("id") Long id) {
        Ornithologue orni = ofy().load().type(Ornithologue.class).id(id).now();
        if(orni == null){
            throw new NotFoundException();
        }
        logger.info("Calling getObservation method");
        return orni;
    }

    /**
     * This inserts a new <code>Ornithologue</code> object.
     *
     * @param ornithologue The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOrnithologue")
    public Ornithologue insertOrnithologue(Ornithologue ornithologue) {
        ofy().save().entity(ornithologue).now();
        logger.info("Created observation with ID: " + ornithologue.getId());
        return ofy().load().entity(ornithologue).now();
    }
}