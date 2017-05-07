package edu.luc.cs439.system.facility.Chaos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogger implements IChaosLogger {

    static final Logger LOG = LoggerFactory.getLogger(DefaultLogger.class);

    @Override
	public void Log(String objectType, String methodName, String chaosName) {
		LOG.error(objectType + " in method " + methodName + " had problem " + chaosName);
	}

}
