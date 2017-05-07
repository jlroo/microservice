package edu.luc.cs439.system.facility.Chaos;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class ChaosSource {
	
	private static final Random _rand = new Random(new Date().getTime());
	private static final IChaosLogger _logger = new DefaultLogger();
    private static int ProbabilityOfProblem = 10;
    public static int getProbabilityOfProblem(){return ProbabilityOfProblem;}
    public static void setProbabilityOfProblem(int problemRate){ProbabilityOfProblem = problemRate;}

    public static IChaosBuilder ForDataAccess(String name) throws Exception {
        final int num = _rand.nextInt(ProbabilityOfProblem);
		if(num == 1) {

			final int whichProblem = _rand.nextInt(2);
			switch(whichProblem){
			case 0:
				return new LatencyProblem(name, _logger);
			case 1:
				return new DatabaseConnectionLostProblem(name, _logger);
			case 2:
				return new DatabaseDeadlockProblem(name, _logger);
			default:
				throw new Exception("unexpected random value");
			}
			
		} else {
			return new NullBuilder();
		}
	}

	public static IChaosBuilder ForService(String name) throws Exception {

		final int num = _rand.nextInt(ProbabilityOfProblem);
		if(num == 1) {

			final int whichProblem = _rand.nextInt(1);
			switch(whichProblem){
			case 0:
				return new LatencyProblem(name, _logger);
			case 1:
				return new WebServiceRandomExceptionProblem(name, _logger);
			default:
				throw new Exception("unexpected random value");
			}
		} else {
			return new NullBuilder();
		}
	}
	

}
