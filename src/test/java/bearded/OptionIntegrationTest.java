package bearded;

import org.junit.Test;

import java.util.function.Supplier;

import static bearded.Option.Some;
import static org.fest.assertions.api.Assertions.assertThat;

public class OptionIntegrationTest {

    @Test
    public void should_add_two_numbers() throws Exception {
        Supplier<Option<Integer>> service1 = () -> Some(42);
        Supplier<Option<Integer>> service2 = () -> Some(24);

        Option<Integer> result = add(service1.get(), service2.get());

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get()).isEqualTo(66);
    }

    @Test
    public void should_add_a_number_and_None() throws Exception {
        Supplier<Option<Integer>> service1 = () -> Some(42);
        Supplier<Option<Integer>> service2 = () -> Option.None();

        Option<Integer> result = add(service1.get(), service2.get());

        assertThat(result.isEmpty()).isTrue();
    }

    private Option<Integer> add(Option<Integer> number1, Option<Integer> number2) {
        return number1.flatMap(n1 ->
            number2.map(n2 -> n1 + n2));
    }
}
