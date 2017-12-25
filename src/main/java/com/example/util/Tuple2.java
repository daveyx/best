package com.example.util;

public class Tuple2<A, B>
{
    private final A a;
    private final B b;

    public Tuple2(final A a, final B b)
    {
        this.a = a;
        this.b = b;
    }

    public A getA()
    {
        return a;
    }

    public B getB()
    {
        return b;
    }

    @Override
    public String toString()
    {
        return "Tuple2 <a=" + a
               + ", b=" + b
               + '>';
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        @SuppressWarnings("rawtypes")
		final Tuple2 tuple2 = (Tuple2) o;

        if (a != null ? !a.equals(tuple2.a) : tuple2.a != null) {
            return false;
        }
        if (b != null ? !b.equals(tuple2.b) : tuple2.b != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
