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
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;
import java.io.File;

public class Server {

    public static void main(String[] argv) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Nombre: ");
            String name = s.nextLine().trim();
            File fileX = new File("a.txt");
            Chat server = new Chat(name);
            Registry registry = LocateRegistry.createRegistry(8888);
            registry.bind("ejemplo", server);

            while (true) {
                String msg = s.nextLine().trim();
                if (server.getClient() != null) {

                    Vector<ChatInterface> client = server.getClient();
                    Iterator<ChatInterface> ic = client.iterator();
                    while (ic.hasNext()) {
                        ic.next().send("[" + server.getName() + "] " + msg);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("[System] Server failed: " + e);
        }
    }

}
