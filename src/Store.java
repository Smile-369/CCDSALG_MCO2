import java.io.IOException;

public class Store {
    static Substring kmer=new Substring();
    static BinarySearchTree bst= new BinarySearchTree();

    public static void main(String[] args) throws IOException {
        for(int i = 4;i<7;i++){
            for(int j = 5;j<8;j++){
                System.out.println("n="+i+" k="+j);
                String[] test = kmer.kmerArrayCreation(kmer.createRandomString((int) Math.pow(10, i)), j);
                bst.createFile(String.format("Output n=10^%d k=%d",i,j));
                bst.create(test);
                System.out.println(bst.contains(test[45]));
                bst.displayCounts(String.format("Output n=10^%d k=%d.txt",i,j));
                bst.destroy();
            }
        }
//        bst.displayInOrder();
//        System.out.println();

    }
}
