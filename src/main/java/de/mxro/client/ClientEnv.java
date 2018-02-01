package de.mxro.client;

import delight.async.Operation;
import delight.async.properties.PropertyNode;
import delight.concurrency.Concurrency;
import delight.factories.FactoryCollection;
import delight.functional.Success;
import delight.promise.Promise;
import delight.promise.helper.PromiseFactory;
import delight.state.StateRegistry;
import de.mxro.service.ServiceRegistry;

public interface ClientEnv extends PromiseFactory {

    public FactoryCollection factories();

    public ServiceRegistry services();

    public PropertyNode metrics();

    public StateRegistry state();


    public Promise<Success> stop();

    public Concurrency concurrency();

    @Override
    public <R> Promise<R> promise(Operation<R> operation);

}
