package Model;
import View.*;
import java.util.ArrayList;

public class Deposit implements IList {
    private int depositId;
    private int clientId;
    private int localId;
    private ArrayList<Integer> workersIdList = new ArrayList<>();
    private ArrayList<Integer> itensIdList = new ArrayList<>();
    private ArrayList<Integer> itensQuantityList = new ArrayList<>();

    public Deposit(int depositId, int clientId, int localId) {
        this.depositId=depositId;
        this.clientId=clientId;
        this.localId=localId;
    }

    //Getters

    public int getDepositId() {
        return depositId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getLocalId() {
        return localId;
    }

    //WorkerList

    public void setWorkersIdList(ArrayList<Integer> workersIdList) {
        this.workersIdList = workersIdList;
    }

    public ArrayList<Integer> getWorkersIdList() {
        return workersIdList;
    }

    public void addWorkerId(int workerId){
        getWorkersIdList().add(workerId);
    }

    //ItemList

    public ArrayList<Integer> getItensIdList() {
        return itensIdList;
    }

    public ArrayList<Integer> getItensQuantityList() {
        return itensQuantityList;
    }

    public void addItemId(int itemId){
        getItensIdList().add(itemId);
    }

    public void addItemQuantity(int quantity) {
        getItensQuantityList().add(quantity);
    }

    //Getter Client & Local

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

    public Item getItemAtIndex(int index) {
        Item i = null;

        if (getClient() == null)
            throw new NullPointerException("Cliente inexistente.");

        for (int j = 0; j < getClient().getItemListSize(); j++) {
            if (itensIdList.get(index) == getClient().getItemsList().get(j).getItemId()){
                i = getClient().getItemsList().get(j);
            }
        }
        return i;
    }

    public void setItemQuantity(Item settingItem, int addingQuantity) {
        if (settingItem == null)
            throw new NullPointerException("Item inexistente.");
        int previousQuantity = settingItem.getQuantity();
        settingItem.setQuantity(previousQuantity + addingQuantity);
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

    public void addingQuantityToItem() {
        for (int i = 0; i < getItensIdList().size(); i++) {
            setItemQuantity(getItemAtIndex(i), getItensQuantityList().get(i));
        }
    }

    //Specific toString()

    public String stringDepositIdLocalName() {
        if (getLocal().getName() == null)
            throw new NullPointerException("Local Inexistente.");
        return "  " + getDepositId() + " (" + getLocal().getName() + ")";
    }

    public String stringDepositIdItemQuantity(int itemId) {
        int index = 0;
        for (int i = 0; i < getItensIdList().size(); i++) {
            if (itemId == getItensIdList().get(i))
                index = i;
        }

        return "  " + getDepositId() + " " + getItensQuantityList().get(index);
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
        int countNumberOfDrivers = 0;
        int index = 0;
        for (int i = 0; i < workersIdList.size(); i++) {
            if (getWorkerAtIndex(i).getCategory().equals("Condutor"))
                countNumberOfDrivers++;
            index = i;
        }

        if (countNumberOfDrivers == 0)
            throw new IllegalStateException("");//error print quando não tem condutor

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

