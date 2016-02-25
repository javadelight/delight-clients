package de.mxro.client;

import delight.async.properties.PropertiesCommon;
import delight.log.LogsCommon;
import delight.promise.PromisesCommon;

import de.mxro.client.internal.ClientImpl;
import de.mxro.metrics.MetricsCommon;

/**
 * Entry-point for creating cross-platform clients.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class ClientsCommon {

    public static ClientEnv createPortable() {
        final ClientEnv client = new ClientImpl();

        client.factories().register(PromisesCommon.createUnsafePromiseFactory());

        client.factories().register(PropertiesCommon.createUnsafePropertiesFactory());

        client.factories().register(MetricsCommon.createUnsafeFactory());

        client.factories().register(LogsCommon.createUnsafeLogsFactory());

        return client;

    }

}
