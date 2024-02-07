package de.openknowledge.workshop.serverless.util;

import software.amazon.awssdk.regions.Region;

public class AwsRegionProvider {

    // region to use: e.g. Frankfurt == EU_CENTRAL_1, LONDON == EU_WEST_2
    // for "your" region" see https://docs.aws.amazon.com/de_de/AWSEC2/latest/UserGuide/using-regions-availability-zones.html

    // adjust region to "your" default region
    private static Region DEFAULT_REGION = Region.EU_CENTRAL_1;

    // environment var "REGION" can be used to override DEFAULT_REGION
    private static final String ENV_VAR_REGION = "REGION";

    public static Region getDefaultRegion() {
        String region = Environment.getEnvVarAsString(ENV_VAR_REGION, DEFAULT_REGION.toString());
        return Region.of(region);
    }


}
