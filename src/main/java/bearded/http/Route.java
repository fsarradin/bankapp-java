package bearded.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Route {

    private List<RouteMatcher> routeMatchers;

    public Route(RouteMatcher... routeMatchers) {
        this.routeMatchers = Arrays.asList(routeMatchers);
    }

    public void match(HttpExchange httpExchange) throws IOException {
        for (RouteMatcher routeMatcher : routeMatchers) {
            if (routeMatcher.match(httpExchange)) {
                routeMatcher.apply(httpExchange);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    public static RouteMatcher inCaseOf(String path, HttpFunction httpFunction) {
        return new PathRouteMatcher(path, httpFunction);
    }

    public static RouteMatcher otherwise(HttpFunction httpFunction) {
        return new OtherwiseRouteMatcher(httpFunction);
    }

}
