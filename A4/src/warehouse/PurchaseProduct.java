package warehouse;

public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile("purchaseproduct.in");
        StdOut.setFile("purchaseproduct.out");
        Warehouse w1 = new Warehouse();
        int numProd = StdIn.readInt();
        for(int i = 0; i < numProd; i++){
            if(StdIn.readString().equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w1.addProduct(id, name, stock, day, demand);
            }
            else{
                int p1 = StdIn.readInt();
                int p2 = StdIn.readInt();
                int p3 = StdIn.readInt();
                w1.purchaseProduct(p2, p1, p3);
            }
        }
        StdOut.print(w1);
    }
}
