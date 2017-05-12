package com.example.Kev.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "oiseauApi",
        version = "v1",
        resource = "oiseau",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.carob.example.com",
                ownerName = "backend.myapplication.carob.example.com",
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
        // TODO: Implement this function
        logger.info("Calling getOiseau method");
        return null;
    }

    /**
     * This inserts a new <code>Oiseau</code> object.
     *
     * @param oiseau The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOiseau")
    public Oiseau insertOiseau(Oiseau oiseau) {
        // TODO: Implement this function
        logger.info("Calling insertOiseau method");
        return oiseau;
    }
}