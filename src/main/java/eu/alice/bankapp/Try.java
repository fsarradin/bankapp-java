package eu.alice.bankapp;

import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class Try<S> {

    public abstract S get();

    public abstract Throwable getError();

    public abstract boolean isSuccess();

    public abstract  <U> Try<U> flatMap(Function<S, Try<U>> f);

    public <U> Try<U> map(Function<S, U> f) {
        return flatMap(x -> Success(f.apply(get())));
    }

    public static <S> Try<S> Success(S value) {
        return new Try<S>() {

            @Override
            public S get() {
                return value;
            }

            @Override
            public Throwable getError() {
                throw new NoSuchElementException();
            }

            @Override
            public boolean isSuccess() {
                return true;
            }

            @Override
            public <U> Try<U> flatMap(Function<S, Try<U>> f) {
                return f.apply(get());
            }

        };
    }

    public static <S> Try<S> Failure(Throwable error) {
        return new Try<S>() {

            @Override
            public S get() {
                throw new NoSuchElementException();
            }

            @Override
            public Throwable getError() {
                return error;
            }

            @Override
            public boolean isSuccess() {
                return false;
            }

            @Override
            public <U> Try<U> flatMap(Function<S, Try<U>> f) {
                return Failure(getError());
            }

        };
    }

}
