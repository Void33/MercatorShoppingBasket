package com.steveedmans.model

import scala.collection.mutable

trait Offer extends Purchasable {
  def process(contents: mutable.Map[String, (Purchasable, Int)]): mutable.Map[String, (Purchasable, Int)]
}
