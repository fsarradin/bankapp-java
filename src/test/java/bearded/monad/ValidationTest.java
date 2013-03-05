package bearded.monad;

import bearded.monad.Validation;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.fest.assertions.api.Assertions.assertThat;

public class ValidationTest {

    @Test
    public void should_is_success_when_success() throws Exception {
        Validation<Integer, ?> success = Validation.Success(1);

        assertThat(success.isSuccess()).isTrue();
    }

    @Test
    public void should_not_is_success_when_failure() throws Exception {
        Validation<?, String> failure = Validation.Failure("error");

        assertThat(failure.isSuccess()).isFalse();
    }

    @Test
    public void should_get_value_when_success() throws Exception {
        Validation<Integer, ?> success = Validation.Success(1);

        assertThat(success.get()).isEqualTo(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void should_complain_when_getting_value_on_failure() throws Exception {
        Validation<?, String> failure = Validation.Failure("error");

        failure.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void should_complain_when_getting_error_on_success() throws Exception {
        Validation<Integer, ?> success = Validation.Success(1);

        success.getError();
    }

    @Test
    public void should_get_error_when_failure() throws Exception {
        Validation<?, String> failure = Validation.Failure("error");

        assertThat(failure.getError()).isEqualTo("error");
    }

    @Test
    public void should_get_success_when_map_on_success() throws Exception {
        Validation<Integer, ?> result = Validation.Success(1).map(v -> v + 1);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo(2);
    }

    @Test
    public void should_get_failure_when_map_on_failure() throws Exception {
        Validation<Integer, String> result = Validation.<Integer, String>Failure("error").map(v -> v + 1);

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo("error");
    }

    @Test
    public void should_get_success_when_flatMap_on_success() throws Exception {
        Validation<Integer, ?> result = Validation.Success(1).flatMap(v -> Validation.<Integer, Object>Success(v + 1));

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo(2);
    }

    @Test
    public void should_get_failure_when_flatMap_on_failure() throws Exception {
        Validation<?, String> result = Validation.Failure("error1").map(v -> Validation.Failure("error2"));

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo("error1");
    }

    @Test
    public void should_get_failure_when_flatMap_failure_on_success() throws Exception {
        Validation<Integer, String> result = Validation.<Integer, String>Success(1).flatMap(o -> Validation.<Integer, String>Failure("error"));

        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo("error");
    }

}
