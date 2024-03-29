package com.steveedmans.model

/**
 * The details of an item that can be sold
 * @param name The name of the item
 * @param cost The cost of the item, in pence (this is to avoid the complication of rounding).
 */
case class Item(name: String, cost: Int) extends Purchasable:
  // A single item only ever has one item in it
  override val count: Int = 1
