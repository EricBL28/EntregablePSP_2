package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/propierties.properties"));


            FTPClient clienteFTP = new FTPClient();
            clienteFTP.connect(prop.getProperty("hostname"), 21);

            int respuesta=clienteFTP.getReplyCode();
            if(FTPReply.isPositiveCompletion(respuesta)){


                clienteFTP.login(prop.getProperty("username"), prop.getProperty("password"));

                clienteFTP.enterLocalPassiveMode();

                System.out.println("Carpetas disponibles en Servidor:");

                String[] nombreCarpeta = clienteFTP.listNames();

                for (String s : nombreCarpeta) {
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
}