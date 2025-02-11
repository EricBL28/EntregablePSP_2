/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.example;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * ****************************************************************************
 * programa para enviar correo desde una cuenta de gmail.com por el puerto 465
 * SMTP sobre el protocolo seguro SSL (tiene que ser seguro, porque ni Google
 * ni ningún otro proveedor dejan enviar hoy día por el puerto 25)
 *
 * @author IMCG
 */
class EnviarCorreoSSL {

    //cuenta de usuario en gmail.com
    private static final String cuentaUsuario = "ericnztpruebas@gmail.com";

    //la contraseña que pones aquí no es la de tu correo, sino la que generas desde Gmail para que se conecte tu aplicación
  /*Ve a tu cuenta de Google.
  Selecciona Seguridad.
  En "Iniciar sesión en Google", selecciona Verificación en dos pasos.
  En la parte inferior de la página, selecciona Contraseñas de aplicación.
  Introduce un nombre que te ayude a recordar dónde utilizarás la contraseña de aplicación.
  Selecciona Generar.
  Para introducir la contraseña de aplicación, sigue las instrucciones que aparecen en pantalla. La contraseña de aplicación es el código de 16 caracteres que se genera en tu dispositivo.
  Selecciona Hecho.*/
    private static final String password = "tahi txkx owfk sctt";
    //dirección de correo del destinatario58
    private static final String mailDestinatario = "struitsmith@gmail.com";
    //redes@psp.local email del servidor de la máquina virtual


    public static void main(String[] args) {

        //valora propiedades para construir la sesión con el servidor
        Properties props = new Properties();
        //servidor SMTP
        //GMAIL --> smtp.gmail.com
        //local mira la ip con ip address
        //props.put("mail.smtp.host", "192.168.1.3");
        props.put("mail.smtp.host", "smtp.gmail.com");

        //identificación requerida
        props.put("mail.smtp.auth", "true");

        //Descomenta las siguientes líneas para enviar por SMTPS (SMTP sobre SSL)

        //puerto para el socket de sesión
        //props.put("mail.smtp.socketFactory.port", "465");
        //tipo de socket
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //puerto smtp
        //props.put("mail.smtp.port", "465");


        //Para transmisión segura a través de TLS
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        //Sin seguridad
        //props.put("mail.smtp.port", "25");

        //abre una nueva sesión contra el servidor basada en:
        //el usuario, la contraseña y las propiedades especificadas
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(cuentaUsuario, password);
                    }
                });

        System.out.println("Enviando Correo");
        try {
            //compone el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cuentaUsuario));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailDestinatario));

            //asunto
            message.setSubject("Holi");
            //cuerpo del mensaje
            message.setText("Emosido engañado");
            //envía el mensaje, realizando conexión, transmisión y desconexión
            Transport.send(message);
            //lo da por enviado
            System.out.println("Enviado!");
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
    }
}
