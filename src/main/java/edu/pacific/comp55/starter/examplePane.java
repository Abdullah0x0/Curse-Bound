package edu.pacific.comp55.starter;

import java.util.ArrayList;

import acm.graphics.GObject;

/*
 * EXAMPLE PANE HERE BASICALLY COPY AND PASTE INTO NEW PANES
 */

public class examplePane extends GraphicsPane {
	// ---------- important do not touch  //
	private MainApplication program;
	private ArrayList<GObject> render = new ArrayList<GObject>();
	// ---------------------------------- //
	
	// initialize GObjects here.. GObject myObject; etc.. //
	
	// -------------------------------------------------- //
	
	//need to pass main application in
	public examplePane(MainApplication program) {
		super();
		this.program = program;
		
		// create GObjects here ----------------------------- //
		// myObject = new GObject(...);
		// render.add(myObject) ----- Allows our object to get auto rendered.
		// -------------------------------------------------- //
	}
	
	@Override
	public void showContents() {
		for (GObject obj : render) {
			program.remove(obj);
		}
	}

	@Override
	public void hideContents() {
		for (GObject obj : render) {
			program.remove(obj);
		}
	}

}
