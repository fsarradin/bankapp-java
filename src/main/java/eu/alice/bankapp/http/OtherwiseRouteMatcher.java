package eu.alice.bankapp.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class OtherwiseRouteMatcher implements RouteMatcher {

    private final HttpFunction httpFunction;

    public OtherwiseRouteMatcher(HttpFunction httpFunction) {
        this.httpFunction = httpFunction;
    }

    @Override
    public boolean match(HttpExchange httpExchange) {
        return true;
    }

    @Override
    public String apply(HttpExchange httpExchange) throws IOException {
        return httpFunction.apply(httpExchange);
    }

}
