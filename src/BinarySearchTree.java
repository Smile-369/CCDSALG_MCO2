import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BinarySearchTree {
    long startTime = System.currentTimeMillis();

    long endTime;
    private class Node {
        String value;

        int count;
        Node left, right;

        public Node(String value) {
            this.value = value;
            this.count=1;
        }
    }

    private Node root;

    public void create(String[] values) {
        startTime = System.currentTimeMillis();

        for (String value : values) {
            insert(value);
        }
        endTime= System.currentTimeMillis();
        long elapsedTime=endTime-startTime;
        System.out.println("Create "+elapsedTime+"ms");
    }
    public void create() {
        root = null;
    }


    public void destroy() {
        root = null;
    }

    public void insert(String value) {
        root = insert(root, value);
    }

    private Node insert(Node node, String value) {


        if (node == null) {
            return new Node(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0)  {
            node.right = insert(node.right, value);
        }else{
            node.count++;
        }



        return node;
    }

    public boolean contains(String value) {
        startTime = System.nanoTime();
        return contains(root, value);
    }

    private boolean contains(Node node, String value) {
        if (node == null) {
            return false;
        }

        if (value.equals(node.value)) {
            endTime= System.nanoTime();
            long elapsedTime=endTime-startTime;
            System.out.println("search "+elapsedTime+"ns");
            return true;
        } else if (value.compareTo(node.value) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    public void writeCountstoFile(String filePath) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            writeCountsToFile(root,out);
            out.close();
        } catch (IOException e) {
        }

    }
    public static void createFile(String Name) {
        try {
            File file = new File(String.format("%s.txt",Name));
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private void writeCountsToFile(Node node,BufferedWriter out) throws IOException {

        if (node == null) {
            return;
        }
        writeCountsToFile(node.left,out);
            out.write(node.value + ": " + node.count+"\n");
        writeCountsToFile(node.right,out);

    }

}




