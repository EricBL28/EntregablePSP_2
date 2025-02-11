package org.example;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {

        String opcion;
        do {
            mostrarMenu();
            opcion =sc.nextLine();
            elegirOpcion(opcion);
        }while (!Objects.equals(opcion, "3"));
    }
    public static void mostrarMenu(){

        System.out.println("MENU");
        System.out.println("1. Leer un correo");
        System.out.println("2. Enviar un correo");
        System.out.println("3. Salir");
    }
    public static void elegirOpcion(String opcion) throws IOException {

        switch(opcion){
            case "1":
                leerCorreo();
                break;
            case "2":
                enviarCorreo();
                break;
            case "3":
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }

    private static void enviarCorreo() {
    }

    private static void leerCorreo() {
    }
}