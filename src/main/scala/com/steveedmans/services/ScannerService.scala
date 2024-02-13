package com.steveedmans.services

import com.steveedmans.model.{Basket, *}

class ScannerService {
  private val shop = Shop()

  /**
   * process a list of items to be bought.
   * @param contents A space separated list of items to be purchased
   * @return A shopping basket containing the items, if anything unexpected
   *         happens then an error message as a string is returned.
   */
  def processBasket(contents: String): Either[String, Basket] = {
    determineContents(contents) map createBasket
  }

  private[services] def determineContents(basketContents: String): Either[String, List[Item]] = {
    val items = basketContents.split(" ")
    val contents = items.toList.flatMap(item => shop.items.get(item.toLowerCase))
    if (items.length == contents.length) {
      Right(contents)
    } else {
      Left("Unknown items in the basket")
    }
  }

  private[services] def createBasket(contents: List[Item]): Basket = {
    val basket = Basket()
    contents.foreach(item => basket.add(item))
    basket
  }
}
