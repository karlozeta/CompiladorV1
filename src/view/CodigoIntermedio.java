package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CodigoIntermedio extends JFrame{
    private JTextArea textAreaCodInt;
    private JPanel panel1;

    public CodigoIntermedio() {
    }

    public CodigoIntermedio(List<String> lista) {

        super("Código Intermedio");
        setContentPane(panel1);
        textAreaCodInt.setFont(new Font("Courier New", Font.PLAIN, 12));

        lista.forEach(l -> textAreaCodInt.append(l));

    }
}
