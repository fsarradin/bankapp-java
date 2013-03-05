package bearded.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

public class AliceProperties {

    public static final String Alice = "Alice";

    public static final Multimap<String, String> ALICE_ACCOUNT_NUMBERS = ImmutableSetMultimap.<String, String>builder()
            .putAll("BGP", "CC-BGP-1", "CC-BGP-2", "CC-BGP-42")
            .putAll("La Postale", "CP-LPO-2")
            .putAll("Breizh Bank", "CC-BRB-3")
            .build();

    public static final String PRINCIPAL_BANK = "BGP";

    public static final String PRINCIPAL_ACCOUNT = "CC-BGP-1";

    public static Set<String> getBankNames() {
        return ALICE_ACCOUNT_NUMBERS.keySet();
    }

    public static Set<String> getAccountNumbersIn(String bankName) {
        return Sets.newHashSet(ALICE_ACCOUNT_NUMBERS.get(bankName));
    }

}
