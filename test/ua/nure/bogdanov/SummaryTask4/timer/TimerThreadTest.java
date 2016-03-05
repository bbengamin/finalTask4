package ua.nure.bogdanov.SummaryTask4.timer;

import org.junit.Assert;
import org.junit.Test;

public class TimerThreadTest {
	@Test
	public void testConstructor1() throws InterruptedException {
		TimerThread t = new TimerThread(5L, 5L, 5);
		t.setDaemon(true);
		t.start();
		Thread.sleep(2000);
		t.interrupt();
	}

	@Test
	public void testGetters() {
		TimerThread t = new TimerThread(5L, 5L, 5);
		boolean result = true;
		result = t.getTime().compareTo("05:00") == 0;
		result = t.getIdTest() == 5L;
		result = t.getIdUser() == 5L;

		Assert.assertTrue(result);
	}
}
