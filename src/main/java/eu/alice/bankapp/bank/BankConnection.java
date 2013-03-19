package eu.alice.bankapp.bank;

import com.google.common.collect.ImmutableMap;
import eu.alice.bankapp.entity.Account;

import java.util.Map;

import static eu.alice.bankapp.entity.AliceProperties.Alice;

public class BankConnection {

    public static Map<String, BankAccessor> getBankAccessors() {
        return ImmutableMap.<String, BankAccessor>builder()
                .put("BGP", new BankAccessor(ImmutableMap.<String, Account>builder()
                        .put("CC-BGP-1", new Account(Alice, 5000.0))
                        .put("CC-BGP-2", new Account(Alice, 5000.0))
                        .put("CC-BGP-42", new Account(Alice, 3000.0))
                        .build()
                ))

                .put("La Postale", new BankAccessor(ImmutableMap.<String, Account>builder()
                        .put("CP-LPO-2", new Account(Alice, 5000.0))
                        .build()
                ))

                .put("Breizh Bank", new BankAccessor(ImmutableMap.<String, Account>builder()
                        .put("CC-BRB-3", new Account(Alice, 5000.0))
                        .build()
                ))

                .build();

    }

}
