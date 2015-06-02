package controller.general;

import java.util.ArrayList;

public interface Processes {
	/**
	 * Processes for scripts
	 */
	public ArrayList<Process> scripts = new ArrayList<Process>();
	
	/**
	 * Processes for application
	 */
	public ArrayList<Process> app = new ArrayList<Process>();

}
