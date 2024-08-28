import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

object AircraftStreamAnalytics {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("AircraftStreamAnalytics")
      .getOrCreate()

    // Read from Kafka
    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "aircraft-states")
      .load()

    // Kafka messages are in a binary format, convert them to String
    val flightDF = kafkaDF.selectExpr("CAST(value AS STRING)")
    val parsedDF = flightDF.select(from_json(col("value"), schema).as("data")).select("data.*")

    // Display the DataFrame to the console
    val query = parsedDF.writeStream
      .format("console")
      .outputMode("append") // Use "append" mode to only show new rows
      .trigger(Trigger.ProcessingTime("1 minutes"))
      .start()

    query.awaitTermination()
  }
  
  // Define schema based on the data you're expecting
  val schema = new org.apache.spark.sql.types.StructType()
    .add("icao24", org.apache.spark.sql.types.StringType)
    .add("callsign", org.apache.spark.sql.types.StringType)
    .add("originCountry", org.apache.spark.sql.types.StringType)
    .add("lastUpdate", org.apache.spark.sql.types.LongType)
    .add("firstSeen", org.apache.spark.sql.types.LongType)
    .add("latitude", org.apache.spark.sql.types.DoubleType)
    .add("longitude", org.apache.spark.sql.types.DoubleType)
    .add("barometricAltitude", org.apache.spark.sql.types.DoubleType)
    .add("onGround", org.apache.spark.sql.types.BooleanType)
    .add("velocity", org.apache.spark.sql.types.DoubleType)
    .add("trueTrack", org.apache.spark.sql.types.DoubleType)
    .add("verticalRate", org.apache.spark.sql.types.DoubleType)
    .add("additionalInfo", org.apache.spark.sql.types.StringType)
    .add("geometricAltitude", org.apache.spark.sql.types.DoubleType)
    .add("squawkCode", org.apache.spark.sql.types.StringType)
    .add("emergency", org.apache.spark.sql.types.BooleanType)
    .add("reserved", org.apache.spark.sql.types.IntegerType)
}