/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_practica2;

/**
 *
 * @author Erick C
 */
import java.io.File;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class Client {

    public static void main(String[] argv) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Nombre:");
            String name = s.nextLine().trim();

            ChatInterface client = new Chat(name);
            Registry registry = LocateRegistry.getRegistry(8888);
            ChatInterface server = (ChatInterface) registry.lookup("ejemplo");
            String msg = "[" + client.getName() + "] conectado!";
            server.send(msg);
            System.out.println("Servicio listo");
            server.setClient(name, client);
            String user = "";
            while (true) {
                System.out.println("Initializing");
                System.out.println(server.leerArchivo());
                String cadenaPlus = server.leerArchivo();
                //System.out.print("Usuario: ");
                //user=s.nextLine().trim();
                System.out.print("Mensaje: ");
                msg = s.nextLine().trim();
                if (!user.equals("")) {
                    msg = "[" + client.getName() + "] " + msg;
                    server.escribirArchivo(msg);
                    server.send(msg, user);

                } else {
                    msg = "[" + client.getName() + "] " + msg;
                    String msg2 = cadenaPlus + msg;
                    server.escribirArchivo(msg2);
                    server.send(msg);

                }
            }
        } catch (Exception e) {
            System.out.println("[System] Server failed: " + e);
        }
    }

}
