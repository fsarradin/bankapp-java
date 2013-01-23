package bearded.monad;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.fest.assertions.api.Assertions.assertThat;

public class OptionTest {

    @Test(expected = NoSuchElementException.class)
    public void should_complain_when_get_from_None() {
        Option.None().get();
    }

    @Test
    public void should_have_element_when_get_from_Some() {
        assertThat(Option.Some(1).get()).isEqualTo(1);
    }

    @Test
    public void should_get_new_value_when_map_on_Some() {
        assertThat(Option.Some(1).map(x -> x + 1).get()).isEqualTo(2);
    }

    @Test
    public void should_get_none_when_map_on_None() {
        Option<Integer> integerNone = Option.None();

        assertThat(integerNone.map(x -> x + 1)).isEqualTo(integerNone);
    }

    @Test
    public void should_get_new_value_when_flatMap_on_Some() {
        assertThat(Option.Some(1).flatMap(x -> Option.Some(x + 1)).get()).isEqualTo(2);
    }

    @Test
    public void should_get_none_when_flatMap_on_Some_and_function_get_None() {
        Option<Integer> integerNone = Option.None();

        assertThat(Option.Some(1).flatMap(x -> integerNone)).isEqualTo(integerNone);
    }

    @Test
    public void should_get_none_when_flatMap_on_None() {
        Option<Integer> integerNone = Option.None();

        assertThat(integerNone.flatMap(x -> Option.Some(x + 1))).isEqualTo(integerNone);
    }

    @Test
    public void should_iterate_over_Some() throws Exception {
        Option<Integer> integerOption = Option.Some(1);

        List<Integer> values = new ArrayList<>();
        for (Integer value : integerOption) {
            values.add(value);
        }

        assertThat(values).containsExactly(1);
    }

    @Test
    public void should_iterate_over_None() throws Exception {
        Option<Integer> integerOption = Option.None();

        List<Integer> values = new ArrayList<>();
        for (Integer value : integerOption) {
            values.add(value);
        }

        assertThat(values).isEmpty();
    }

}
