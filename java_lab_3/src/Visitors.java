import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Visitors implements Runnable {

	private int countVisitorsToMuseum;
	private int countOutVisitorsToMuseum;
	public PassEast museum;
	public PassWest museum2;
	public Director director;

	
	public Visitors (int countVisitorsToMuseum) {
		this.countVisitorsToMuseum=countVisitorsToMuseum;
	}
	
	public int getCountVisitorsToMuseum() {
		return countVisitorsToMuseum;
	}

	public int getCountOutVisitorsToMuseum() {
		return countOutVisitorsToMuseum;
	}

	public void setCountVisitorsToMuseum(int countVisitorsToMuseum) {
		this.countVisitorsToMuseum = countVisitorsToMuseum;
	}

	public void setCountOutVisitorsToMuseum(int countOutVisitorsToMuseum) {
		this.countOutVisitorsToMuseum = countOutVisitorsToMuseum;
	}

	public void generateVisitors()  {
		director.getDirectorLock().lock();
		try 
		{
			countVisitorsToMuseum = new Random().nextInt(30) + 1;
			System.out.println("Пришли посетители - " + countVisitorsToMuseum + " людей.");
			museum.setFlagOfTakeVisitors(true);
			director.getDirectorFunds().signalAll();
		}
		finally 
		{
			director.getDirectorLock().unlock();
		}
	}
	
	public void generateOutVisitors () {
		director.getDirectorLock().lock();
		try { 
			if ( museum.getInputPeople() > 1 ) {
				//System.out.println(" - " + museum.getInputPeople() );
			countOutVisitorsToMuseum = new Random().nextInt(museum.getInputPeople() / 3) +1;
			museum2.setTakeOutvisitors(true);
			}
			
		}
		finally {
			director.getDirectorLock().unlock();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			generateVisitors();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			generateOutVisitors();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
