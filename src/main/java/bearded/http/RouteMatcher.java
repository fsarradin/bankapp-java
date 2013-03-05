package bearded.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RouteMatcher {

    boolean match(HttpExchange httpExchange);

    String apply(HttpExchange httpExchange) throws IOException;

}
