package com.steveedmans.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*

class ShopTest extends AnyWordSpec with should.Matchers {
  "The shop" should {
    "sell oranges" in {
      val shop = new Shop()

      val item = shop.items.get("orange")
      item shouldBe defined
      item.get.name shouldBe "orange"
      item.get.cost shouldBe 25
    }

    "sell apples" in {
      val shop = new Shop()

      val item = shop.items.get("apple")
      item shouldBe defined
      item.get.name shouldBe "apple"
      item.get.cost shouldBe 60
    }
  }
}
