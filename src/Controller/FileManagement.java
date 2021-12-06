package Controller;
import Model.*;
import java.io.*;

/**
 * Classe para gravar e ler ficheiros
 */
public class FileManagement implements Serializable{

    public FileManagement() {
    }

    /**
     * Escreve um ficheiro
     * @param fileName nome do ficheiro (com a sua "extensão")
     * @param writableObject um objeto
     */
    public void serialize(String fileName, Object writableObject) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(writableObject);
            System.out.println("Ficheiro gravado com sucesso.");
            oos.close();
            fos.close();
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lê um ficheiro
     * @param fileName nome do ficheiro
     */
    public void deserialize(String fileName) {
        Object obj;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = (Object) ois.readObject();
            System.out.println("Ficheiro lido com sucesso.");
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            //return;
        }
    }

    public FileManagement getYourself() {
        return this;
    }

}
