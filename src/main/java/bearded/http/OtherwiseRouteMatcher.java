package bearded.http;

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
    public void apply(HttpExchange httpExchange) throws IOException {
        httpFunction.apply(httpExchange);
    }

}
