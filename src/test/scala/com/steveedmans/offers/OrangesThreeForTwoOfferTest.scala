package com.steveedmans.offers

import com.steveedmans.model._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.*
import scala.collection.mutable

class OrangesThreeForTwoOfferTest extends AnyWordSpec with should.Matchers {
  private val shop = Shop()
  private val orange = shop.items("orange")
  private val apple = shop.items("apple")
  private val expectedOfferName = "Oranges3For2"

  private val offer = OrangesThreeForTwoOffer()

  "Buy One Get One Free for apples" should {
    "be named" in {
      offer.name shouldBe expectedOfferName
    }

    "cost the same two oranges" in {
      offer.cost shouldBe orange.cost * 2
    }

    "Leave an empty basket unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)]()
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "Leave a basket just containing apples unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 3)
      )
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "Leave a basket with apples and a single orange unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 3),
        "orange" -> (orange, 1)
      )
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "Leave a basket with apples and two oranges unchanged" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "apple" -> (apple, 3),
        "orange" -> (orange, 2)
      )
      val processed = offer.process(contents)
      processed shouldBe contents
    }

    "convert 3 oranges to one instance of the offer and no oranges" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 3)
      )
      val processed = offer.process(contents)
      processed.get("orange") shouldBe empty
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 1
        case _ => fail("Not found")
      }
    }

    "convert 4 oranges to one instance of the offer and one orange" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 4)
      )
      val processed = offer.process(contents)
      processed.get("orange") shouldBe Some((orange, 1))
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 1
        case _ => fail("Not found")
      }
    }

    "convert 5 oranges to one instance of the offer and two orange" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 5)
      )
      val processed = offer.process(contents)
      processed.get("orange") shouldBe Some((orange, 2))
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 1
        case _ => fail("Not found")
      }
    }

    "convert 6 oranges to two instance of the offer and no oranges" in {
      val contents = mutable.Map[String, (Purchasable, Int)](
        "orange" -> (orange, 6)
      )
      val processed = offer.process(contents)
      processed.get("orange") shouldBe empty
      processed.get(expectedOfferName) match {
        case Some((item, count)) =>
          item.name shouldBe expectedOfferName
          count shouldBe 2
        case _ => fail("Not found")
      }
    }
  }
}
