
public class Main {
	public static void main(String[] args) {
		Director director = new Director(MuseumState.Close);
		Visitors visitors = new Visitors(15);
		PassEast museum =new PassEast(visitors, director,0);
		PassWest museum2 = new PassWest(visitors, director,0,museum);
		Controller controller = new Controller(director, museum,museum2);
		
		visitors.museum = museum;
		director.controller = controller;
		visitors.museum2 = museum2;
		visitors.director = director;
		museum.controller = controller;
		museum2.controller = controller;
		controller.st = director.State;
		System.out.println("Start!!!!!!");
		
		new Thread (director,"Director").start();
		
		new Thread(museum,"East").start();
		
		new Thread(museum2,"West").start();
	
		new Thread(controller,"Controller").start();
		
		new Thread(visitors,"Visitors").start();
	
		
	}
}
