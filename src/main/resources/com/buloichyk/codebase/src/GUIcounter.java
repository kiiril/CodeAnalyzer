import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUIcounter {
    static int counter;
    static JLabel label = new JLabel("Frame of the counter "+counter);
    public static void main(String[] args) {
        JFrame frame = new JFrame("Action listener example");
        JButton b1 = new JButton("+");
        JButton b2 = new JButton("-");
        //lambda expression
        b1.addActionListener(e->{
            counter++;
            label.setText("After INCREASE: "+counter);
        });
        // new keyword for ActionListener
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                label.setText("After DECREASE:"+counter);
            }
        });
        /*ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                label.setText("After decreasing: "+ counter);
            }
        };
        b2.AAL(act);*/
        frame.add(b1);
        frame.add(b2);
        frame.add(label);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        frame.pack();
    }
}
