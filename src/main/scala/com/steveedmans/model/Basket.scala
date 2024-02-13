package com.steveedmans.model

import scala.collection.mutable

/**
 * The shopping basket containing the items being bought.
 */
class Basket {
  // For the sake of simplicity I am using a mutable map, but in a real world
  // application I would expect the items in the basket to be stored in some
  // form of database.
  private[model] var items: mutable.Map[String, (Item, Int)] = mutable.Map()

  /**
   * The number of individual items in the basket
   * @return the total number of items in the basket, not a count of the type of items.
   */
  def size: Int = items.values.map((_, count) => count).sum

  /**
   * Add a new item to the basket
   * @param item the item to be added
   */
  def add(item: Item): Unit = {
    items.get(item.name) match {
      case Some(item, count) => items(item.name) = (item, count + 1)
      case _ => items(item.name) = (item, 1)
    }
  }

  /**
   * The cost of the items in the basket
   * @return the total cost of the items in the basket, in pence.
   */
  def cost: Int = {
    items.values.map((item, count) => item.cost * count).sum
  }
}
