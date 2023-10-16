package acetoys;

import acetoys.simulation.TestPopulation;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AceToysSimulation extends Simulation {

    private static final String TEST_TYPE = System.getenv("TEST_TYPE");

    private static final String DOMAIN = "acetoys.uk";

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://" + DOMAIN)
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.9,ru-RU;q=0.8,ru;q=0.7,tr;q=0.6");


    {
        var testPopulation = switch (TEST_TYPE) {
            case "RAMP_USERS" -> TestPopulation.rampUsers;
            case "COMPLEX_INJECTION" -> TestPopulation.complexInjection;
            case "CLOSED_MODEL" -> TestPopulation.closedModel;
            default -> TestPopulation.instantUsers;
        };
        setUp(testPopulation).protocols(httpProtocol);
    }
}
