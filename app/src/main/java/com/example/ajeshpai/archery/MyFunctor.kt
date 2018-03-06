package com.example.ajeshpai.archery

import arrow.HK
import arrow.core.*
import arrow.typeclasses.Eq
import arrow.typeclasses.Functor
import arrow.typeclasses.Monad


class Myeq: Monad<MyClass> {
    override fun <A, B> flatMap(fa: HK<MyClass, A>, f: (A) -> HK<MyClass, B>): HK<MyClass, B> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <A> pure(a: A): HK<MyClass, A> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <A, B> tailRecM(a: A, f: (A) -> HK<MyClass, Either<A, B>>): HK<MyClass, B> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class Myeq1:EqInterface<MyClass2>{
    override fun eqv(a: MyClass2, b: MyClass2): Boolean {
        return a.id==b.id
    }

}