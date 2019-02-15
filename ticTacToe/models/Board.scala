package ticTacToe.models

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//import scala.concurrent.duration._

class Board(rows: List[List[Int]] = List(
  List(-1, -1, -1),
  List(-1, -1, -1),
  List(-1, -1, -1))) {

  private val rows_ = rows

  def getColor(coordinate: Coordinate): Int = {

    def getColor(row: List[Int], column: Int): Int =
      row.zipWithIndex match {
        case (color, position) :: tail if position == column => color
        case (_, position) :: tail =>
          getColor(row.tail, column - 1)
      }

    def unzipWithoutIndex(list: List[(List[Int], Int)]): List[List[Int]] =
      list match {
        case Nil => Nil
        case (item, _) :: tail => item :: unzipWithoutIndex(tail)
      }

    rows_.zipWithIndex match {
      case (row, position) :: tail if position == coordinate.getRow =>
        getColor(row, coordinate.getColumn)
      case (_, _) :: tail =>
        new Board(
          unzipWithoutIndex(tail)).getColor(new Coordinate(coordinate.getRow - 1, coordinate.getColumn))
    }
  }

  def put(coordinate: Coordinate, player: Int): Board = {

    new Board(
      rows_.zipWithIndex.map {
        case (row, position) =>
          if (position != coordinate.getRow)
            row
          else
            row.zipWithIndex.map {
              case (color, position) =>
                if (position == coordinate.getColumn)
                  player
                else
                  color
            }
      })
  }

  def move(from: Coordinate, to: Coordinate): Board =
    this.put(from, -1).put(to, this.getColor(from))


  private def getCoordinates(player: Int): List[Coordinate] = {

    def getCoordinates2(player: Int, row: List[Int], fila: Int, columna: Int): List[Coordinate] = {
      row match {
        case Nil => Nil
        case head :: tail => if (head == player)
          new Coordinate(fila, columna) :: getCoordinates2(player, tail, fila, columna + 1)
        else
          getCoordinates2(player, tail, fila, columna + 1)
      }
    }

    def getCoordinates(player: Int, rows: List[List[Int]], fila: Int): List[Coordinate] = {
      rows match {
        case Nil => Nil
        case head :: tail =>
          getCoordinates2(player, head, fila, 0) ++ getCoordinates(player, tail, fila + 1)
      }
    }

    getCoordinates(player, rows_, 0)
  }

  def isComplete: Boolean = this.getCoordinates(-1).length == 3

  def isTicTacToe: Boolean = {

    def isTicTacToe(player: Int): Boolean = {

      def equals(strings: List[String]): Boolean =
        strings match {
          case Nil => true
          case _ :: Nil => true
          case first :: second :: tail if (first == second) => equals(second :: tail)
          case _ :: _ => false
        }

      def getDirections(coordinates: List[Coordinate]): List[String] = {
        coordinates match {
          case Nil => Nil
          case head :: Nil => Nil
          case first :: second :: tail => first.getDirection(second) :: getDirections(second :: tail)
        }
      }

      println ("board.isTicTacToe(param)")
      val coordinates = this.getCoordinates(player)
      val directions = getDirections(coordinates)
      coordinates.length == 3 && equals(directions) && !directions.contains("")
    }

    //isTicTacToe(0) || isTicTacToe(1)
    //Aqui es donde hay que meter Futures para hacerlo en paralelo

    //implicit val baseTime = System.currentTimeMillis
    println("board.isTicTacToe")
    val ticTacToePlayer0 = Future {
      val result = isTicTacToe(0)
      println(s"The result for player0 is $result")
      result
    }
    val ticTacToePlayer1 = Future {
      val result = isTicTacToe(1)
      println(s"The result for player1 is $result")
      result
    }

    val result = for {
      r1 <- ticTacToePlayer0
      r2 <- ticTacToePlayer1
    } yield (r1 || r2)

    Thread.sleep(50)

//    result.failed foreach {
//      case e => e.printStackTrace
//    }

    result.value match{
      case None => false
      case Some(a) => a.get
    }
  }

  override def equals(that: Any): Boolean =
    that match {
      case that: Board =>
        rows_ == that.rows_
      case _ => false
    }

  override def hashCode(): Int = {
    val state = Seq(rows_)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

}
