import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum MuseumState {
	Open,
	Close;
}

public class Director implements Runnable{
	public MuseumState State;
	public Lock directorLock;
	public Condition directorFunds;
	public Controller controller;
	int count = 0;
	
	public Director (MuseumState State) {
		super();
		this.State = State;
		directorLock = new ReentrantLock();
		directorFunds = directorLock.newCondition();
		
	}
	
	
	public Lock getDirectorLock() {
		return directorLock;
	}




	public Condition getDirectorFunds() {
		return directorFunds;
	}

	public MuseumState getState() {
		return State;
	}
	
	public void setState(MuseumState state) {
		State = state;
	}


	public void RandomState (MuseumState value) {	
		int number = new Random().nextInt(2) + 1;
	//	System.out.println("Number = " + number + " count = " + count);
		
		directorLock.lock();
		try 
		{
			if (number == 2 ) 
			{   
				State = MuseumState.Open;
				System.out.println("Музей открыт");
				controller.setEvent(true);
			}
			else
			{
				State = MuseumState.Close;
				System.out.println("Музей закрыт");
				controller.setEvent(true);
			}
		
			directorFunds.signalAll();
			count++;
		}
		finally 
		{
			directorLock.unlock();
		}
	}


	
	
	
	@Override
	public void run() {
		 //TODO Auto-generated method stub
		try {
			Thread.sleep(300);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) 
		{
			try 
			{
				RandomState(State);
				//museumFunds.signalAll();
				Thread.sleep(8000);
			}
			catch (InterruptedException e) 
			{e.printStackTrace();}
			
		}// endwhile
			
		
	}
	
	
}
