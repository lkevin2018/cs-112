package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile("betteraddproduct.in");
        StdOut.setFile("betteraddproduct.out");
        Warehouse w1 = new Warehouse();
        int numProd = StdIn.readInt();
        for(int i = 0; i < numProd; i++){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w1.betterAddProduct(id, name, stock, day, demand);
        }
        StdOut.print(w1);
    }
}
