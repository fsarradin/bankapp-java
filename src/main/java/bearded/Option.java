package bearded;

import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class Option<T> {

    private Option() {}

    public abstract boolean isEmpty();

    public abstract T get();

    public abstract <U> Option<U> flatMap(Function<T, Option<U>> f);

    public <U> Option<U> map(Function<T, U> f) {
        return flatMap(x -> Some(f.apply(get())));
    }

    public static <U> Option<U> Some(U value) {
        return new Option<U>() {
            @Override
            public U get() {
                return value;
            }

            @Override
            public <U1 extends Object> Option<U1> flatMap(Function<U, Option<U1>> f) {
                return  f.apply(value);
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

        };
    }

    public static <U> Option<U> None() {
        return (Option<U>) NONE;
    }


    private static final Option<?> NONE = new Option<Object>() {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Object get() {
            throw new NoSuchElementException();
        }

        @Override
        public <U> Option<U> flatMap(Function<Object, Option<U>> f) {
            return None();
        }
    };
}