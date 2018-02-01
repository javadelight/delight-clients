package de.mxro.client.jre;

import de.mxro.client.ClientEnv;
import de.mxro.client.ClientsCommon;
import de.mxro.client.internal.ClientEnvImpl;
import de.mxro.metrics.jre.Metrics;
import delight.async.properties.jre.Properties;
import delight.concurrency.jre.ConcurrencyJre;
import delight.promise.jre.Promises;

public final class ClientEnvironments extends ClientsCommon {

    public static ClientEnv create() {
        final ClientEnv client = new ClientEnvImpl();

        registerFactories(client);

        return client;
    }

    private static ClientEnv registerFactories(final ClientEnv forClient) {
        forClient.factories().register(Promises.createPromiseFactory());
        forClient.factories().register(Properties.createPropertiesFactory());

        forClient.factories().register(Metrics.createMetricsFactory());


        forClient.factories().register(ConcurrencyJre.createFactory());

        return forClient;
    }

}
