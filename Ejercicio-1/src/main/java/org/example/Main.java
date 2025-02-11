package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    final static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws IOException {

        Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/propierties.properties"));

        FTPClient clienteFTP = new FTPClient();

        clienteFTP.connect(prop.getProperty("hostname"), Integer.parseInt(prop.getProperty("puerto")));

        int respuesta=clienteFTP.getReplyCode();
        if(FTPReply.isPositiveCompletion(respuesta)){
            clienteFTP.login(prop.getProperty("usuario"), prop.getProperty("password"));
            String opcion;
            do {
                mostrarMenu();
                opcion =sc.nextLine();
                elegirOpcion(opcion,clienteFTP);
            }while (!Objects.equals(opcion, "6"));

            clienteFTP.disconnect();
        }else{
            clienteFTP.disconnect();
            System.err.println("FTP ha rechazado la conexión esblecida");
            System.exit(1);
        }

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
    public static void elegirOpcion(String opcion,FTPClient clienteFTP) throws IOException {

        switch(opcion){
            case "1":
                mostrarDirectorio(clienteFTP);
                break;
            case "2":
                entrarDirectorio(clienteFTP);
                break;
            case "3":
                clienteFTP.changeToParentDirectory();
                break;
            case "4":
                subirFichero(clienteFTP);
                break;
            case "5":
                borrarFichero(clienteFTP);
                break;
            case "6":
                System.out.println("ADIOS :C");
                break;
            default:
                System.out.println("Opcion no valida");
        }
    }
    public static void mostrarDirectorio(FTPClient clienteFTP) {
        try {
            clienteFTP.enterLocalPassiveMode();
            System.out.println("Carpetas disponibles en Servidor:");

            FTPFile[] nombreCarpeta = clienteFTP.listFiles();
            for (FTPFile s : nombreCarpeta) {
                System.out.println(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void entrarDirectorio(FTPClient clienteFTP)  {
        try{
            System.out.println("Introduce el directorio al que quieres acceder:");
            String directorio = sc.nextLine();
            boolean hecho = clienteFTP.changeWorkingDirectory(directorio);

            if (hecho) {
                System.out.println("Se ha entrado al directorio");
            } else {
                System.out.println("No se ha entrado al directorio");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    public static void subirFichero(FTPClient clienteFTP){

        try{

            System.out.println("Introduce el fichero que quieres subir: ");
            String fichero = sc.nextLine();
            File file= new File(fichero);

            if(file.exists()){
                InputStream in = new FileInputStream(fichero);

                clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);

                clienteFTP.setFileTransferMode(FTP.BINARY_FILE_TYPE);

                clienteFTP.appendFile(fichero,in);

                System.out.println("Se ha subido el fichero correctamente");

            }else{
                System.out.println("No se ha encontrado el fichero");
            }


        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
    public static void borrarFichero(FTPClient clienteFTP){

        try {
            System.out.println("Introduce el fichero que quieres borrar: ");

            String fichero = sc.nextLine();
            if(clienteFTP.deleteFile(fichero)){
                System.out.println("El fichero se ha eliminado");
            }else {
                System.out.println("No se ha eliminado");
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }

    }
}