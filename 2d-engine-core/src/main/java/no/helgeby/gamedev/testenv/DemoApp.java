package no.helgeby.gamedev.testenv;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import no.helgeby.gamedev.engine.Engine;
import no.helgeby.gamedev.engine.Environment;

public class DemoApp extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoApp("2D Engine Demo");
		});
	}

	private static Engine createEngine() {
		//return new Engine(new TestEnvironment());
		return new Engine(new BouncingBoxEnvironment());
	}

	public DemoApp(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Preferred size doesn't account for window padding.
		final int width = Environment.DEFAULT_WIDTH + 16;
		final int height = Environment.DEFAULT_HEIGHT + 39;

		setPreferredSize(new Dimension(width, height));

		Engine engine = createEngine();

		// Make the engine aware of the window position, for movement effects.
		engine.initPosition((int) getLocation().getX(), (int) getLocation().getY());

		WorldPanel worldPanel = new WorldPanel(engine);
		add(worldPanel);
		pack();

		setVisible(true);
		engine.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				engine.stop();
			}
		});

		DemoApp me = this;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				int x = (int) me.getLocation().getX();
				int y = (int) me.getLocation().getY();
				engine.move(x, y);
			}
		});
	}
}
