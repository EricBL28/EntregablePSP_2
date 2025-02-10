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
            opcion = sc.nextLine();
        }while (!Objects.equals(opcion, "6"));

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
                System.out.println("Descarga finalizada correctamente");
            }else{
                clienteFTP.disconnect();
                System.err.println("FTP ha rechazado la conexión esblecida");
                System.exit(1);
            }

        }catch(IOException e){
            System.err.println("Error al leer el archivo : "+e.getMessage());
        }

    }

    public static void mostrarMenu(){

        System.out.println("MENU");
        System.out.println("1. Mostrar directorio actual");
        System.out.println("2. Entrar directorio");
        System.out.println("3. Subir al directorio padre");
        System.out.println("4. Subir chero: ");
        System.out.println("5. Borrar un chero: ");
        System.out.println("6. Salir");


    }
}