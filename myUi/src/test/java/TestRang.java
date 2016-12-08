import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class TestRang {

	public static void main(String[] args) {
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "5");
		ArrayList list = new ArrayList();
		list.add("aaaa");
		list.add("bbbb");
		list.add("ccccc");
		list.add("dddd");
		list.add("dddd");
		list.add("dddd");
		list.add("dddd");
		list.add("xxxxx");
		list.add("xxxxx");
		list.add("zzzzz");
		ArrayDeque<Object> queue = new ArrayDeque<Object>();
		queue.addAll(list);
		// queue.pollFirst()

		Integer i = 0;
		IntStream.range(0, list.size()).parallel().forEach(o -> {
			System.out.println(Thread.currentThread().getId());
			System.out.println(queue.pollFirst());
			try {
				Thread.sleep(10_000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

}
