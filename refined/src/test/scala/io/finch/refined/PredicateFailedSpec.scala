package io.finch.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._
import io.finch._

class PredicateFailedSpec extends FinchSpec {
  it should "return error with predicate failure information" in {
    val endpoint = get(param[Int Refined Positive]("int")) { (i: Int Refined Positive) =>
      Ok(i.value)
    }

    inside(endpoint(Input.get("/?int=-1")).awaitValue(dispatcherIO)) { case Some(Left(result)) =>
      result.getCause shouldBe a[PredicateFailed]
    }
  }
}
