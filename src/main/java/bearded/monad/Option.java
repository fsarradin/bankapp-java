package bearded.monad;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class Option<T> implements Iterable<T> {

    private Option() {}

    public abstract T get();

    public abstract T getOrElse(T defaultValue);

    protected abstract List<T> toList();

    public abstract <U> Option<U> flatMap(Function<T, Option<U>> f);

    public <U> Option<U> map(Function<T, U> f) {
        return flatMap(x -> Some(f.apply(get())));
    }

    @Override
    public Iterator<T> iterator() {
        return toList().iterator();
    }

    public static <U> Option<U> Some(U value) {
        return new Option<U>() {
            @Override
            public U get() {
                return value;
            }

            @Override
            public U getOrElse(U defaultValue) {
                return value;
            }

            @Override
            protected List<U> toList() {
                return Collections.singletonList(value);
            }

            @Override
            public <V> Option<V> flatMap(Function<U, Option<V>> f) {
                return  f.apply(value);
            }

        };
    }

    public static <U> Option<U> None() {
        return (Option<U>) NONE;
    }


    private static final Option<?> NONE = new Option<Object>() {
        @Override
        public Object get() {
            throw new NoSuchElementException();
        }

        @Override
        public Object getOrElse(Object defaultValue) {
            return defaultValue;
        }

        @Override
        protected List<Object> toList() {
            return Collections.emptyList();
        }

        @Override
        public <U> Option<U> flatMap(Function<Object, Option<U>> f) {
            return None();
        }
    };
}