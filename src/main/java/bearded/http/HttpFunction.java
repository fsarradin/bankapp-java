package bearded.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface HttpFunction {
    void apply(HttpExchange httpExchange) throws IOException;
}
