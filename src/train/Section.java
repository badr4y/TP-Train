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
	
	/**
	 * @return a boolean verifying whether the section is available or not
	 */
	@Override
	public synchronized boolean isAvailable() {
		return !full;
	}
	
	/**
	 * @return a boolean verifying whether the section is empty or not
	 */
	@Override
	public boolean isEmpty() {
		return !full;
	}
	
	/**
	 * reserves the section by setting its variable full as true
	 */
	public synchronized void reserve() {
		full = true;
	}
	
	/**
	 * releases the section by setting its variable full as false
	 */
	@Override
	public synchronized void release() {
		full = false;
	}

}
