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
	
	@Override
	public synchronized boolean isAvailable() {
		return count < size ;
	}
	
	@Override
	public boolean isEmpty() {
		return count == 0;
	}
	
	@Override
	public synchronized void reserve() {
		count++;
	}
	
	@Override
	public synchronized void release() {
		count--;
	}
	
}
