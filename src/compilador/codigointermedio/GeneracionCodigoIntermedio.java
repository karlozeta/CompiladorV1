package compilador.codigointermedio;

import compilador.models.Cuadruplo;
import compilador.models.ExpresionAssembly;
import compilador.models.Simbolo;
import view.CodigoIntermedio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneracionCodigoIntermedio {

    private class Expresion {
        public String id;
        public String expresion;

        private Expresion(String id, String expresion) {
            this.id = id;
            this.expresion = expresion;
        }
    }

    private class tmpValor {
        public String nombre;
        public double valor;

        private tmpValor(String nombre, double valor) {
            this.nombre = nombre;
            this.valor = valor;
        }
    }

    ArrayList<Simbolo> listaSimbolos;
    ArrayList<Expresion> listaExpresiones;
    ArrayList<Cuadruplo> listaCuadruplos;
    List<String> lista = new ArrayList<>();

    public GeneracionCodigoIntermedio(ArrayList<Simbolo> listaSimbolos) {
        this.listaSimbolos = listaSimbolos;
        obtenerExpresiones();
        generarCuadruplos();
    }

    private void obtenerExpresiones() {
        listaExpresiones = new ArrayList<>();
        for (Simbolo simbolo : listaSimbolos) {
            String expresion = simbolo.getValor();
            // Cuádruplos
            if (expresion.split("\\s+").length >= 3) {
                listaExpresiones.add(new Expresion(simbolo.getIdentificador(), expresion));
            }
        }
    }

    private void generarCuadruplos() {
        for (Expresion ex : listaExpresiones) {
            cabeceraImpresion(ex);
            listaCuadruplos = new ArrayList<>();
            ArrayList<String> elementos = new ArrayList<>(Arrays.asList(ex.expresion.split("\\s+")));
            int contadorTmp = 1;
            for (int i = 0; i < elementos.size(); i++) {
                if (elementos.get(i).equals("/") || elementos.get(i).equals("*")) {
                    generarCuadruplo(elementos, i, contadorTmp);
                    contadorTmp++;
                    i = -1;
                } else if (!elementos.contains("/") && !elementos.contains("*")  && (elementos.get(i).equals("+") || elementos.get(i).equals("-"))) {
                    generarCuadruplo(elementos, i, contadorTmp);
                    contadorTmp++;
                    i = -1;
                } else if (elementos.size() == 1) {
                    listaCuadruplos.add(new Cuadruplo(
                            ex.id,  // Operador
                            elementos.get(i),       // Operador1
                            "",            // Operador2
                            ""              // Resultado
                    ));
                }

            }
            cuerpoImpresion();
            generarCodigoObjeto();
        }

        CodigoIntermedio codigoIntermedio = new CodigoIntermedio(lista);
        codigoIntermedio.setSize(700, 700);
        codigoIntermedio.setVisible(true);
    }

    private void generarCodigoObjeto() {
        cabeceraAssembly();
        listaCuadruplos.forEach(cuadruplo -> {
            if (cuadruplo.getOperador().equals("*")) {
                lista.add(ExpresionAssembly.MOV + " " + cuadruplo.getOperador1() + ", " + cuadruplo.getResultado() + "\n");
                lista.add(ExpresionAssembly.MULT + " " + cuadruplo.getOperador2() + ", " + cuadruplo.getResultado() + "\n");
            } else if (cuadruplo.getOperador().equals("/")) {
                lista.add(ExpresionAssembly.MOV + " " + cuadruplo.getOperador1() + ", " + cuadruplo.getResultado() + "\n");
                lista.add(ExpresionAssembly.DIV + " " + cuadruplo.getOperador2() + ", " + cuadruplo.getResultado() + "\n");
            } else if (cuadruplo.getOperador().equals("+")) {
                lista.add(ExpresionAssembly.MOV + " " + cuadruplo.getOperador1() + ", " + cuadruplo.getResultado() + "\n");
                lista.add(ExpresionAssembly.ADD + " " + cuadruplo.getOperador2() + ", " + cuadruplo.getResultado() + "\n");
            } else if (cuadruplo.getOperador().equals("-")) {
                lista.add(ExpresionAssembly.MOV + " " + cuadruplo.getOperador1() + ", " + cuadruplo.getResultado() + "\n");
                lista.add(ExpresionAssembly.SUB + " " + cuadruplo.getOperador2() + ", " + cuadruplo.getResultado() + "\n");
            } else {
                lista.add(ExpresionAssembly.MOV + " " + cuadruplo.getOperador() + ", " + cuadruplo.getOperador1() + "\n");
            }
        });
    }

    private String generarTmp(String operador) {
        switch (operador) {
            case "/":
                return "varTempDiv";
            case "*":
                return "varTempMultip";
            case "+":
                return "varTempSum";
            case "-":
                return "varTempResta";
            default:
                return "varTemp";
        }
    }

    private void generarCuadruplo(ArrayList<String> elementos, int i, int contador) {
        String tmp = generarTmp(elementos.get(i)) + contador;
        listaCuadruplos.add(new Cuadruplo(
                elementos.get(i),       // Operador
                elementos.get(i - 1),   // Operador1
                elementos.get(i + 1),   // Operador2
                tmp                     // Resultado
        ));
        elementos.set(i, tmp);
        elementos.remove(i + 1);
        elementos.remove(i - 1);

    }

    private void cabeceraAssembly(){
        lista.add("\n---------- ENSAMBLADOR -----------------------------------------------------\n");
    }

    private void cabeceraImpresion(Expresion ex){
        lista.add("\n---------- Expresión -----------------------------------------------------\n");
        lista.add(ex.id + " = " + ex.expresion + "\n");
        lista.add("--------------------------------------------------------------------------\n");
        lista.add(String.format("%15s %20s %20s %20s\n", "Operador", "Operando1", "Operando2", "Resultado"));
        lista.add("--------------------------------------------------------------------------\n");
    }

    private void cuerpoImpresion() {
        for (Cuadruplo cuadruplo : listaCuadruplos) {
            lista.add(String.format("%15s %20s %20s %20s\n",cuadruplo.getOperador() , cuadruplo.getOperador1(), cuadruplo.getOperador2(), cuadruplo.getResultado()));
        }
        lista.add("--------------------------------------------------------------------------\n\n");
    }

}


