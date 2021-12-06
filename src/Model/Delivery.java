package Model;
import View.*;
import java.util.ArrayList;

/**
 * Classe que representa a Entrega
 */
public class Delivery implements IList {
    private int deliveryId;
    private int clientId;
    private int localId;
    private ArrayList<Integer> workersIdList = new ArrayList<>();
    private ArrayList<Integer> itensIdList = new ArrayList<>();
    private ArrayList<Integer> itensSubtractingList = new ArrayList<>();

    /**
     * Constructor de Entrega
     * @param deliveryId id da entrega
     * @param clientId id do cliente
     * @param localId id do local
     */
    public Delivery(int deliveryId, int clientId, int localId) {
        this.deliveryId = deliveryId;
        this.clientId=clientId;
        this.localId=localId;
    }

    //Getters
    /**
     * Getter id da entrega
     * @return id
     */
    public int getDeliveryId() {
        return deliveryId;
    }

    /**
     * Getter id do cliente
     * @return id
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Getter id do local
     * @return id
     */
    public int getLocalId() {
        return localId;
    }

    //WorkerList

    /**
     * Retorna a lista de id dos funcionários
     * @return lista
     */
    public ArrayList<Integer> getWorkersIdList() {
        return workersIdList;
    }

    /**
     * Adicionar á lista
     * @param workerId um id
     */
    public void addWorkerId(int workerId){
        getWorkersIdList().add(workerId);
    }

    //ItemList

    /**
     * Retorna a lista de ids ded itens
     * @return
     */
    public ArrayList<Integer> getItensIdList() {
        return itensIdList;
    }

    /**
     * Retorna uma lista com números para subtrair
     * @return
     */
    public ArrayList<Integer> getItensSubtractingList() {
        return itensSubtractingList;
    }

    /**
     * Adiciona á lista
     * @param itemId id
     */
    public void addItemId(int itemId){
        getItensIdList().add(itemId);
    }

    /**
     * Adiciona á lista
     * @param quantity quantidade
     */
    public void addItemSubtracting(int quantity) {
        getItensSubtractingList().add(quantity);
    }

    //Getter Client & Local

    /**
     * Retorna o cliente com o id de cliente dado no Constructor
     * @return cliente
     */
    public Client getClient() {
        int id = getClientId();
        Client c = null;
        for (Client client : clientList) {
            if (id == client.getClientId()) {
                c = client;
            }
        }
        return c;
    }
    /**
     * Retorna o Local com o id de Local dado no Constructor
     * @return local
     */
    public Local getLocal() {
        int id = getLocalId();
        Local l = null;
        for (int i = 0; i < localList.size(); i++) {
            if (id == localList.get(i).getLocalId()){
                l = localList.get(i);
            }
        }
        return l;
    }

    //Specific Worker

    /**
     * Retorna o Funcionário no index dado
     * @param index index
     * @return funcionário
     */
    public Worker getWorkerAtIndex(int index) {
        Worker w = null;
        for (int i = 0; i < workerList.size(); i++) {
            if (workersIdList.get(index) == workerList.get(i).getWorkerId()){
                w = workerList.get(i);
            }
        }
        return w;
    }

    //Specific Item

    /**
     *
     * @param index
     * @return
     */
    public Item getItemAtIndex(int index) {
        Item i = null;

        for (int j = 0; j < getClient().getItemListSize(); j++) {
            if (itensIdList.get(index) == getClient().getItemsList().get(j).getItemId()){
                i = getClient().getItemsList().get(j);
            }
        }
        return i;
    }

    public void subtractingItemQuantity(Item subtractedItem, int subtractingQuantity) {
        int previousQuantity = subtractedItem.getQuantity();
        subtractedItem.setQuantity(previousQuantity - subtractingQuantity);
    }

    public Item getItemById(int itemId) {
        Item item = null;
        Client client = getClient();
        for (int i = 0; i < client.getItemListSize(); i++) {
            if (itemId == client.getItemsList().get(i).getItemId())
                item = client.getItemsList().get(i);
        }
        return item;
    }

    public void subtractingQuantityToItem() {
        for (int i = 0; i < getItensIdList().size(); i++) {
            subtractingItemQuantity(getItemAtIndex(i), getItensSubtractingList().get(i));
        }
    }

    //Specific toString()

    public String stringDeliveryIdLocalName() {
        return "  " + getDeliveryId() + " (" + getLocal().getName() + ")";
    }

    public String stringDeliveryIdItemQuantity(int itemId) {
        int index = 0;
        for (int i = 0; i < getItensIdList().size(); i++) {
            if (itemId == getItensIdList().get(i))
                index = i;
        }
        return "  " + getDeliveryId() + " " + getItensSubtractingList().get(index);
    }

    //Restrictions

    public ArrayList<Item> getItens() {
        ArrayList<Item> list = new ArrayList<>();
        for (int i = 0; i < getItensIdList().size(); i++) {
            list.add(getItemAtIndex(i));
        }
        return list;
    }

    public ArrayList<Character> checkListOfPermissions() {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < getItens().size(); i++) {
            list.add(getItens().get(i).getPermission());
        }
        return list;
    }

    public ArrayList<Character> checkDriver() {
        int countDrivers = 0;
        for (int i = 0; i < workersIdList.size(); i++) {
            if (getWorkerAtIndex(i).getCategory() == "Condutor")
                countDrivers++;
        }

        //TODO:

        ArrayList<Character> list = checkListOfPermissions();
        ArrayList<Character> leftoverItensPermission = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) == 'N' && list.get(i) == 'P' )
                leftoverItensPermission.add(list.get(i));
        }
        return leftoverItensPermission;
    }

    //Acontece depois do checkDriver()
    public ArrayList<Character> checkLoader() {
        ArrayList<Character> list = checkDriver();

        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i) == 'N' && list.get(i) == 'S' )
                    list.remove(i);
            }
        }
        return list;
    }

    public void checkWorkerPermission() {
        ArrayList<Character> list = checkLoader();

        if (list.size() != 0) {
            throw new IllegalStateException("Carregador sem permissões"); //error print - não têm condições
        }
    }

}
