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
	
	@Override
	public synchronized void arrive() throws InterruptedException {
		while(full) {
			wait();
		}
		full = true;
	}
	
	@Override
	public synchronized void depart() {
		full = false;
		notifyAll();
	}
}
