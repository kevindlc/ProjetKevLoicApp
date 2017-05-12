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
        name = "ornithologueApi",
        version = "v1",
        resource = "ornithologue",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.carob.example.com",
                ownerName = "backend.myapplication.carob.example.com",
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
        // TODO: Implement this function
        logger.info("Calling getOrnithologue method");
        return null;
    }

    /**
     * This inserts a new <code>Ornithologue</code> object.
     *
   * @param ornithologue The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOrnithologue")
    public Ornithologue insertOrnithologue(Ornithologue ornithologue) {
        // TODO: Implement this function
        logger.info("Calling insertOrnithologue method");
        return ornithologue;
    }
}