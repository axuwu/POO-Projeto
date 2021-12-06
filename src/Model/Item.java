package Model;

/**
 * Classe que representa o Item
 */
public class Item {
    private int itemId;
    private String name;
    private String surname;
    private char permission;
    private int quantity;

    /**
     * Constructor do item
     * @param itemId id do item
     * @param name nome
     * @param permission permissão
     */
    public Item(int itemId, String name, char permission) {
        this.itemId=itemId;
        this.name=name;
        this.surname=null;
        checkPermission(permission);
        this.permission=permission;
        this.quantity=0;
    }

    /**
     * Constructor do item
     * @param itemId id do item
     * @param name 1º nome do item
     * @param surname 2º nome do item
     * @param permission permissão
     */
    public Item(int itemId, String name, String surname, char permission) {
        this.itemId=itemId;
        this.name=name;
        this.surname=surname;
        checkPermission(permission);
        this.permission=permission;
        this.quantity=0;
    }

    //Restrictions

    /**
     * Verifica se existe apenas N, S ou P como permissão
     * @param permission sigla da permissão
     */
    private void checkPermission(char permission) {
        if (permission != 'N' && permission != 'S' && permission != 'P'){
            throw new IllegalArgumentException("Permissão inexistente.");
        }
    }

    //Getters & Setters
    /**
     * Getter do id do item
     * @return id do item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Getter do nome completo do item
     * @return nome completo do item
     */
    public String getFullName() {
        String str;
        if (surname == null)
            str = name;
        else
            str = name + " " + surname;
        return str;
    }

    /**
     * Retorna a permissão do item
     * @return sigla da permissão
     */
    public char getPermission() {
        return permission;
    }

    /**
     * Retorna a quantidade do item
     * @return quantidade
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter da quantidade do item
     * @param quantity quantidade
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retorna nome completo da permissão
     * @return nome completo
     */
    public String getPermissionName() {
        String string;
        Character receivedPermission = getPermission();
        Character normal = 'N';
        Character seguro = 'S';
        Character perigoso = 'P';
        if (receivedPermission.equals(normal))
            string = "Normal";
        else if (receivedPermission.equals(seguro))
            string = "Seguro";
        else if(receivedPermission.equals(perigoso))
            string = "Perigoso";
        else
            string = null;
        return string;
    }

    //Specific toString()
    /**
     * ToString de quantidade, nome da permissão e nome do item
     * @return string info
     */
    public String stringQuantityPermissionName() {
        return getQuantity() + " [" + getPermissionName() + "] " + getFullName();
    }

    /**
     * ToString id do item, quantidade, permissão e nome do item
     * @return string info
     */
    public String stringIdQuantityPermissionName() {
        return getItemId() + " (" + getQuantity() + ") " + "[" + getPermission() + "] " + getFullName();
    }

}
