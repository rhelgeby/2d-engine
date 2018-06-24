package no.helgeby.gamedev.testenv;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import no.helgeby.gamedev.engine.Engine;

public class WorldPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Engine engine;

	public WorldPanel(Engine engine) {
		this.engine = engine;
		engine.setEventListener(new EngineEventListener() {

			@Override
			public void onPostDraw(Engine engine) {
				WorldPanel.this.repaint();
			}
		});

		WorldPanel me = this;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				engine.resize(me.getWidth(), me.getHeight());
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(engine.getEnvironment().getImage(), 0, 0, Color.BLACK, null);

		// Draw some debug info on top of the image.
		g.setColor(new Color(255, 255, 255, 50));

		g.fillRect(16, 16, 200, 24);
		g.setColor(Color.BLACK);
		g.drawString("Frame " + engine.getFrameCount(), 24, 32);
		g.setColor(Color.YELLOW);
		g.drawString("Frame " + engine.getFrameCount(), 23, 31);
	}
}
