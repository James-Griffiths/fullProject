package Leisure;


import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class RoundedJTextField extends JTextField {
private Shape shape;

public RoundedJTextField() {
    super();
    setOpaque(false); // As suggested by @AVD in comment.
} 

public RoundedJTextField(int size) {
    super(size);
    setOpaque(false); // As suggested by @AVD in comment.
} 

public RoundedJTextField(String text, int size) {
    super(text, size);
    setOpaque(false); // As suggested by @AVD in comment.
}

protected void paintComponent(Graphics g) {
     g.setColor(getBackground());
     g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 5, 5);
     super.paintComponent(g);
}
protected void paintBorder(Graphics g) {
     g.setColor(getForeground());
     g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 5, 5);
}
public boolean contains(int x, int y) {
     if (shape == null || !shape.getBounds().equals(getBounds())) {
         shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 5, 5);
     }
     return shape.contains(x, y);
}
}