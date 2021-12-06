package Controller;
import Model.*;
import View.*;

public class Operations implements IList {
    private Deposit deposit;
    private Delivery delivery;

    public Operations(Deposit deposit) {
        this.deposit=deposit;
    }

    public Operations(Delivery delivery) {
        this.delivery=delivery;
    }

    //Getters

    public Deposit getDeposit() {
        return deposit;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    //Deposit Related

    public void clientDepositing() {
        Deposit deposit = getDeposit();
        if (deposit != null) {
            for (int i = 0; i < clientList.size(); i++) {
                if (deposit.getClientId() == clientList.get(i).getClientId()) {
                    clientList.get(i).addDeposit(deposit);
                }
            }
        } else {
            System.out.println("Não existe deposito");
        }
    }

    public void doDepositItemQuantity() {
        getDeposit().addingQuantityToItem();
    }

    public void workerDepositing() {
        Deposit deposit = getDeposit();
        if (deposit != null) {
            for (int i = 0; i < deposit.getWorkersIdList().size(); i++) {
                for (int j = 0; j < workerList.size(); j++) {
                    if (deposit.getWorkersIdList().get(i) == workerList.get(j).getWorkerId()) {
                        workerList.get(j).addDeposit(deposit);
                    }
                }
            }
        } else {
            System.out.println("Não existe deposito");
        }
    }

    public void doDepositPermissionCheck() {
        getDeposit().checkWorkerPermission();
    }

    //Delivery Related

    public void clientDelivering() {
        Delivery delivery = getDelivery();
        if (delivery != null){
            for (int i = 0; i < clientList.size(); i++) {
                if(delivery.getClientId() == clientList.get(i).getClientId()){
                    clientList.get(i).addDelivery(delivery);
                }
            }
        } else {
            System.out.println("Não existe entrega");
        }
    }

    public void doDeliveryItemQuantity() {
        getDelivery().subtractingQuantityToItem();
    }

    public void workerDelivering() {
        Delivery delivery = getDelivery();
        if (delivery != null) {
            for (int i = 0; i < delivery.getWorkersIdList().size(); i++) {
                for (int j = 0; j < workerList.size(); j++) {
                    if (delivery.getWorkersIdList().get(i) == workerList.get(j).getWorkerId()) {
                        workerList.get(j).addDelivery(delivery);
                    }
                }
            }
        } else {
            System.out.println("Não existe entrega");
        }
    }

    public void doDeliveryPermissionCheck() {
        getDelivery().checkWorkerPermission();
    }

    //Print Information

    public void doPrint(int option) {
        if (option == 1 && getDeposit() != null)
            getDeposit().getClient().printInfo();
        else if (option == 2 && getDeposit() != null)
            getDeposit().getClient().printAllItensInfo();
        else if (option == 3 && getDelivery() != null)
            getDelivery().getClient().printInfo();
        else if (option == 4  && getDelivery() != null)
            getDelivery().getClient().printAllItensInfo();
    }

}
