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
	
	public int getSize() {
		return size;
	}
	
	public int getCount() {
		return count;
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
	
	public synchronized boolean verifyCapacity(Direction dir) {
		Element test = this.next(dir);
		int count = 1;
		while (test instanceof Section) {
			if (!test.isEmpty()) {
				count++;
			}
			test = test.next(dir);
		}
		Element cross = test;
		if (count <= (((Station) test).getSize()-((Station) test).getCount())) {
			if (test.next(dir) != null) {
				test = test.next(dir);
			}
			while (test instanceof Section) {
				if (!test.isEmpty() && test.railway.getRecord().get(test) != dir) {
					count++;
				}
				test = test.next(dir);
			}
		}
		
		return count <= (((Station) cross).getSize()-((Station) cross).getCount());
	}
	
	@Override
	public synchronized void reserve() {
		count++;
	}
	
	@Override
	public synchronized void release() {
		count--;
	}
	
	@Override
	public synchronized void depart(Direction dir) throws InterruptedException {
		Element nextElement = this.next(dir);
		while (!(nextElement instanceof Station)) {
			while (nextElement.railway.getRecord().get(nextElement) != dir && !nextElement.isEmpty()) {
				wait();
			}
			nextElement = nextElement.next(dir);
		}
		while (!this.verifyCapacity(dir)){
			wait();
		}
		
		super.depart(dir);
	}
}
