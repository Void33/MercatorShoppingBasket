package com.steveedmans.offers

import com.steveedmans.model.{Purchasable, Shop}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*
import scala.collection.mutable

class ApplesBuyOneGetOneFreeOfferTest extends AnyWordSpec with should.Matchers {
  private val shop = Shop()
  private val orange = shop.items("orange")
  private val apple = shop.items("apple")
  private val expectedOfferName = "ApplesBOGOF"

  private val offer = ApplesBuyOneGetOneFreeOffer()

  "Buy One Get One Free for apples" should {
    "be named" in {
      offer.name shouldBe expectedOfferName
    }

    "cost the same a single apple" in {
      offer.cost shouldBe apple.cost
    }

    "Leave an empty basket unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)]()
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "Leave a basket just containing oranges unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 3)
      )
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "Leave a basket with oranges and a single apple unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 3),
        "apple" -> (apple, 1)
      )
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "convert 2 apples to one instance of the offer and no apples" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 2)
      )
      val processed = offer.process(contents)
      processed.get("apple") shouldBe empty
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 1
        case _ => fail("Not found")
      }
    }

    "convert 3 apples to one instance of the offer and one apple" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 3)
      )
      val processed = offer.process(contents)
      processed.get("apple") shouldBe Some((apple, 1))
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 1
        case _ => fail("Not found")
      }
    }

    "convert 6 apples to three instance of the offer and no apples" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 6)
      )
      val processed = offer.process(contents)
      processed.get("apple") shouldBe empty
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 3
        case _ => fail("Not found")
      }
    }
  }
}
