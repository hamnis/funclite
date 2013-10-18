package net.hamnaberg.funclite;

public final class Predicates {
    private Predicates() {
    }

    public static <A> Predicate<A> not(final Predicate<A> p) {
        return new Predicate<A>() {
            @Override
            public boolean apply(A input) {
                return !p.apply(input);
            }
        };
    }


    public static <A> Predicate<A> and(final Predicate<A> p, final Predicate<A> p2) {
        return new Predicate<A>() {
            @Override
            public boolean apply(A input) {
                return p.apply(input) && p2.apply(input);
            }
        };
    }

    public static <A> Predicate<A> or(final Predicate<A> p, final Predicate<A> p2) {
        return new Predicate<A>() {
            @Override
            public boolean apply(A input) {
                return p.apply(input) || p2.apply(input);
            }
        };
    }


    @SuppressWarnings("unchecked")
    public static <A> Predicate<A> alwaysTrue() {
        return (Predicate<A>) TRUE;
    }

    @SuppressWarnings("unchecked")
    public static <A> Predicate<A> alwaysFalse() {
        return (Predicate<A>) FALSE;
    }

    public static Predicate<Integer> positive() {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input != null && input.intValue() > 0;
            }
        };
    }


    public static Predicate<Object> TRUE = new Predicate<Object>() {
        @Override
        public boolean apply(Object input) {
            return true;
        }
    };
    public static Predicate<Object> FALSE = new Predicate<Object>() {
        @Override
        public boolean apply(Object input) {
            return false;
        }
    };

}
