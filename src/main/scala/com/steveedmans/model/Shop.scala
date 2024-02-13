package com.steveedmans.model

import com.steveedmans.offers._

/**
 * The shop using the shopping basket.
 */
class Shop {
  /**
   * The items that can be purchased in the shop.
   */
  val items: Map[String, Purchasable] = Map[String, Purchasable](
    "orange" -> Item("orange", 25),
    "apple" -> Item("apple", 60)
  )

  /**
   * A list of the offers currently available.
   */
  val offers: List[Offer] = List(
    ApplesBuyOneGetOneFreeOffer(),
    OrangesThreeForTwoOffer()
  )
}
