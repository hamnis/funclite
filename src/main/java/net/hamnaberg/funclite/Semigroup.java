package net.hamnaberg.funclite;

public interface Semigroup<A> {
    A append(A a, A b);
}
