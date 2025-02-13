/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class Main {

    private static final Properties prop = new Properties();
    private static String cuentaUsuario;
    private static String password;
    private static String mailDestinatario="struitsmith@gmail.com";
    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) throws IOException {

        cargarPropiedades();

        String opcion;
        do {
            mostrarMenu();
            opcion =sc.nextLine();
            elegirOpcion(opcion);
        }while (!Objects.equals(opcion, "3"));

    }

    public static void mostrarMenu(){

        System.out.println("MENU");
        System.out.println("1. Leer correo");
        System.out.println("2. Enviar Email");
        System.out.println("3. Salir");

    }
    public static void elegirOpcion(String opcion) throws IOException {

        switch(opcion){
            case "1":
                leerEmail();
                break;
            case "2":
                enviarEmail();
                break;
            case "3":
                System.out.println("Saliendo del programa");
                break;

            default:
                System.out.println("Opcion no valida");
        }
    }
    public static void cargarPropiedades() {
        try {
            prop.load(new FileInputStream("src/main/resources/configuracion.properties"));
            cuentaUsuario = prop.getProperty("email");
            password = prop.getProperty("password");
        } catch (IOException ex) {
            System.out.println("Error cargando configuración: " + ex.getMessage());
        }
    }

    public static void leerEmail() {

        try {
            Properties prop = new Properties();
            prop.put("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(prop, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", cuentaUsuario, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                if (!message.isSet(Flags.Flag.SEEN)) {
                    System.out.println("Remitente: " + Arrays.toString(message.getFrom()));
                    System.out.println("Asunto: " + message.getSubject());
                    System.out.println("Fecha: " + message.getSentDate());
                }
            }
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            System.out.println("Error leyendo correos: " + e.getMessage());
        }

    }

    public static void enviarEmail() {

        try {
            System.out.print("Destinatario: ");
            String to = sc.nextLine();
            System.out.print("Asunto: ");
            String subject = sc.nextLine();
            System.out.print("Mensaje: ");
            String body = sc.nextLine();
            System.out.print("Ruta del archivo adjunto: ");
            String filePath = sc.nextLine();

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");

            Session session = Session.getInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(cuentaUsuario, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(cuentaUsuario));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(new File(filePath).getName());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Correo enviado correctamente.");
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

    }
}
