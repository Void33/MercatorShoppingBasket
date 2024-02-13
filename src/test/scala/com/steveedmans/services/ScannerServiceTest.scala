package com.steveedmans.services

import com.steveedmans.model.*
import com.steveedmans.offers._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*

class ScannerServiceTest extends AnyWordSpec with should.Matchers {
  private val shop = Shop()
  private val orange = shop.items("orange")
  private val apple = shop.items("apple")
  private val service = ScannerService()

  "The scanner service" when {
    "determining contents of the basket" should {
      "find an apple" in {
        service.determineContents("apple").getOrElse(fail("either was not Right!")) should contain (apple)
      }

      "find an Apple" in {
        service.determineContents("Apple").getOrElse(fail("either was not Right!")) should contain(apple)
      }

      "find an orange" in {
        service.determineContents("orange").getOrElse(fail("either was not Right!")) should contain(orange)
      }

      "find an Orange" in {
        service.determineContents("Orange").getOrElse(fail("either was not Right!")) should contain(orange)
      }

      "find a collection of items" in {
        val contents = service.determineContents("Apple Apple Orange Apple").getOrElse(fail("either was not Right!"))
        contents.size shouldBe 4
        contents.count(item => item === apple) shouldBe 3
        contents.count(item => item === orange) shouldBe 1
      }

      "fail to process a basket with an unknown item" in {
        service.determineContents("apple other orange").isLeft shouldBe true
      }
    }

    "add required items to a basket" should {
      "add an apple to the basket" in {
        val contents = List(apple)
        val basket = service.createBasket(contents)
        basket.size shouldBe 1
        basket.cost shouldBe 60
      }

      "add an orange to the basket" in {
        val contents = List(orange)
        val basket = service.createBasket(contents)
        basket.size shouldBe 1
        basket.cost shouldBe 25
      }

      "add a collection of items to the basket" in {
        val contents = List(apple, apple, orange, apple)
        val basket = service.createBasket(contents)
        basket.size shouldBe 4
        basket.cost shouldBe 205
      }
    }

    "apply the offers to a basket" should {
      "use the BOGOF apples offer" in {
        val basket = Basket()
        basket.add(apple)
        basket.add(apple)

        basket.size shouldBe 2
        basket.cost shouldBe 120

        val finalBasket = service.applyOffers(basket)

        finalBasket.size shouldBe 2
        finalBasket.cost shouldBe 60
      }

      "use the 3 for 2 oranges offer" in {
        val basket = Basket()
        basket.add(orange)
        basket.add(orange)
        basket.add(orange)

        basket.size shouldBe 3
        basket.cost shouldBe 75

        val finalBasket = service.applyOffers(basket)

        finalBasket.size shouldBe 3
        finalBasket.cost shouldBe 50
      }
    }

    "process the required items" should {
      "find an apple" in {
        val basket = service.processBasket("apple").getOrElse(fail("either was not Right!"))
        basket.size shouldBe 1
        basket.cost shouldBe 60
      }

      "find an orange" in {
        val basket = service.processBasket("orange").getOrElse(fail("either was not Right!"))
        basket.size shouldBe 1
        basket.cost shouldBe 25
      }

      "find a collection of items" in {
        // Should apply the BOGOF offer for apples
        val basket = service.processBasket("apple apple orange apple").getOrElse(fail("either was not Right!"))
        basket.size shouldBe 4
        basket.cost shouldBe 145
      }

      "find a larger collection of items" in {
        // Should apply both offers
        val basket = service.processBasket("apple apple orange apple apple orange apple orange").getOrElse(fail("either was not Right!"))
        basket.size shouldBe 8
        basket.cost shouldBe 230
      }

      "handle an empty basket" in {
        service.processBasket("").isLeft
      }

      "handle a basket with an unknown item" in {
        service.processBasket("apple apple orange chocolate apple").isLeft
      }
    }
  }
}
