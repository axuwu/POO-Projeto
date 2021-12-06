package Model;
import java.util.ArrayList;

/**
 * Classe que  representa o Funcionário
 */
public class Worker {
    private final int workerId;
    private final String name;
    private final String surname;
    private final char permission;
    private final String category;
    private ArrayList<Deposit> deposits = new ArrayList<>();
    private ArrayList<Delivery> deliveries = new ArrayList<>();

    /**
     * Constructor do Funcionário
     * @param workerId id
     * @param name nome
     * @param permission permissão
     * @param category categoria
     */
    public Worker(int workerId, String name, char permission, String category) {
        this.workerId = workerId;
        this.name = name;
        this.surname = null;
        checkPermission(permission);
        this.permission = permission;
        checkCategory(category);
        this.category = category;
        checkRestrictions();
    }

    /**
     *  Constructor do Funcionário
     * @param workerId id
     * @param name nome
     * @param surname apelido
     * @param permission permissão
     * @param category categoria
     */
    public Worker(int workerId, String name, String surname, char permission, String category) {
        this.workerId = workerId;
        this.name = name;
        this.surname = surname;
        checkPermission(permission);
        this.permission = permission;
        checkCategory(category);
        this.category = category;
        checkRestrictions();
    }

    //Restrictions
    /**
     * Verifica a permissão
     * @param permission
     */
    private void checkPermission(char permission) {
        if (permission != 'N' && permission != 'S' && permission != 'P'){
            throw new IllegalArgumentException("Permissão inexistente.");
        }
    }

    /**
     * Verifica a categoria
     * @param category
     */
    public void checkCategory(String category) {
        if (!category.equals("Condutor") && !category.equals("Carregador") && !category.equals("Gestor")){
            throw new IllegalArgumentException("Categoria inexistente.");
        }
    }

    /**
     * Verifica se as restrições estão certas (ver tabela do enunciado)
     */
    public void checkRestrictions() {
        if (getCategory().equals("Condutor")) {
            if (getPermission() != 'N' && getPermission() != 'P')
                throw new IllegalCallerException("Categoria com Permissão errada.");
        } else if (getCategory().equals("Carregador")) {
            if (getPermission() != 'N' && getPermission() != 'S')
                throw new IllegalArgumentException("Categoria com Permissão errada.");
        } else if (getCategory().equals("Gestor")) {
            if (getPermission() != 'N')
                throw new IllegalArgumentException("Categoria com Permissão errada.");
        }
    }

    //Getters & Setters
    /**
     * Getter do id
     * @return id
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * Getter nome completo
     * @return nome
     */
    public String getFullName() {
        String str;
        if (surname == null) {
            str = name;
        }else {
            str = name + " " + surname;
        }
        return str;
    }

    /**
     * Getter da permissão
     * @return permissão
     */
    public char getPermission() {
        return permission;
    }

    /**
     * Getter da categoria
     * @return categoria
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter nome da permissão
     * @return nome da permissão
     */
    public String getPermissionName() {
        String string = null;
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

        return string;
    }

    //Specific toString()
    /**
     * ToString do nome, categoria e nome da permissão
     * @return string info
     */
    public String stringNameCategoryPermission() {
        return getFullName() + "\n" + getCategory() + "\n" + getPermissionName();
    }

    //DepositListRelated

    /**
     * Getter da lista de depósitos
     * @return lista
     */
    public ArrayList<Deposit> getDepositsList() {
        return deposits;
    }

    /**
     * Adicionar um depósito á lista
     * @param deposit depósito
     */
    public void addDeposit(Deposit deposit) {
        getDepositsList().add(deposit);
    }

    //DeliveryListRelated
    /**
     * Getter da lista de entregas
     * @return lista
     */
    public ArrayList<Delivery> getDeliveriesList() {
        return deliveries;
    }

    /**
     * Adicionar uma entrega á lista
     * @param delivery entrega
     */
    public void addDelivery(Delivery delivery) {
        getDeliveriesList().add(delivery);
    }

    //Print Information

    /**
     * Imprime informação
     */
    public void printInfo() {
        System.out.println(stringNameCategoryPermission());

        System.out.println("Depósitos:");
        if (getDepositsList().size() != 0) {
            for (int i = 0; i < getDepositsList().size(); i++) {
                for (int j = 0; j < getDepositsList().get(i).getClient().getItemListSize(); j++) {
                    System.out.println(" " + getDepositsList().get(i).getClientId() +
                            " " + getDepositsList().get(i).getClient().getItemsList().get(j).getItemId() +
                            " (" + getDepositsList().get(i).getLocal().getName() +
                            ") " + getDepositsList().get(i).getClient().getFullName());
                }
            }
        }

        System.out.println("Entregas");
        if (getDeliveriesList().size() != 0) {
            for (int i = 0; i < getDeliveriesList().size(); i++) {
                for (int j = 0; j < getDeliveriesList().get(i).getClient().getItemListSize(); j++) {
                    System.out.println(" " + getDeliveriesList().get(i).getClientId() +
                            " " + getDeliveriesList().get(i).getClient().getItemsList().get(j).getItemId() +
                            " (" + getDeliveriesList().get(i).getLocal().getName() +
                            ") " + getDeliveriesList().get(i).getClient().getFullName());
                }
            }
        }
    }
}
