package com.steveedmans.services

import com.steveedmans.model.Shop
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
        val basket = service.processBasket("apple apple orange apple").getOrElse(fail("either was not Right!"))
        basket.size shouldBe 4
        basket.cost shouldBe 205
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
