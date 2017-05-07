package edu.luc.cs439.system.client.retries;

public interface IRetryPolicy {

	ISingleMethodPolicy NewMethod();

}
