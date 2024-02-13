package com.steveedmans.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*

class BasketTest extends AnyWordSpec with should.Matchers {
  private val shop = Shop()
  private val orange = shop.items("orange")
  private val apple = shop.items("apple")

  "A Basket" should {
    "not contain any items when it is newly created" in {
      val basket = Basket()

      basket.size shouldBe 0
    }

    "allow adding an orange to an empty basket" in {
      val basket = Basket()

      basket.add(orange)

      basket.size shouldBe 1

      val orangesInBasket = basket.items.get("orange")
      orangesInBasket shouldBe defined
      orangesInBasket.get._1 shouldBe orange
      orangesInBasket.get._2 shouldBe 1
    }

    "allow adding a second orange to a basket" in {
      val basket = Basket()

      basket.add(orange)

      basket.size shouldBe 1

      basket.add(orange)

      basket.size shouldBe 2

      basket.cost shouldBe 50

      val orangesInBasket = basket.items.get("orange")
      orangesInBasket shouldBe defined
      orangesInBasket.get._1 shouldBe orange
      orangesInBasket.get._2 shouldBe 2
    }

    "allow adding an apple to an empty basket" in {
      val basket = Basket()

      basket.add(apple)

      basket.size shouldBe 1

      val applesInBasket = basket.items.get("apple")
      applesInBasket shouldBe defined
      applesInBasket.get._1 shouldBe apple
      applesInBasket.get._2 shouldBe 1
    }

    "allow adding a apple orange to a basket" in {
      val basket = Basket()

      basket.add(apple)

      basket.size shouldBe 1

      basket.add(apple)

      basket.size shouldBe 2

      basket.cost shouldBe 120

      val applesInBasket = basket.items.get("apple")
      applesInBasket shouldBe defined
      applesInBasket.get._1 shouldBe apple
      applesInBasket.get._2 shouldBe 2
    }

    "add an apple and an orange to the basket" in {
      val basket = Basket()

      basket.add(orange)
      basket.add(apple)

      basket.size shouldBe 2

      basket.cost shouldBe 85

      val applesInBasket = basket.items.get("apple")
      applesInBasket shouldBe defined
      applesInBasket.get._1 shouldBe apple
      applesInBasket.get._2 shouldBe 1

      val orangesInBasket = basket.items.get("orange")
      orangesInBasket shouldBe defined
      orangesInBasket.get._1 shouldBe orange
      orangesInBasket.get._2 shouldBe 1
    }
  }
}
