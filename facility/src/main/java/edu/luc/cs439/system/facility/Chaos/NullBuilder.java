package edu.luc.cs439.system.facility.Chaos;

public class NullBuilder implements IChaosBuilder {

	@Override
	public IChaosBuilder ForMethod(String string) {
		return this;
	}

	@Override
	public void run() {
	}

    @Override
    public int getProbabilityOfProblem() {return 0;}

    @Override
    public void setProbabilityOfProblem(int problemRate) { }


}
