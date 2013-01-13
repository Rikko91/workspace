

public class PassWest implements Runnable {
	private int people_in_museum;
	private boolean takeOutvisitors = false;
	public Director director; // �����njh
	public Controller controller; //����������
	public Visitors visitors;
	public PassEast museum;
	
	

	public void setTakeOutvisitors(boolean takeOutvisitors) {
		this.takeOutvisitors = takeOutvisitors;
	}


	public PassWest ( Visitors visitors_, Director director_,int input, PassEast museum) {
	director = director_;
	visitors = visitors_;
	people_in_museum=input;
	this.museum = museum;
	}
	
	
	
	public void takeOutVisitors () throws InterruptedException {	// ������� ����������� �� �����
		director.getDirectorLock().lock();
		try {
			//if (permitPeople() == false || permitPeople() == true  )
			//{
				//System.out.println("����� ������ - �������� West ��������");
				people_in_museum = visitors.getCountOutVisitorsToMuseum();
				
				while (people_in_museum != 0 ) {
					people_in_museum--;
					
				}
				System.out.println("Через WEST вышло - " + visitors.getCountOutVisitorsToMuseum() + " �����, �������� " + ( museum.getInputPeople() - visitors.getCountOutVisitorsToMuseum()) ) ;				
				int count = museum.getInputPeople() - visitors.getCountOutVisitorsToMuseum();
				museum.setPeople_in_museum(count); 
			//}
		}
		finally {
			director.getDirectorLock().unlock();
		}
	}
	

	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			director.getDirectorLock().lock();
			try {
				try {
					director.getDirectorFunds().await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (takeOutvisitors == true) {
					try {
						takeOutVisitors();
						takeOutvisitors = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
			}
			finally {
				director.getDirectorLock().unlock();
			}
			
		}
	}
	
}
