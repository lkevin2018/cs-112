package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile("restock.in");
        StdOut.setFile("restock.out");
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
                w1.restockProduct(StdIn.readInt(), StdIn.readInt());
            }
        }
        StdOut.print(w1);
    }
}
