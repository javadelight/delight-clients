package de.mxro.client.tests

import de.mxro.client.jre.ClientEnvironments
import de.mxro.metrics.jre.Metrics
import de.oehme.xtend.junit.JUnit
import delight.async.properties.jre.Properties
import org.junit.Test

@JUnit
class TestCreateAndStop {
	
	
	@Test
	def void test() {
		
		var client = ClientEnvironments.create

		
		
		client.metrics.record(Metrics.increment("counter"))
		
		client.state.record(Properties.set("123", "456"));
		
		client.stop.get
		
	}
	
	
}