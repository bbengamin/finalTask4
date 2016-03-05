package ua.nure.bogdanov.SummaryTask4.timer;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class TimerThread extends Thread implements Serializable {

	private static final long serialVersionUID = 4916553911199256380L;

	private Long idUser;
	private Long idTest;
	private int minutes;
	private int seconds;
	private static final Logger LOG = Logger.getLogger("TimerThread");

	public TimerThread(Long idUser, Long idTest, int time) {
		this.idUser = idUser;
		this.idTest = idTest;
		this.minutes = time;
	}

	public Long getIdUser() {
		return idUser;
	}

	public Long getIdTest() {
		return idTest;
	}

	public String getTime() {
		return minutes + ":" + seconds;
	}

	@Override
	public void run() {
		LOG.trace(this.getName() + " was started");
		while (minutes >= 0 && seconds >= 0) {
			try {

				Thread.sleep(1000);
				seconds--;
				if (seconds <= 0) {
					minutes--;
					seconds = 60;
				}
			} catch (InterruptedException e) {
				break;
			}
		}
		LOG.trace(this.getName() + " was finished");
	}

}
