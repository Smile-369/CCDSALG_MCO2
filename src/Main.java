import java.io.IOException;

public class Main {
    static Substring kmer=new Substring();
    static BinarySearchTree bst= new BinarySearchTree();
    static Map<String,Integer> map;

    public static void main(String[] args) throws IOException {
        //run this 3 times and put the values
        for(int i = 4;i<7;i++){
            for(int j = 5;j<8;j++){
                System.out.println();
                String[] test = kmer.kmerArrayCreation(kmer.createRandomString((int) Math.pow(10, i)), j);
                Map<String, Integer> map2= new Map<>(test.length),map3 = new Map<>(test.length);
                System.out.println("n="+i+" k="+j);
                System.out.println();
                System.out.println("BST");
                bst.create(test);
                System.out.println(bst.contains(test[45]));
                System.out.println();
                System.out.println();
                System.out.println("fnv");
                map2.create(test,map2,2);
                System.out.println(map2.collisions);
                map2.get(test[45],2);
                map2.writeCountsToFile(String.format("Output n=10^%d k=%d fnv.txt",i,j),map2,test,2);

                System.out.println();
                System.out.println("crc");
                map3.create(test,map3,3);
                System.out.println(map3.collisions);
                map3.writeCountsToFile(String.format("Output n=10^%d k=%d crc.txt",i,j),map3,test,3);
                map3.get(test[45],3);

                bst.writeCountstoFile(String.format("Output n=10^%d k=%d bst.txt",i,j));
                bst.destroy();
            }
        }




    }
}
