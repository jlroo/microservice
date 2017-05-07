package edu.luc.cs439.system.facility.Chaos;

public interface IChaosBuilder {

	IChaosBuilder ForMethod(String name);

    int getProbabilityOfProblem();
    void setProbabilityOfProblem(int problemRate);

    void run() throws Exception;

}
