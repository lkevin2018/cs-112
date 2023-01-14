package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("deleteproduct.in");
        StdOut.setFile("deleteproduct.out");
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
                w1.deleteProduct(StdIn.readInt());
            }
        }
        StdOut.print(w1);
    }
}
