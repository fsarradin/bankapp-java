package bearded.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MyHttpServer {

    public static void closeWith(HttpExchange exchange, int responseCode, String content) throws IOException {
        System.out.println(exchange.getRequestURI().getPath() +  " [" + responseCode + "]: " + content);

        String query = exchange.getRequestURI().getQuery();
        String body = (query != null) ? query.split("[&=]")[1] + "(" + content + ")" : content;

        byte[] response = body.getBytes(Charset.forName("UTF-8"));
        exchange.sendResponseHeaders(responseCode, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    public static RichHttpServer serve(int port, String root) {
        return new RichHttpServer(port, root);
    }

    public static class RichHttpServer {

        private final int port;

        private final String root;

        public RichHttpServer(int port, String root) {
            this.port = port;
            this.root = root;
        }

        public void with(Route route) throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext(root, httpExchange -> {
                try {
                    String content = route.match(httpExchange);

                    closeWith(httpExchange, 200, content);
                } catch (IOException e) {
                    e.printStackTrace();
                    closeWith(httpExchange, 500, e.getMessage());
                }
            });

            System.out.println("Server running on port " + port + "...");
            server.start();
        }

        public void withRoute(RouteMatcher... routeMatchers) throws IOException {
            with(new Route(routeMatchers));
        }

    }

}
