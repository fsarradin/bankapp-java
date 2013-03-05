package bearded.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface HttpFunction {

    String apply(HttpExchange httpExchange) throws IOException;

}
