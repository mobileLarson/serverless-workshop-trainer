package de.openknowledge.workshop.cloud.serverless.util;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.commons.lang3.NotImplementedException;

public class FakeLambdaLogger implements LambdaLogger {

    public void log(String var1) {
        throw new NotImplementedException("lambdaLogger::log(String var1)");
    }

    public void log(byte[] var1) {
        throw new NotImplementedException("lambdaLogger::log(byte[] var1)");
    }
}
