package vzdornov.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    int bound = 20;
        AVLTree avlTree = new AVLTree();
        Random random = new Random();
        for (int i = 0; i < bound; i++) {
            int j = (int) random.nextInt(bound);
            avlTree.insert(j);
            System.out.print(j + ", ");
        }
        System.out.println();
        avlTree.scan();
        System.out.println();
        avlTree.remove(9);
        System.out.println();
        avlTree.scan();
    }
}
