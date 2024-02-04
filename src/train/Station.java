package train;

/**
 * Représentation d'une gare. C'est une sous-classe de la classe {@link Element}.
 * Une gare est caractérisée par un nom et un nombre de quais (donc de trains
 * qu'elle est susceptible d'accueillir à un instant donné).
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Station extends Element {
	private final int size;
	private int count;

	public Station(String name, int size) {
		super(name);
		if(name == null || size <=0)
			throw new NullPointerException();
		this.size = size;
		this.count = 0;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	/**
	 * @return a boolean verifying whether the station has an available place or not
	 */
	@Override
	public synchronized boolean isAvailable() {
		return count < size ;
	}
	
	/**
	 * @return a boolean verifying whether the station is empty or not
	 */
	@Override
	public boolean isEmpty() {
		return count == 0;
	}
	
	/**
	 * reserves a place in the station by adding 1 to the count variable
	 */
	@Override
	public synchronized void reserve() {
		count++;
	}
	
	/**
	 * releases a place in the stating by subtracting 1 to the count variable
	 */
	@Override
	public synchronized void release() {
		count--;
	}
	
	/**
	 * this method is called by a train when wanting to depart from the station, it is overriding its parent method in {@link Element} to add a condition that stops the train from departing if there is a train coming in the opposite direction
	 * @param dir represents the direction of the train departing from the station
	 * @throws InterruptedException
	 */
	@Override
	public synchronized void depart(Direction dir) throws InterruptedException {
		Element pointer = this.next(dir);
		
		while (!(pointer instanceof Station)) {
			while (pointer.railway.getRecord().get(pointer) != dir && !pointer.isEmpty()) {
				wait();
			}
			pointer = pointer.next(dir);
		}
		super.depart(dir);
	}
}
