package edu.luc.cs439.system.facility.Chaos;

public class LatencyProblem implements IChaosBuilder {

	private String _method;
	private final String _dataAccessName;
	private final IChaosLogger _log;
	
	public LatencyProblem(String name, IChaosLogger log) {
		_dataAccessName = name;
		_log = log;
	}

	@Override
	public IChaosBuilder ForMethod(String method) {
		_method = method;
		return this;
	}

	@Override
	public void run() throws Exception {
		_log.Log(_dataAccessName, _method, "Latency");
		
		Thread.sleep(500);
	}

	@Override
	public int getProbabilityOfProblem() {return 0;}

	@Override
	public void setProbabilityOfProblem(int problemRate) { }

}
