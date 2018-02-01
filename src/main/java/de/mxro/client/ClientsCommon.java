package de.mxro.client;

import de.mxro.client.internal.ClientEnvImpl;
import de.mxro.metrics.MetricsCommon;
import delight.async.properties.PropertiesCommon;
import delight.promise.PromisesCommon;

/**
 * Entry-point for creating cross-platform clients.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class ClientsCommon {

    public static ClientEnv createPortable() {
        final ClientEnv client = new ClientEnvImpl();

        client.factories().register(PromisesCommon.createUnsafePromiseFactory());

        client.factories().register(PropertiesCommon.createUnsafePropertiesFactory());

        client.factories().register(MetricsCommon.createUnsafeFactory());


        return client;

    }

}
