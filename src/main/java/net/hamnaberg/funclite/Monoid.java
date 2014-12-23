package net.hamnaberg.funclite;

public interface Monoid<A> extends Semigroup<A> {
    A zero();
}
