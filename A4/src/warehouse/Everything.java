package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile("everything.in");
        StdOut.setFile("everything.out");
        Warehouse w1 = new Warehouse();
        int numProd = StdIn.readInt();
        for(int i = 0; i < numProd; i++){
            String s1 = StdIn.readString();
            if(s1.equals("add")){
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();
                w1.addProduct(id, name, stock, day, demand);
            }
            else if(s1.equals("purchase")){
                int p1 = StdIn.readInt();
                int p2 = StdIn.readInt();
                int p3 = StdIn.readInt();
                w1.purchaseProduct(p2, p1, p3);
            }
            else if(s1.equals("delete")){
                int d1 = StdIn.readInt();
                w1.deleteProduct(d1);
            }
            else if(s1.equals("restock")){
                int r1 = StdIn.readInt();
                int r2 = StdIn.readInt();   
                w1.restockProduct(r1, r2);
            }
        }
        StdOut.print(w1);
    }
}
