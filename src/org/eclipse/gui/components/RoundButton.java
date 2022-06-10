package org.eclipse.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class RoundButton extends JButton {

	private Shape shape;
	
	public RoundButton(String label) {
		super(label);

		setBackground(new Color(41, 197, 255));
		setFocusable(false);
		setForeground(Color.WHITE);

		/*
		 * These statements enlarge the button so that it becomes a circle rather than
		 * an oval.
		 */
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		/*
		 * This call causes the JButton not to paint the background. This allows us to
		 * paint a round background.
		 */
		setContentAreaFilled(false);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.WHITE);
			setForeground(new Color(41, 197, 255));
		} else {
			g.setColor(getBackground());
		}
		// g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13, 13);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(Color.white);
		setForeground(Color.white);
		// g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 13,13);
	}

	public boolean contains(int x, int y) {
		// If the button has changed size, make a new shape object.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

}