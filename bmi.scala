import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window
object demo2 extends App {
  val spark = SparkSession
    .builder()
    .master(master = "local")
    .appName(name="salary").getOrCreate()
  import spark.implicits._
  val dataDF=Seq((1, "a", "HR", 100),
    (2, "a", "sales", 200),
    (3, "b", "IT", 250),
    (4, "c", "HR", 150),
    (5, "d", "IT", 275),
    (6, "d", "IT", 350),
    (7, "d", "HR", 400),
    (8, "e", "HR", 175),
    (9, "f", "sales", 500),
    (10, "g", "sales", 450)).toDF(colNames = "ID","Name","Department","Salary")
  dataDF.show()

  val windowSpec = Window.partitionBy("Department").orderBy($"Salary".desc)
  val outdf = dataDF.withColumn("rank",dense_rank().over(windowSpec))
    .where($"rank" === 2)
    .select("Department","Salary")

  outdf.show()
}
