package Model;
import java.util.ArrayList;

/**
 * Classe que representa o Cliente
 */
public class Client {
    private int clientId;
    private String name;
    private String surname;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Deposit> deposits = new ArrayList<>();
    private ArrayList<Delivery> deliveries = new ArrayList<>();
    private Worker manager;

    /**
     * O constructor da Classe Cliente quando não tem apelido
     * @param clientId o número id do Cliente
     * @param name o primeiro nome do Cliente
     */
    public Client(int clientId, String name) {
        this.clientId = clientId;
        this.name=name;
        this.surname=null;
    }

    /**
     * O constructor da Classe Cliente quando tem apelido
     * @param clientId o número id do Cliente
     * @param name o primeiro nome do Cliente
     * @param surname o apelido (último nome) do Cliente
     */
    public Client(int clientId, String name, String surname) {
        this.clientId = clientId;
        this.name=name;
        this.surname=surname;
    }

    //Getters & Setters

    /**
     * O getter do número id do Cliente
     * @return retorna um Integer de id do Cliente
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * O getter do nome completo do Cliente
     * @return retorna um String de 1º nome ou 1º nome + apelido, do Cliente
     */
    public String getFullName() {
        String str = null;
        if (surname == null) {
            str = name;
        }else if (surname != null) {
            str = name + " " + surname;
        }
        return str;
    }

    /**
     * Vê se o Cliente tem um Funcionário com um Funcionário com categoria de Gestor
     */
    public void checkManager() {
        if (manager == null){
            throw new NullPointerException("Funcionário inexistente.");
        } else if (!manager.getCategory().equals("Gestor")){
            throw new IllegalArgumentException("Funcionário incorreto.");
        }
    }

    /**
     * Setter para associar Gestor ao Cliente
     * @param manager
     */
    public void setManager(Worker manager) {
        this.manager = manager;
    }

    /**
     * Retorna o nome do Gestor associado ao Cliente
     * @return
     */
    public String getManagerName() {
        return manager.getFullName();
    }

    //ItemListRelated
    /**
     * Getter para retornar a sua lista de Itens
     * @return
     */
    public ArrayList<Item> getItemsList() {
        return items;
    }

    /**
     * Adiciona um Item á lista de Itens do Cliente
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Retorna (procurando) um Item do id que pedi
     * @param itemId o id especifico do item que quero
     * @return
     */
    public Item getItemById(int itemId) {
        Item item = null;
        for (int i = 0; i < getItemListSize(); i++) {
            if (itemId == getItemsList().get(i).getItemId())
                item = getItemsList().get(i);
        }
        return item;
    }

    /**
     * Retorna um Item a partir do index
     * @param index posição que está no Array
     * @return
     */
    public Item getItemAtIndex(int index) {
        return items.get(index);
    }

    /**
     * Retorna o (tamanho) size da Lista de Itens
     * @return
     */
    public int getItemListSize() {
        return items.size();
    }

    /**
     * Adiciona um valor quantidade ao item
     * @param item item
     * @param itemQuantity quantidade
     */
    public void addItemQuantity(Item item, int itemQuantity){
        int previousQuantity = item.getQuantity(); //inicialmente tem 0
        item.setQuantity(previousQuantity + itemQuantity);
    }

    //DepositListRelated
    /**
     * Retorna uma lista de depósitos
     * @return
     */
    public ArrayList<Deposit> getDepositsList() {
        return deposits;
    }

    /**
     * Adiciona um depósito á lista
     * @param deposit
     */
    public void addDeposit(Deposit deposit) {
        deposits.add(deposit);
    }

    //DeliveryListRelated
    /**
     * Retorna uma lista de entregas
     * @return
     */
    public ArrayList<Delivery> getDeliveriesList() {
        return deliveries;
    }

    /**
     * Adiciona uma entrega á lista
     * @param delivery
     */
    public void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }

    //Print Information
    /**
     * Imprime as informações
     */
    public void printInfo() {
        System.out.println(getFullName() + "\n" +
                getManagerName() + "\n" +
                "Items:");
        for (int i = 0; i < getItemListSize(); i++) {//TEMOS DE IMPRIMIR COM 2 ESPAÇOS LOL
            System.out.println("  " + getItemAtIndex(i).stringIdQuantityPermissionName());
        }

        System.out.println("Depósitos:");
        if (getDepositsList().size() != 0) {
            for (int i = 0; i < getDepositsList().size(); i++) {
                System.out.println(getDepositsList().get(i).stringDepositIdLocalName());
            }
        }

        System.out.println("Entregas:");
        if (getDeliveriesList().size() != 0) {
            for (int i = 0; i < getDeliveriesList().size(); i++) {
                System.out.println(getDeliveriesList().get(i).stringDeliveryIdLocalName());
            }
        }
    }

    /**
     * Imprime informaçãp especifica do item que pedi
     * @param itemId id do item
     */
    public void printItemInfo(int itemId) {
        System.out.println(getItemById(itemId).stringQuantityPermissionName());

        System.out.println("Depósitos:");
        if (getDepositsList().size() != 0) {
            Deposit deposit = null;
            for (int i = 0; i < getDepositsList().size(); i++) {
                if (getDepositsList().get(i).getItemById(itemId).getItemId() == itemId)
                    deposit = getDepositsList().get(i);
            }
            System.out.println(deposit.stringDepositIdItemQuantity(itemId));
        }

        System.out.println("Entregas:");
        if (getDeliveriesList().size() != 0) {
            Delivery delivery = null;
            for (int i = 0; i < getDeliveriesList().size(); i++) {
                if (getDeliveriesList().get(i).getItemAtIndex(itemId).getItemId() == itemId)
                    delivery = getDeliveriesList().get(i);
            }
            System.out.println(delivery.stringDeliveryIdItemQuantity(itemId));
        }

    }

    /**
     * Imprime todas as informações de todos os itens
     */
    public void printAllItensInfo() {
        int id = 0;
        for (int i = 0; i < getItemListSize(); i++) {
            System.out.println(getItemAtIndex(i).stringIdQuantityPermissionName());
            id = getItemAtIndex(i).getItemId();

            System.out.println("Depósitos:");
            if (getDepositsList().size() != 0) {
                Deposit deposit = null;
                for (int j = 0; j < getDepositsList().size(); j++) {
                    if (getDepositsList().get(i).getItemById(id).getItemId() == id)
                        deposit = getDepositsList().get(i);
                }
                System.out.println(deposit.stringDepositIdItemQuantity(id));
            }

            System.out.println("Entregas:");
            if (getDeliveriesList().size() != 0) {
                Delivery delivery = null;
                for (int j = 0; j < getDeliveriesList().size(); j++) {
                    if (getDeliveriesList().get(i).getItemAtIndex(id).getItemId() == id)
                        delivery = getDeliveriesList().get(i);
                }
                System.out.println(delivery.stringDeliveryIdItemQuantity(id));

            }

        }
    }
}
