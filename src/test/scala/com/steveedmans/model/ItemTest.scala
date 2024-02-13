package com.steveedmans.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*

class ItemTest extends AnyWordSpec with should.Matchers {

  "an item" should {
    "have a name" in {
      val product = new Item("orange", 25)
      product.name shouldBe "orange"
    }

    "have a price" in {
      val product = new Item("orange", 25)
      product.cost shouldBe 25
    }
  }
}
