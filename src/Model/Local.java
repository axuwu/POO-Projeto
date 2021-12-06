package Model;
import java.io.Serializable;

/**
 * Classe que representa o Local
 */
public class Local implements Serializable {
    private int localId;
    private String name;

    /**
     * Constructor do Local
     * @param localId id
     * @param name nome
     */
    public Local(int localId, String name) {
        this.localId = localId;
        this.name = name;
    }

    /**
     * Getter id
     * @return id
     */
    public int getLocalId() {
        return localId;
    }

    /**
     * Getter nome
     * @return nome
     */
    public String getName() {
        return name;
    }
}