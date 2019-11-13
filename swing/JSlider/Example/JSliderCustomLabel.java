package JSlider.Example;



//Bu kod aşağıdaki linkten alınmıştır. Örnek olması açısından buraya eklenmiştir.
// https://kodejava.org/how-do-i-create-a-jslider-with-custom-labels/

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class JSliderCustomLabel extends JPanel {
    public JSliderCustomLabel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 200));

        JSlider slider = new JSlider(JSlider.VERTICAL, 0, 40, 0);
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Freezing"));
        labels.put(15, new JLabel("Cold"));
        labels.put(25, new JLabel("Warm"));
        labels.put(35, new JLabel("Hot"));
        slider.setLabelTable(labels);

        slider.setPaintLabels(true);

        add(slider, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new JSliderCustomLabel();

        JFrame frame = new JFrame("JSlider Custom Label");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JSliderCustomLabel.showFrame();
            }
        });
    }
}
