package com.steveedmans.offers

import com.steveedmans.model._
import scala.collection.mutable

/**
 * Replace two apples items with this offer at half the cost
 */
class ApplesBuyOneGetOneFreeOffer extends Offer {
  override def process(contents: mutable.Map[String, (Purchasable, Int)]):
    mutable.Map[String, (Purchasable, Int)] = {
    contents.flatMap {
      case ("apple", (apple, count)) =>
        val apples = if (count % 2 == 1) {
          List(apple.name -> (apple, 1))
        } else {
          List()
        }
        val offers = if (count > 1) {
          List(name -> (this, count / 2))
        } else {
          List()
        }
        apples ++ offers
      case notApples => List(notApples)
    }
  }

  override val name: String = "ApplesBOGOF"
  override val cost: Int = 60
  override val count: Int = 2
}
