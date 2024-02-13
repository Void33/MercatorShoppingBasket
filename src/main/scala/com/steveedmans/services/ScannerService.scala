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
    determineContents(contents) map createBasket map applyOffers
  }

  private[services] def determineContents(basketContents: String): Either[String, List[Purchasable]] = {
    val items = basketContents.split(" ")
    val contents = items.toList.flatMap(item => shop.items.get(item.toLowerCase))
    if (items.length == contents.length) {
      Right(contents)
    } else {
      Left("Unknown items in the basket")
    }
  }

  /**
   * Apply all of the known offer to the basket.
   *
   * I am assuming that this only happens once and we do not need
   * to check to see if an offer is already being used in
   * the basket.
   *
   * @param basket The original basket
   * @return the basket after the offers have all been applied
   */
  private[services] def applyOffers(basket: Basket): Basket = {
    shop.offers.foreach { offer =>
      basket.processOffer(offer)
    }
    basket
  }

  private[services] def createBasket(contents: List[Purchasable]): Basket = {
    val basket = Basket()
    contents.foreach(item => basket.add(item))
    basket
  }
}
