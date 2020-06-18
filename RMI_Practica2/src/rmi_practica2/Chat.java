/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi_practica2;

import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Erick C
 */
public class Chat extends UnicastRemoteObject implements ChatInterface{
    public String name;
    public Vector<ChatInterface> client = new Vector();
    public HashMap<String, ChatInterface> clientes = new HashMap();
    private File file;
    private FileReader fr = null;
    private BufferedReader br = null;
    private FileWriter fw = null;
    private String selectedPath;

    public Chat(String n) throws RemoteException {
        this.name = n;
    }
    public void setFilePath(String s) throws RemoteException{
        this.file=new File(s);
    }
    public void setFileData(String s) throws RemoteException{
        this.selectedPath=s;
    }
    
    public File getFileData() throws RemoteException{
        File buscar = new File(selectedPath);
        return buscar;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public void setClient(ChatInterface c) throws RemoteException {
        client.add(c);
    }

    public void setClient(String userName, ChatInterface c) throws RemoteException {
        this.setClient(c);
        clientes.put(userName, c);
    }

    public Vector<ChatInterface> getClient() {
        return client;
    }

    public void send(String s) throws RemoteException {
        System.out.println(s);
        Iterator<ChatInterface> it = this.client.iterator();
        while (it.hasNext()) {
            it.next().send(s);
        }
    }

    public void send(String s, String userName) throws RemoteException {
        this.clientes.get(userName).send(s);
    }

    public String leerArchivo() throws RemoteException {
        String completo = "";
        try {
            fr = new FileReader(this.file);
            br = new BufferedReader(fr);
            String linea;
            completo = "";
            while ((linea = br.readLine()) != null) {
                completo += linea + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                    return completo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return completo;
    }
    
    public void escribirArchivo(String msg) throws RemoteException{
        try {
            fw = new FileWriter(this.file);
            fw.write(msg);
            fw.flush();
            fw.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean verifyExist(String fileNameVerify) throws RemoteException{
        boolean retVal=false;
        
        File tmpDir = new File("./serverDocs/"+fileNameVerify+".txt");
        System.out.println(fileNameVerify);
        System.out.println(tmpDir.exists());
        if(tmpDir.exists()){
            
            System.out.println("Ya existe");
            retVal=true;
        }else{
            System.out.println("Es nuevo");
            retVal=false;
        }
        return retVal;
    }
    
    public void createFile(String fileName) throws RemoteException {
        try{
            String path = "./serverDocs/" + fileName + ".txt";
            File newFile = new File(path);
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
