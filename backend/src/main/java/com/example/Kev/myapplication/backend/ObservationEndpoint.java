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
        name = "observationApi",
        version = "v1",
        resource = "observation",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.carob.example.com",
                ownerName = "backend.myapplication.carob.example.com",
                packagePath = ""
        )
)
public class ObservationEndpoint {

    private static final Logger logger = Logger.getLogger(ObservationEndpoint.class.getName());

    /**
     * This method gets the <code>Observation</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Observation</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getObservation")
    public Observation getObservation(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getObservation method");
        return null;
    }

    /**
     * This inserts a new <code>Observation</code> object.
     *
     * @param observation The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertObservation")
    public Observation insertObservation(Observation observation) {
        // TODO: Implement this function
        logger.info("Calling insertObservation method");
        return observation;
    }
}