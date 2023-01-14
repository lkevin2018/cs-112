package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addproduct.in");
        StdOut.setFile("addproduct.out");
        Warehouse w1 = new Warehouse();
        int numProd = StdIn.readInt();
        for(int i = 0; i < numProd; i++){
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();
            w1.addProduct(id, name, stock, day, demand);
        }
        StdOut.print(w1);
    }
}
