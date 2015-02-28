package net.hamnaberg.funclite;

public class OptionalOps {

    public static <A> com.google.common.base.Optional<A> toGuava(Optional<A> opt) {
        return com.google.common.base.Optional.fromNullable(opt.orNull());
    }

    public static <A> Optional<A> fromGuava(com.google.common.base.Optional<A> opt) {
        return Optional.fromNullable(opt.orNull());
    }

    public static <A> java.util.Optional<A> toJavaOptional(Optional<A> opt) {
        return java.util.Optional.ofNullable(opt.orNull());
    }

    public static <A> Optional<A> fromJavaOptional(java.util.Optional<A> opt) {
        return Optional.fromNullable(opt.orElse(null));
    }


}
