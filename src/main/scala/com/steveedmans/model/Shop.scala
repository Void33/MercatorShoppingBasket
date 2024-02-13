package com.steveedmans.model

/**
 * The shop using the shopping basket.
 */
class Shop {
  /**
   * The items that can be purchased in the shop.
   */
  val items: Map[String, Item] = Map[String, Item](
    "orange" -> Item("orange", 25),
    "apple" -> Item("apple", 60)
  )
}
