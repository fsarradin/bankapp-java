package bearded;

import bearded.bank.BankConnection;
import bearded.bank.BankService;
import bearded.http.MyHttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static bearded.http.Route.inCaseOf;
import static bearded.http.Route.otherwise;

public class WebBankMain {

    public static void main(String[] args) throws Exception {
        BankService bankService = BankService.createBankService(BankConnection.getBankAccessors());

        MyHttpServer.serve(8080, "/").withRoute(

                inCaseOf("banks", bankService::getBalanceByBank),
                inCaseOf("total", bankService::getTotalBalance),
                inCaseOf("principal", bankService::getPrincipalBalance),

                otherwise(WebBankMain::notFound404)

        );
    }

    private static String notFound404(HttpExchange exchange) throws IOException {
        return exchange.getRequestURI().getPath() + " not found";
    }

}
