package eu.alice.bankapp;

import eu.alice.bankapp.bank.BankConnection;
import eu.alice.bankapp.bank.BankService;
import eu.alice.bankapp.http.MyHttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static eu.alice.bankapp.http.Route.inCaseOf;
import static eu.alice.bankapp.http.Route.otherwise;

public class WebBankMain {

    public static void main(String[] args) throws Exception {
        BankService bankService = BankService.createBankService(BankConnection.getBankAccessors());

        MyHttpServer.serve(8080, "/").withRoute(

                inCaseOf("total", bankService::getTotalBalance),

                otherwise(WebBankMain::notFound404)

        );
    }

    private static String notFound404(HttpExchange exchange) throws IOException {
        return exchange.getRequestURI().getPath() + " not found";
    }

}
