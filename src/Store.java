import java.io.IOException;

public class Store {
    static Substring kmer=new Substring();
    static BinarySearchTree bst= new BinarySearchTree();
    static Map<String,Integer> map;

    public static void main(String[] args) throws IOException {
        //run this 3 times and put the values
        for(int i = 4;i<7;i++){
            for(int j = 5;j<8;j++){
                System.out.println();
                String[] test = kmer.kmerArrayCreation(kmer.createRandomString((int) Math.pow(10, i)), j);
                Map<String, Integer> map1= new Map<>(test.length),map2= new Map<>(test.length),map3 = new Map<>(test.length);
                bst.createFile(String.format("Output n=10^%d k=%d",i,j));
                System.out.println("n="+i+" k="+j);
                System.out.println();
                System.out.println("BST");
                bst.create(test);
                System.out.println(bst.contains(test[45]));
                System.out.println();
                System.out.println("Built in");
                map1.create(test,map1,1);
                map1.get(test[45],1);
                System.out.println();
                System.out.println("KNV");
                map2.create(test,map2,2);
                map2.get(test[45],2);
                System.out.println();
                System.out.println("CRC");
                map3.create(test,map3,3);
                map3.get(test[45],3);

                bst.writeCountstoFile(String.format("Output n=10^%d k=%d.txt",i,j));
                bst.destroy();
            }
        }
        String input = Substring.createRandomString((int)Math.pow(10,4));
        String[] kmerArray = Substring.kmerArrayCreation(input, 5);


//        map.create(test);
//        System.out.println(map.get(test[1]));
//        bst.displayInOrder();
//        System.out.println();

    }
}
