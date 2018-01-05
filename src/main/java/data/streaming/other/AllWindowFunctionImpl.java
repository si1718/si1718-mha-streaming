package data.streaming.other;

import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class AllWindowFunctionImpl implements AllWindowFunction<String, String, TimeWindow> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5039985104495426823L;

	public void apply(TimeWindow window, Iterable<String> values, Collector<String> out) throws Exception {
		for(String s: values) {
			out.collect(s);
		}
		
	}

}
