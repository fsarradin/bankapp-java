package bearded.monad;

import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class Validation<S, F> {

    private Validation() {}

    public abstract S get();

    public abstract F getError();

    public abstract boolean isSuccess();

    public abstract  <U> Validation<U, F> flatMap(Function<S, Validation<U, F>> f);

    public <U> Validation<U, F> map(Function<S, U> f) {
        return flatMap(x -> Validation.<U, F>Success(f.apply(get())));
    }

    public static <S, F> Validation<S, F> Success(S value) {
        return new Validation<S, F>() {

            @Override
            public S get() {
                return value;
            }

            @Override
            public F getError() {
                throw new NoSuchElementException();
            }

            @Override
            public boolean isSuccess() {
                return true;
            }

            @Override
            public <U> Validation<U, F> flatMap(Function<S, Validation<U, F>> f) {
                return f.apply(get());
            }

        };
    }

    public static <S, F> Validation<S, F> Failure(F error) {
        return new Validation<S, F>() {

            @Override
            public S get() {
                throw new NoSuchElementException();
            }

            @Override
            public F getError() {
                return error;
            }

            @Override
            public boolean isSuccess() {
                return false;
            }

            @Override
            public <U> Validation<U, F> flatMap(Function<S, Validation<U, F>> f) {
                return Failure(getError());
            }

        };
    }

}
