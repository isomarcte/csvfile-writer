import com.opencsv._
import java.io._
import scala.collection.JavaConverters._
import scala.util._

object Main {

  val header: List[String] =
    List("Serial Number", "Record Type", "First File value", "Second file value")

  val rows: List[List[String]] =
    List(
      List(
        List('1','2','3','4').toString, List("A","abhc","agch","mknk").toString, List("B", "gjgbn", "fgbhjf", "dfjf").toString
      )
    )

  def addPrefix(lls: List[List[String]]): List[List[String]] =
    lls.foldLeft((1, List.empty[List[String]])){
      case ((serial: Int, acc: List[List[String]]), value: List[String]) =>
        (serial + 1, (serial.toString +: value) +: acc)
    }._2.reverse

  def writeCsvFile(
    fileName: String,
    header: List[String],
    rows: List[List[String]]
  ): Try[Unit] =
    Try(new CSVWriter(new BufferedWriter(new FileWriter(fileName)))).flatMap((csvWriter: CSVWriter) =>
      Try{
        csvWriter.writeAll(
          (header +: rows).map(_.toArray).asJava
        )
        csvWriter.close()
      } match {
        case f @ Failure(_) =>
          // Always return the original failure.  In production code we might
          // define a new exception which wraps both exceptions in the case
          // they both fail, but that is omitted here.
          Try(csvWriter.close()).recoverWith{
            case _ => f
          }
        case success =>
          success
      }
    )

  def main(args: Array[String]): Unit = {
    val rs: List[List[String]] = rows.flatMap(r => (1 to 5).map(_ => r))
    println(writeCsvFile("/tmp/test.csv", header, addPrefix(rs)))
  }
}
