package com.steveedmans

import com.steveedmans.services.ScannerService

/**
 * This is a very simple interface to the service. I would not
 * expect something this simple to be used in a production service.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val contents = args.mkString(" ")

    if (contents.nonEmpty) {
      val service = ScannerService()
      service.processBasket(contents) match {
        case Right(basket) =>
          val costInPounds: Double = basket.cost / 100.0
          println(s"There are ${basket.size} items in the basket which totals to £${costInPounds}")
        case Left(error) =>
          println(s"An error occurred processing the basket - $error")
      }
    } else {
      println("No items in the basket")
    }
  }
}
