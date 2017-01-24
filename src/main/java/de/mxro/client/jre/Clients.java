package de.mxro.client.jre;

import delight.async.properties.jre.Properties;
import delight.concurrency.jre.ConcurrencyJre;
import delight.log.jre.Logs;
import delight.promise.jre.Promises;

import de.mxro.client.ClientEnv;
import de.mxro.client.ClientsCommon;
import de.mxro.client.internal.ClientImpl;
import de.mxro.metrics.jre.Metrics;

public final class Clients extends ClientsCommon {

    public static ClientEnv create() {
        final ClientEnv client = new ClientImpl();

        registerFactories(client);

        return client;
    }

    private static ClientEnv registerFactories(final ClientEnv forClient) {
        forClient.factories().register(Promises.createPromiseFactory());
        forClient.factories().register(Properties.createPropertiesFactory());

        forClient.factories().register(Metrics.createMetricsFactory());

        forClient.factories().register(Logs.createLogsFactory());

        forClient.factories().register(ConcurrencyJre.createFactory());

        return forClient;
    }

}
