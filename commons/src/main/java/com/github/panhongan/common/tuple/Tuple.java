package com.github.panhongan.common.tuple;

public class Tuple {

    public static class Tuple2<T1, T2> {
        public T1 _1;
        public T2 _2;

        public static <T1, T2> Tuple2 of(T1 t1, T2 t2) {
            Tuple2 tuple = new Tuple2();
            tuple._1 = t1;
            tuple._2 = t2;
            return tuple;
        }

        @Override
        public String toString() {
            return "(" + _1 + "," + _2 + ")";
        }
    }

    public static class Tuple3<T1, T2, T3> {
        public T1 _1;
        public T2 _2;
        public T3 _3;

        public static <T1, T2, T3> Tuple3 of(T1 t1, T2 t2, T3 t3) {
            Tuple3 tuple = new Tuple3();
            tuple._1 = t1;
            tuple._2 = t2;
            tuple._3 = t3;
            return tuple;
        }

        @Override
        public String toString() {
            return "(" + _1 + "," + _2 + "," + _3 + ")";
        }
    }
}
