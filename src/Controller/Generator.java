package Controller;
import Model.*;
import View.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Generator implements IList {

    public Generator() {
    }

    public Integer convertToInt(String string) {
        Integer converted = Integer.valueOf(string);
        return converted;
    }

    public Character convertToChar(String string) {
        Character converted = string.charAt(0);
        return converted;
    }

    public Client findClientById(int id) {
        Client clientFound = null;
        for (int i = 0; i < clientList.size(); i++) {
            if (id == clientList.get(i).getClientId())
                clientFound = clientList.get(i);
        }
        return clientFound;
    }

    public Worker findWorkerById(int id) {
        Worker workerFound = null;
        for (int i = 0; i < workerList.size(); i++) {
            if (id == workerList.get(i).getWorkerId())
                workerFound = workerList.get(i);
        }
        return workerFound;
    }

    public Local findLocalById(int id) {
        Local local = null;
        for (int i = 0; i < localList.size(); i++) {
            if (id == localList.get(i).getLocalId())
                local = localList.get(i);
        }
        return local;
    }

    public void registerWorker(String[] arguments) {
        int wordLength = arguments.length;
        int workerID = workerList.size() + 1;

        if (wordLength == 4) {
            Worker newlyWorker = new Worker(workerID, arguments[3], convertToChar(arguments[2]), arguments[1]);
            workerList.add(newlyWorker);
        } else if (wordLength == 5) {
            Worker newlyWorker = new Worker(workerID, arguments[3], arguments[4], convertToChar(arguments[2]), arguments[1]);
            workerList.add(newlyWorker);
        }
    }

    public void registerClient(String[] arguments) {
        int wordLength = arguments.length;

        int clientID = clientList.size() + 1;
        if (wordLength == 3){
            Client newlyClient = new Client(clientID, arguments[2]);
            newlyClient.setManager(findWorkerById(convertToInt(arguments[1])));
            newlyClient.checkManager();
            clientList.add(newlyClient);
        } else if (wordLength == 4){
            Client newlyClient = new Client(clientID, arguments[2], arguments[3]);
            newlyClient.setManager(findWorkerById(convertToInt(arguments[1])));
            newlyClient.checkManager();
            clientList.add(newlyClient);
        }
    }

    public void registerItem(String[] arguments) {
        int wordLength = arguments.length;

        System.out.println("Permissão do Item (em caso de vazio vira 'N'): ");
        Scanner scan = new Scanner(System.in);
        String permission = scan.nextLine(); //Escrever a permissão do item
        if (permission.isBlank()){ //Se não for escrito nada, a permissão vira 'N'
            permission = "N";
        }

        if (findClientById(convertToInt(arguments[1])) == null)
            throw new NullPointerException("Cliente inexistente.");

        if (wordLength == 3) {
            Item newlyItem = new Item(findClientById(convertToInt(arguments[1])).getItemListSize()+1, arguments[2], convertToChar(permission));
            findClientById(convertToInt(arguments[1])).addItem(newlyItem);
        }else if (wordLength == 4) {
            Item newlyItem = new Item(findClientById(convertToInt(arguments[1])).getItemListSize()+1, arguments[2], arguments[3], convertToChar(permission));
            findClientById(convertToInt(arguments[1])).addItem(newlyItem);
        }
    }

    public void registerLocal(String[] arguments) {
        int localID = localList.size() + 1;
        Local newlyLocal = new Local(localID, arguments[1]);
        for (int i = 0; i < localList.size(); i++) {
            if (localList.get(i).getName() == arguments[1])
                throw new IllegalArgumentException("Local existente.");
        }
        localList.add(newlyLocal);
    }

    public void registerDeposit(String[] arguments) {
        System.out.println("Inserir os Id's dos Funcionários: ");
        Scanner workersIdScan = new Scanner(System.in);
        String workersIdScanned = workersIdScan.nextLine();
        String[] tokens = workersIdScanned.split("\\s");
        ArrayList<Integer> workersIdList = new ArrayList<>();
        for (String str: tokens) {
            workersIdList.add(convertToInt(str));
        }

        ArrayList<Integer> itemStuffList = new ArrayList<>();
        System.out.println("Inserir o Id do Item e Quantidade dele: ");
        Scanner itemScan1 = new Scanner(System.in);
        String itemStuffScanned1 = itemScan1.nextLine();
        String[] numbers1 = itemStuffScanned1.split("\\s");

        System.out.println("Inserir o Id do Item e Quantidade dele (se não tem, deixar vazio): ");
        Scanner itemScan2 = new Scanner(System.in);
        String itemStuffScanned2 = itemScan2.nextLine();
        if (!itemStuffScanned2.isBlank()) {
            String[] numbers2 = itemStuffScanned2.split("\\s");
            for (String number: numbers2) {
                itemStuffList.add(convertToInt(number));
            }
        }
        for (String number: numbers1) {
            itemStuffList.add(convertToInt(number));
        }

        if (findClientById(convertToInt(arguments[1])) == null)
            throw new NullPointerException("Cliente inexistente.");

        if (findLocalById(convertToInt(arguments[2])) == null)
            throw new NullPointerException("Local inexistente.");

        ArrayList<Worker> workers = new ArrayList<>();
        for (Integer number: workersIdList) {
            workers.add(findWorkerById(number));
        }

        int depositId = findClientById(convertToInt(arguments[1])).getDepositsList().size() + 1;
        Deposit newlyDeposit = new Deposit(depositId, convertToInt(arguments[1]), convertToInt(arguments[2]));

        newlyDeposit.setWorkersIdList(workersIdList);

        for (int i = 0; i < itemStuffList.size(); i++) {
            if ((i & 1) == 0) {
                newlyDeposit.addItemId(itemStuffList.get(i));
            } else {
                newlyDeposit.addItemQuantity(itemStuffList.get(i));
            }
        }

        Operations operationsDeposit = new Operations(newlyDeposit);
        operationsDeposit.clientDepositing();
        operationsDeposit.workerDepositing();
        operationsDeposit.doDepositItemQuantity();
        operationsDeposit.doDepositPermissionCheck();
    }

    public void registerDelivery(String[] arguments) {
        System.out.println("Inserir os Id's dos Funcionários: ");
        Scanner workersIdScan2 = new Scanner(System.in);
        String workersIdScanned2 = workersIdScan2.nextLine();
        String[] tokens2 = workersIdScanned2.split("\\s");
        ArrayList<Integer> workersIdList2 = new ArrayList<>();
        for (String str: tokens2) {
            workersIdList2.add(convertToInt(str));
        }

        ArrayList<Integer> itemStuffList2 = new ArrayList<>();
        System.out.println("Inserir o Id do Item e Quantidade dele: ");
        Scanner itemScan1 = new Scanner(System.in);
        String itemStuffScanned1 = itemScan1.nextLine();
        String[] numbers1 = itemStuffScanned1.split("\\s");

        System.out.println("Inserir o Id do Item e Quantidade dele (se não tem, deixar vazio): ");
        Scanner itemScan2 = new Scanner(System.in);
        String itemStuffScanned2 = itemScan2.nextLine();
        if (!itemStuffScanned2.isBlank()) {
            String[] numbers2 = itemStuffScanned2.split("\\s");
            for (String number: numbers2) {
                itemStuffList2.add(convertToInt(number));
            }
        }
        for (String number: numbers1) {
            itemStuffList2.add(convertToInt(number));
        }

        if (findClientById(convertToInt(arguments[1])) == null)
            throw new NullPointerException("Cliente inexistente.");

        if (findLocalById(convertToInt(arguments[2])) == null)
            throw new NullPointerException("Local inexistente.");

        ArrayList<Worker> workers = new ArrayList<>();
        for (Integer number: workersIdList2) {
            workers.add(findWorkerById(number));
        }

        int deliveryId = findClientById(convertToInt(arguments[1])).getDeliveriesList().size() + 1;
        Delivery newlyDelivery = new Delivery(deliveryId, convertToInt(arguments[1]), convertToInt(arguments[2]));

        newlyDelivery = new Delivery(deliveryId, convertToInt(arguments[1]), convertToInt(arguments[2]));

        for (int i = 0; i < itemStuffList2.size(); i++) {
            if ((i & 1) == 0) {
                newlyDelivery.addItemId(itemStuffList2.get(i));
            } else {
                newlyDelivery.addItemSubtracting(itemStuffList2.get(i));
            }
        }

        for (Worker w: workers) {
            w.addDelivery(newlyDelivery);
        }
        findClientById(convertToInt(arguments[1])).addDelivery(newlyDelivery);
        newlyDelivery.subtractingQuantityToItem();

        //Operations operationsDelivery = new Operations(newlyDelivery);
        //operationsDelivery.clientDelivering();
        //operationsDelivery.workerDelivering();
        //operationsDelivery.doDeliveryItemQuantity();
        //operationsDelivery.doDeliveryPermissionCheck();
    }

    public void consultClient(String[] arguments) {
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getClientId() == convertToInt(arguments[1])) {
                clientList.get(i).printInfo();
            }
        }
    }

    public void consultItem(String[] arguments) {
        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getClientId() == convertToInt(arguments[1])) {
                for (int j = 0; j < findClientById(clientList.get(i).getClientId()).getItemListSize(); j++) {
                    if (findClientById(clientList.get(i).getClientId()).getItemsList().get(j).getItemId() == convertToInt(arguments[2])) {
                        int id = findClientById(clientList.get(i).getClientId()).getItemsList().get(j).getItemId();
                        findClientById(clientList.get(i).getClientId()).printItemInfo(id);
                    }
                }
            }
        }
    }

    public void consultWorker(String[] arguments) {
        for (int i = 0; i < workerList.size(); i++) {
            if (workerList.get(i).getWorkerId() == convertToInt(arguments[1])) {
                workerList.get(i).printInfo();
            }
        }
    }

    public void consultDelivery(String[] arguments) {
        //TODO: CE idClient idDelivery
        if (findClientById(convertToInt(arguments[1])) == null)
            throw new NullPointerException("Cliente inexistente.");

        Client client = findClientById(convertToInt(arguments[1]));
        for (int i = 0; i < client.getDeliveriesList().size(); i++) {
            if (client.getDeliveriesList().get(i).getDeliveryId() == convertToInt(arguments[2])) {
                System.out.println(client.getDeliveriesList().get(i).getLocal().getName());

                ArrayList<Integer> list = client.getDeliveriesList().get(i).getWorkersIdList();
                ArrayList<Worker> workers = new ArrayList<>();

                for (Integer number: list) {
                    workers.add(findWorkerById(number));
                }

                for (Worker w: workers) {
                    System.out.println(w.getPermission() + " " + w.getFullName());
                }

                /**
                for (int j = 0; j < client.getDeliveriesList().get(i).getWorkersIdList().size(); j++) {
                    Character permission = findWorkerById(client.getDeliveriesList().get(i).getWorkersIdList().get(i)).getPermission();
                    String name = findWorkerById(client.getDeliveriesList().get(i).getWorkersIdList().get(i)).getFullName();
                    System.out.println(permission + " " + name);
                }**/

                ArrayList<Integer> idList = new ArrayList<>();
                ArrayList<Integer> quantityList = new ArrayList<>();

                for (int j = 0; j < client.getDeliveriesList().size(); j++) {
                    for (int k = 0; k < client.getDeliveriesList().get(i).getItensIdList().size(); k++) {
                        idList.add(client.getDeliveriesList().get(i).getItensIdList().get(k));
                    }

                    for (int k = 0; k < client.getDeliveriesList().get(i).getItensSubtractingList().size(); k++) {
                        quantityList.add(client.getDeliveriesList().get(i).getItensSubtractingList().get(k));
                    }

                    for (int k = 0; k < idList.size(); k++) {
                        System.out.println(idList.get(i) + " " + quantityList.get(i) + " " + client.getDeliveriesList().get(i).getLocal().getName());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Generator program = new Generator();

//      try {
//            program.registerWorker(args1);
//      } catch (Exception e) {
//            System.out.println(e.getMessage());
//      }

        program.registerWorker(new String[]{"RF", "Gestor", "N", "Bruce", "L."});
        program.registerClient(new String[]{"RC", "1", "Alice", "M."});
        program.registerItem(new String[]{"RI", "1", "Batatas", "fritas"});//Escrever a permissão do Item no Terminal
        program.registerLocal(new String[]{"RL", "Lisboa"});
        program.registerWorker(new String[]{"RF", "Condutor", "P", "Xu"});
        program.registerWorker(new String[]{"RF", "Carregador", "S", "Sérgio"});

        //program.registerDeposit(new String[]{"RD", "1", "1"});

        //program.registerDelivery();

        //System.out.println("---Consultar Cliente---");
        //program.consultClient(new String[]{"CC", "1"});
        //System.out.println("---Consultar Item---");
        //program.consultItem(new String[]{"CI", "1", "1"});
        //System.out.println("---Consultar Funcionário---");
        //program.consultWorker(new String[]{"CF", "1"});
        //System.out.println("---Consultar Funcionário---");
        //program.consultWorker(new String[]{"CF", "2"});

        program.registerDelivery(new String[]{"RE", "1", "1"});

        program.consultDelivery(new String[]{"CE", "1", "1"});
    }
}
