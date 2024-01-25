package train;

/**
 * Représentation d'une section de voie ferrée. C'est une sous-classe de la
 * classe {@link Element}.
 *
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {
	private boolean full;
	public Section(String name) {
		super(name);
		this.full = false;
	}
	
	public synchronized void setAvailable() {
		available = !available;
	}
	
	@Override
	boolean isAvailable() {
		return available;
	}
	
	@Override
	public synchronized void arrive() throws InterruptedException {
		full = true;
	}
	
	@Override
	public synchronized void depart(Direction dir) throws InterruptedException {
		while(!this.next(dir).isAvailable()) {
			wait();
		}
		full = false;
		this.next(dir).setAvailable();
		this.setAvailable();
		notifyAll();
	}
}
