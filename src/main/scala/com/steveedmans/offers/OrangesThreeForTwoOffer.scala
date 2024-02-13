package com.steveedmans.offers

import com.steveedmans.model._
import scala.collection.mutable

/**
 * Only charge the cost of 2 oranges for every 3 oranges in the basket.
 */
class OrangesThreeForTwoOffer extends Offer {
  override def process(contents: mutable.Map[String, (Purchasable, Int)])
    : mutable.Map[String, (Purchasable, Int)] = {
    contents.flatMap {
      case ("orange", (orange, count)) =>
        val oranges = if (count % 3 != 0) {
          List(orange.name -> (orange, count % 3))
        } else {
          List()
        }
        val offers = if (count > 2) {
          List(name -> (this, count / 3))
        } else {
          List()
        }
        oranges ++ offers
      case notOranges => List(notOranges)
    }
  }

  override val name: String = "Oranges3For2"
  override val cost: Int = 50
  override val count: Int = 3
}
