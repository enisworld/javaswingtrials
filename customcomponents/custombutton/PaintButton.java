package customcomponents.custombutton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class PaintButton extends JComponent implements MouseListener{
    private Dimension size = new Dimension(42,42);

    public Dimension dot = new Dimension((int)(size.width/3), (int)(size.height/3));
    public Dimension arc = new Dimension((int)Math.sqrt(size.width), (int)Math.sqrt(size.height));
    public int val;
    public int pos;
    int buttonXstart;
    int buttonXend;
    int buttonYstart;
    int buttonYend;
    
    private boolean mousePressed = false;
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	public PaintButton() {
		super();
		enableInputMethods(true);   
		addMouseListener(this);
		
        setSize(size.width, size.height);
        setFocusable(true);

        this.pos = pos;
        ActionListener act = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Clicked");
			}
		};
		listeners.add(act);
	}
	
	@Override
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
		System.out.println("draw");

        if(val != -1)
        {
            g.setColor(Color.GREEN);
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc.width, arc.height);
            int height;
            int width = height = dot.height * 2/3;

            // possible positions for each dot on the dice
            int left   = getWidth() * 1/3 - dot.width/2 - width * 1/4;
            int center = getWidth() * 2/3 - dot.width/2 - width * 1/2;
            int right  = getWidth() * 3/3 - dot.width/2 - width * 3/4;

            int top    = getHeight() * 1/3 - dot.height/2 - height * 1/4;
            int middle = getHeight() * 2/3 - dot.height/2 - height * 1/2;
            int bottom = getHeight() * 3/3 - dot.height/2 - height * 3/4;
            roll();
            left = left-20;
        	bottom = bottom-50;
        	right = right -20;
            g.setColor(Color.WHITE);
            center = center - 20;
            switch(val)
            {
                case 0:
                    break;
                case 1:
                    g.fillOval(center, middle, width*4, height*4);
                    g.setColor(Color.BLACK);
                    g.drawString("1", center+width*17/10, middle+height*5/2+1);
                    break;
                case 2:
                	
                    g.fillRect(right, top, width*4, height*4);
                    g.fillRect(left, bottom, width*4, height*4);
                    g.setColor(Color.BLACK);
                    g.drawString("2", right+width*8/5, top+height*5/2);
                    g.drawString("2", left+width*8/5, bottom+height*5/2);
                    buttonXstart = left;
                    buttonXend = left + width*4;
                    buttonYstart = bottom ;
                    buttonYend = bottom + height*4;
                    break;
                case 3:
                    g.fillOval(right, top, width, height);
                    g.fillOval(center, middle, width, height);
                    g.fillOval(left, bottom, width, height);
                    break;
                case 4:
                    g.fillOval(left, top, width, height);
                    g.fillOval(left, bottom, width, height);
                    g.fillOval(right, top, width, height);
                    g.fillOval(right, bottom, width, height);
                    break;
                case 5:
                    g.fillOval(left, top, width, height);
                    g.fillOval(left, bottom, width, height);
                    g.fillOval(right, top, width, height);
                    g.fillOval(right, bottom, width, height);
                    g.fillOval(center, middle, width, height);
                    break;
                case 6:
                    g.fillOval(left, top, width, height);
                    g.fillOval(left, middle, width, height);
                    g.fillOval(left, bottom, width, height);
                    g.fillOval(right, top, width, height);
                    g.fillOval(right, middle, width, height);
                    g.fillOval(right, bottom, width, height);
                    break;
            }


        }
        
    }
    public void roll()
    {
        // the amount of sides on the dice
        int faces = 6;
        // give this dice a random value
        val = 2;//(int)(Math.random() * faces + 1);
    }

	
	@Override
    public Dimension getPreferredSize()
    {
        return new Dimension(getWidth(), getHeight());
    }
	@Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
	@Override
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("mouseclicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mousepressed");
        //notifyListeners(e);
        mousePressed = true;
        if (checkClickPosition(e)) {
        	JOptionPane.showMessageDialog(null, " Button Cliecked!! ", "InfoBox: " + "titlebar", JOptionPane.INFORMATION_MESSAGE);
		}
        //repaint();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
    private void notifyListeners(MouseEvent e)
    {
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
        synchronized(listeners)
        {
            for (int i = 0; i < listeners.size(); i++)
            {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
        }
    }
    private boolean checkClickPosition(MouseEvent e) {
    	if (e.getX()>buttonXstart && e.getX()< buttonXend &&e.getY()>buttonYstart && e.getY()< buttonYend ) {
			return true;
		}
    	return false;
    }
}
