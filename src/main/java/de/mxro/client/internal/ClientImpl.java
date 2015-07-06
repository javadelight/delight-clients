package de.mxro.client.internal;

import delight.async.AsyncCommon;
import delight.async.Operation;
import delight.async.callbacks.ValueCallback;
import delight.concurrency.Concurrency;
import delight.concurrency.configuration.ConcurrencyConfigurations;
import delight.factories.Factories;
import delight.factories.FactoryCollection;
import delight.functional.Success;
import delight.promise.Promise;
import delight.promise.PromiseConfiguration;
import delight.promise.helper.PromiseFactory;

import java.util.ArrayList;
import java.util.List;

import de.mxro.async.log.LogsConfiguration;
import de.mxro.async.properties.PropertiesConfiguration;
import de.mxro.async.properties.PropertyNode;
import de.mxro.client.Client;
import de.mxro.metrics.MetricsConfiguration;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.Services;

public class ClientImpl implements Client {

    private FactoryCollection factories;
    private ServiceRegistry services;
    private PropertyNode metrics;
    private PropertyNode state;
    private PropertyNode logs;
    private PromiseFactory promiseFactory;
    private Concurrency concurrency;

    @Override
    public FactoryCollection factories() {
        if (this.factories != null) {
            return this.factories;
        }

        this.factories = Factories.create();

        return this.factories;
    }

    @Override
    public ServiceRegistry services() {
        if (this.services != null) {
            return this.services;
        }

        this.services = Services.create();

        return this.services;
    }

    @Override
    public PropertyNode metrics() {
        if (this.metrics == null) {

            this.metrics = (PropertyNode) factories().create(new MetricsConfiguration(), Factories.noDependencies());
        }
        return this.metrics;
    }

    @Override
    public PropertyNode state() {
        if (this.state == null) {
            this.state = (PropertyNode) factories().create(new PropertiesConfiguration(), Factories.noDependencies());
        }
        return this.state;
    }

    @Override
    public PropertyNode logs() {
        if (this.logs == null) {
            this.logs = (PropertyNode) factories().create(new LogsConfiguration(), Factories.noDependencies());
        }
        return this.logs;
    }

    @Override
    public Promise<Success> stop() {

        return promise(new Operation<Success>() {

            @Override
            public void apply(final ValueCallback<Success> callback) {
                final List<Operation<Success>> toShutdown = new ArrayList<Operation<Success>>(3);

                if (metrics != null) {
                    toShutdown.add(metrics.stop());
                }

                if (state != null) {
                    toShutdown.add(state.stop());
                }

                if (logs != null) {
                    toShutdown.add(logs.stop());
                }

                AsyncCommon.parallel(toShutdown, new ValueCallback<List<Object>>() {

                    @Override
                    public void onFailure(final Throwable t) {
                        callback.onFailure(t);
                    }

                    @Override
                    public void onSuccess(final List<Object> value) {
                        callback.onSuccess(Success.INSTANCE);
                    }

                });

            }
        });

    }

    @Override
    public Concurrency concurrency() {
        if (this.concurrency == null) {
            this.concurrency = (Concurrency) factories().create(ConcurrencyConfigurations.any(),
                    Factories.noDependencies());
        }

        return this.concurrency;
    }

    @Override
    public <R> Promise<R> promise(final Operation<R> operation) {
        if (promiseFactory == null) {
            this.promiseFactory = (PromiseFactory) factories().create(new PromiseConfiguration(),
                    Factories.noDependencies());
        }
        return this.promiseFactory.promise(operation);
    }
}
