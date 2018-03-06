package com.example.ajeshpai.archery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import arrow.core.Option
import arrow.core.functor
import arrow.core.getOrElse
import arrow.core.toT
import arrow.instances.IntEqInstance
import arrow.instances.StringEqInstance
import arrow.optics.Iso
import arrow.optics.Lens
import arrow.optics.Optional
import arrow.optics.Prism
import arrow.optics.instances.listHead
import arrow.syntax.either.left
import arrow.syntax.either.right
import arrow.syntax.functor.map
import arrow.typeclasses.Eq

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var functior= Option(1).map { it * 2 }
        val optionFunctor = Option.functor()
        val x=g(1).map { f(it) }
    }

    fun g(parameter: Int):Option<Int>{
        return Option(parameter/1)
    }

    fun f(parameter:Int): Int {
        return parameter/2
    }

    fun opticIso() {
        val point: Iso<Point, PointReverse> = Iso(
                get = { point -> PointReverse(point.x, point.y) },
                reverseGet = { pointeReverse -> Point(pointeReverse.x, pointeReverse.y) }
        )
    }

    fun lensIso(){
        val fooLens: Lens<Point, String> = Lens(
                get = { point -> point.x },
                set = { value -> { foo -> foo.copy(x= value) } }
        )
    }

    fun optionalIso(){
        val optionalHead: Optional<List<Int>, Int> = Optional(
                getOrModify = { list -> list.firstOrNull()?.right() ?: list.left() },
                set = { int -> { list -> list.mapIndexed { index, value -> if (index == 0) int else value } } }
        )
    }

    sealed class NetworkResult {
        data class Success(val content: String): NetworkResult()
        object Failure: NetworkResult()
    }

    val networkSuccessPrism: Prism<NetworkResult, NetworkResult.Success> = Prism(
            getOrModify = { networkResult ->
                when(networkResult) {
                    is NetworkResult.Success -> networkResult.right()
                    else -> networkResult.left()
                }
            },
            reverseGet = { networkResult -> networkResult } //::identity
    )
}
    data class Point(val x:String,val y:String)

    data class PointReverse(val x:String,val y:String)
