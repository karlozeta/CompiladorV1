package compilador;

import view.MainView;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MainView();
                frame.setSize(700, 700);
                frame.setVisible(true);
            }
        });
    }
}
