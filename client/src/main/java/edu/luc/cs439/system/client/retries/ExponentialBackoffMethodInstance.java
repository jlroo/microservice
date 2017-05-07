package edu.luc.cs439.system.client.retries;

import edu.luc.cs439.system.contracts.DefaultResponse;

public class ExponentialBackoffMethodInstance implements ISingleMethodPolicy {

    private DefaultResponse _response;
    private int _retriesRemaining = 5;
    private int _currentRetryTime = 200;

    @Override
    public void HadResponse(DefaultResponse response) {
        _response = response;
        if(!_response.Success) {
            --_retriesRemaining;
        }
    }

    @Override
    public void PerformWaitIfNeeded() throws Exception {
        if(!_response.Success) {
            Thread.sleep(_currentRetryTime);
            _currentRetryTime = _currentRetryTime * 4;
        }
    }

    @Override
    public void ThrowErrorFromResponseIfAppropriate() throws Exception {
        if(_retriesRemaining <= 0 && !_response.Success) {
            throw new RuntimeException(_response.Message + "\r\n" + _response.StackTrace);
        }
    }

    @Override
    public boolean ShouldRetry() {
        return !_response.Success && _retriesRemaining > 0 && ErrorIsSomethingWeShouldRetryFor();
    }

    private boolean ErrorIsSomethingWeShouldRetryFor() {
        if(_response.Message.contains("deadlock")){
            return true;
        }
        if(_response.Message.contains("connection")) {
            return true;
        }
        return false;
    }

}
