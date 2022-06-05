package view;

import compilador.analisis.AnalisisLexico;
import compilador.analisis.AnalisisSemantico;
import compilador.analisis.TablaSimbolos;
import compilador.codigointermedio.GeneracionCodigoIntermedio;
import compilador.models.Simbolo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView  extends JFrame{
    private JPanel panel1;
    private JTextArea textAreaCode;
    private JTextArea textAreaIdentificador;
<<<<<<< HEAD
    public JTextArea textAreaLog;
=======
    public  JTextArea textAreaLog;
>>>>>>> 285eb218dba6d77e881dea2fc2416f45960196b1
    private JButton btnAnalizar;

    public MainView() {
        super("Compilador");
        setContentPane(panel1);
        btnAnalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compilar();
            }
        });
    }

    private void compilar() {
        textAreaIdentificador.setText("");
        textAreaLog.setText("");

        if (textAreaCode.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Nada para compilar/analizar!!!");
            textAreaCode.requestFocus();
            return;
        }

        String[] codes =  textAreaCode.getText().split("\n");

        AnalisisLexico analizadorLexico = new AnalisisLexico(codes);
        ArrayList<String> erroresLexicos = analizadorLexico.getErroresLexicos();

        textAreaLog.setText("");

        for (int i = 0; i < erroresLexicos.size(); i++) {
            textAreaLog.append(erroresLexicos.get(i) + "\n");
        }

        TablaSimbolos tablaSimbolos = new TablaSimbolos(analizadorLexico.getTokenRC());

        AnalisisSemantico analizadorSemantico = new AnalisisSemantico(tablaSimbolos);
        for (int i = 0; i < analizadorSemantico.getErroresSemanticos().size(); i++) {
            textAreaLog.append(analizadorSemantico.getErroresSemanticos().get(i) + "\n");
        }

        imprimirIdentificadores(tablaSimbolos.getListaSimbolos());
        if (!analizadorLexico.getHayErrores() && !(analizadorSemantico.getErroresSemanticos().size() > 0)){
            new GeneracionCodigoIntermedio(tablaSimbolos.getListaSimbolos());
        }

    }

    private void imprimirIdentificadores(ArrayList<Simbolo> listaTablaSimbolos){
        listaTablaSimbolos.forEach(s-> {
            textAreaIdentificador.append("Identificador: " + s.getIdentificador() + "\n");
            textAreaIdentificador.append("Tipo Dato: " + s.getTipoDato() + "\n");
            textAreaIdentificador.append("Posición: " + s.getPosicion() + "\n");
            textAreaIdentificador.append("Valor: " + s.getValor() + "\n\n");
        });

    }
}
