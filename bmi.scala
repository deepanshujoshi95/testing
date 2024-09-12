import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

object demo11 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("merger")
      .master("local[*]")
      .getOrCreate()
    val dbUrl = "jdbc:mysql://localhost:3306/u4"
    val dbTable = "employee"
    val dbUser = "root"
    val salaryDF = spark.read
      .format("jdbc")
      .option("url", dbUrl)
      .option("dbtable", dbTable)
      .option("user", dbUser)
      //      .option("password", dbPassword)
      .load()

    import spark.implicits._
    val csvdf = spark.read.option("header", true).csv("C:/Users/DEEJOSHI/desktop/scala_usecase/use12.csv")
    val jsondf = spark.read.option("multiline", true).json("C:/Users/DEEJOSHI/desktop/scala_usecase/jsoncase2.json")
    val employeeDepartmentDF = csvdf
      .join(jsondf, "department_id")
    val employeeDepartmentSalaryDF = employeeDepartmentDF
      .join(salaryDF, "employee_id")
    val averageSalaryPerDepartment = employeeDepartmentSalaryDF
      .groupBy("department_id", "department_name")
      .agg(avg("salary").as("average_salary"))
    val highestAvgSalaryDept = averageSalaryPerDepartment
      .orderBy(desc("average_salary"))
      .first()
    val orderedAverageSalaryPerDepartment = averageSalaryPerDepartment
      .orderBy(desc("average_salary"))
    println("Department ID, Department Name, Average Salary")
    orderedAverageSalaryPerDepartment.foreach { row =>
      val departmentId = row.getAs[String]("department_id")
      val departmentName = row.getAs[String]("department_name")
      val averageSalary = row.getAs[Double]("average_salary")
      println(s"$departmentId, $departmentName, $averageSalary")
    }
  }
}
