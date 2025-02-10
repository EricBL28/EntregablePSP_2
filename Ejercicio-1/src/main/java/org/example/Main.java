package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    final static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        String opcion;
        do {
            mostrarMenu();
            opcion =sc.nextLine();
            elegirOpcion(opcion);
        }while (!Objects.equals(opcion, "6"));

    }
    public static void mostrarMenu(){

        System.out.println("MENU");
        System.out.println("1. Mostrar directorio actual");
        System.out.println("2. Entrar directorio");
        System.out.println("3. Subir al directorio padre");
        System.out.println("4. Subir fichero: ");
        System.out.println("5. Borrar un fichero: ");
        System.out.println("6. Salir");
    }
    public static void elegirOpcion(String opcion){

        switch(opcion){
            case "1":
                mostrarDirectorio();
                break;
            case "2":
                entrarDirectorio();
                break;
            case "3":
                subirDirectorioPadre();
                break;
            case "4":
                subirFichero();
                break;
            case "5":
                borrarFichero();
                break;
            case "6":
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }
    public static void mostrarDirectorio(){
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/propierties.properties"));

            FTPClient clienteFTP = new FTPClient();

            clienteFTP.connect(prop.getProperty("hostname"), Integer.parseInt(prop.getProperty("puerto")));

            int respuesta=clienteFTP.getReplyCode();
            if(FTPReply.isPositiveCompletion(respuesta)){

                clienteFTP.login(prop.getProperty("usuario"), prop.getProperty("password"));

                clienteFTP.enterLocalPassiveMode();

                System.out.println("Carpetas disponibles en Servidor:");

                FTPFile[] nombreCarpeta = clienteFTP.listFiles();

                for (FTPFile s : nombreCarpeta) {
                    System.out.println(s);
                }
                clienteFTP.disconnect();
            }else{
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión esblecida");
                System.exit(1);
            }

        }catch(IOException e){
            System.err.println("Error al leer el archivo : "+e.getMessage());
        }
    }
    public static void entrarDirectorio(){
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/propierties.properties"));


            FTPClient clienteFTP = new FTPClient();
            clienteFTP.connect(prop.getProperty("hostname"), Integer.parseInt(prop.getProperty("puerto")));

            int respuesta=clienteFTP.getReplyCode();
            if(FTPReply.isPositiveCompletion(respuesta)){

                clienteFTP.login(prop.getProperty("usuario"), prop.getProperty("password"));

                System.out.println("Introduce el directorio al que quieres acceder:");
                String directorio = sc.nextLine();
                boolean hecho = clienteFTP.changeWorkingDirectory(directorio);
                if (hecho) {
                    System.out.println("Se ha entrado al directorio");
                } else {
                    System.out.println("No se ha entrado al directorio");
                }


                clienteFTP.disconnect();
            }else{
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión esblecida");
                System.exit(1);
            }


        }catch(IOException e){
            System.err.println("Error al leer el archivo : "+e.getMessage());
        }

    }
    public static void subirDirectorioPadre(){

    }
    public static void subirFichero(){

    }
    public static void borrarFichero(){

    }
}