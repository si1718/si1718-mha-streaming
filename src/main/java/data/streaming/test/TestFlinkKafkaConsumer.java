package data.streaming.test;

import java.util.Properties;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import data.streaming.db.MongoUtils;
import data.streaming.other.AllWindowFunctionImpl;
import data.streaming.utils.LoggingFactory;
import data.streaming.utils.Utils;

public class TestFlinkKafkaConsumer {


	public static void main(String... args) throws Exception {
		MongoUtils.initialize();

		// set up the execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties props = LoggingFactory.getCloudKarafkaCredentials();


		env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

		DataStream<String> stream = env.addSource(new FlinkKafkaConsumer010<>(props.getProperty("CLOUDKARAFKA_TOPIC").trim(), new SimpleStringSchema(), props));
		//stream.print();
		stream.timeWindowAll(Time.seconds(10))
			.apply(new AllWindowFunctionImpl())
			.filter(x -> Utils.isValid(x))
			.map(x -> Utils.createTweetDTO(x))
			.filter(x -> x != null)
			.map(x -> Utils.createKeywordDTO(x))
			.filter(x -> x != null)
			.map(x -> MongoUtils.addKeyword(x))
			.print();

		// execute program
		env.execute("Twitter Streaming Consumer");
	}

}
