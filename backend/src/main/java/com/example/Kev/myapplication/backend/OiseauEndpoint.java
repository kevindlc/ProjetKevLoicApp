package com.example.Kev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.NotFoundException;

import java.util.logging.Logger;

import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * An endpoint class we are exposing
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

    /**
     * This method gets the <code>Oiseau</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Oiseau</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getOiseau")
    public Oiseau getOiseau(@Named("id") Long id) {
        Oiseau ois = ofy().load().type(Oiseau.class).id(id).now();
        if(ois == null){
            throw new NotFoundException();
        }
        logger.info("Calling getObservation method");
        return ois;
    }

    /**
     * This inserts a new <code>Oiseau</code> object.
     *
     * @param oiseau The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOiseau")
    public Oiseau insertOiseau(Oiseau oiseau) {
        ofy().save().entity(oiseau).now();
        logger.info("Created observation with ID: " + oiseau.getId());
        return ofy().load().entity(oiseau).now();
    }
}