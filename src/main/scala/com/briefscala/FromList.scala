package com.briefscala

import shapeless.{Generic, HList}
import shapeless.ops.traversable.FromTraversable

import scala.collection.GenTraversable

class FromList[T] {
  def apply[R <:HList](l: GenTraversable[_])(
    implicit gen: Generic.Aux[T, R], tl: FromTraversable[R])
  : Option[T] = tl(l).map(gen.from)
}
