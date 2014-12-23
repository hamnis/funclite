package net.hamnaberg.funclite;

import java.util.ArrayList;
import java.util.List;

public class Monoids {
    public static <A> Monoid<List<A>> listMonoid() {
        return new Monoid<List<A>>() {
            @Override
            public List<A> zero() {
                return new ArrayList<>();
            }

            @Override
            public List<A> append(List<A> as, List<A> b) {
                ArrayList<A> list = new ArrayList<>(as);
                list.addAll(b);
                return list;
            }
        };
    }


    public static Monoid<Integer> intPlusMonoid() {
        return new Monoid<Integer>() {
            @Override
            public Integer zero() {
                return 0;
            }

            @Override
            public Integer append(Integer a, Integer b) {
                return a + b;
            }
        };
    }

    public static Monoid<Integer> intProductMonoid() {
        return new Monoid<Integer>() {
            @Override
            public Integer zero() {
                return 1;
            }

            @Override
            public Integer append(Integer a, Integer b) {
                return a * b;
            }
        };
    }

    public static Monoid<Long> longProductMonoid() {
        return new Monoid<Long>() {
            @Override
            public Long zero() {
                return 1L;
            }

            @Override
            public Long append(Long a, Long b) {
                return a * b;
            }
        };
    }

    public static Monoid<Long> longPlusMonoid() {
        return new Monoid<Long>() {
            @Override
            public Long zero() {
                return 0L;
            }

            @Override
            public Long append(Long a, Long b) {
                return a + b;
            }
        };
    }

}
