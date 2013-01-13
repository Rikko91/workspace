import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Controller implements Runnable {
	private boolean flag_of_entrance; 
	private boolean event = false;
	public Director director;
	public PassEast museum;
	public PassWest museum2;
	public MuseumState st;
	
	
	public Controller(Director director_, PassEast museum_, PassWest museum2_) {
		director=director_;
		museum = museum_;
		museum2 = museum2_;
	}
	
	
	public boolean isEvent() {
		return event;
	}


	public void setEvent(boolean event) {
		this.event = event;
	}


	public boolean isFlag_of_entrance() {
		return flag_of_entrance;
	}


	public void test () {
		director.getDirectorLock().lock();
		try {
			
			if (director.getState() == MuseumState.Open) {//если музей открыт
				flag_of_entrance=true; //контроллер разрешает пускать людей и выпускать людей 
				museum.setOpengate(true);
				
				if ( st != director.getState() ) 
				System.out.println("Контроллер открыл турникет");
				st = director.getState();
			}
			else
				{// в другом случае разрешает только уходить людям
				flag_of_entrance=false;
				if ( st != director.getState() ) 
				System.out.println("Контроллер закрыл турникет");
				st = director.getState();
				}
			director.getDirectorFunds().signalAll();
		}
		finally 
		{
			director.getDirectorLock().unlock();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) 
		{
			
			director.getDirectorLock().lock();
			try {
				try {
					director.getDirectorFunds().await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (event == true)
				test();
				event = false;
			}
			finally {
				director.getDirectorLock().unlock();
			}
			
		}
	}
	
	
	
}
