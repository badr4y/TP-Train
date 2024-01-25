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
	
	@Override
	void setAvailable() {
		if(count == size-1) {
			available=false;
		}
	}
	
	@Override
	boolean isAvailable() {
		return count < size;
	}
	
	@Override
	synchronized void arrive() throws InterruptedException {
		count++;
		notifyAll();
	}
	
	@Override
	synchronized void depart(Direction dir) throws InterruptedException {
		while(!this.next(dir).isAvailable()) {
			wait();
		}
		if (count>0) {
			count--;
		}
		this.next(dir).setAvailable();
		this.setAvailable();
		notifyAll();
	}
	
	
}
