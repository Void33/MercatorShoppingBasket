package com.steveedmans.model

import com.steveedmans.offers.ApplesBuyOneGetOneFreeOffer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*

class ShopTest extends AnyWordSpec with should.Matchers {
  private val shop = Shop()

  "The shop" should {
    "sell oranges" in {
      val item = shop.items.get("orange")
      item shouldBe defined
      item.get.name shouldBe "orange"
      item.get.cost shouldBe 25
    }

    "sell apples" in {
      val item = shop.items.get("apple")
      item shouldBe defined
      item.get.name shouldBe "apple"
      item.get.cost shouldBe 60
    }

    "have a BOGOF offer for apples" in {
      shop.offers.count(offer => offer.name == "ApplesBOGOF") shouldBe 1
    }

    "have a 3 for 2 offer for oranges" in {
      shop.offers.count(offer => offer.name == "Oranges3For2") shouldBe 1
    }
  }
}
