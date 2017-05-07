package edu.luc.cs439.system.client.retries;

public class ExponentialBackoffRetryPolicy implements IRetryPolicy {

	@Override
	public ISingleMethodPolicy NewMethod() {
		return new ExponentialBackoffMethodInstance();
	}

}
