package bearded.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class PathRouteMatcher implements RouteMatcher {

    private final String path;

    private final HttpFunction httpFunction;

    public PathRouteMatcher(String path, HttpFunction httpFunction) {
        this.path = path;
        this.httpFunction = httpFunction;
    }

    @Override
    public boolean match(HttpExchange httpExchange) {
        String rootPath = httpExchange.getHttpContext().getPath();
        String httpPath = httpExchange.getRequestURI().getPath();
        httpPath = httpPath.substring(rootPath.length());
        if (httpPath.endsWith("/")) {
            httpPath = httpPath.substring(0, httpPath.length() - 1);
        }

        return path.equals(httpPath);
    }

    @Override
    public void apply(HttpExchange httpExchange) throws IOException {
        httpFunction.apply(httpExchange);
    }

}
