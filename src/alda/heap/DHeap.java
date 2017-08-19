//Maja Lund, malu9669


package alda.heap;

//DHeap class - based on Mark Allan Weiss' code for binary heap.
//Reference:
//Mark Allen Weiss. Data Structures and Algorithm Analysis in Java.
//Pearson Education, third edition, 2012.
//
//Note from Weiss' code:
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

import java.util.Arrays;

//Note from Mark Allen Weiss' code:
/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{
    //DEFAULT_CAPACITY, currentSize and array are from Weiss' code.
    //Weiss had this variables in the middle of the code, I moved them to the top.

    private static final int DEFAULT_CAPACITY = 10;

    private int numOfChild;      // Not a part of Weiss' code
    private int currentSize;    // Number of elements in heap (Note from Weiss)
    private AnyType [ ] array; // The heap array (Note from Weiss)

    /**
     * Empty constructor creates a binary heap.
     */
    public DHeap( )
    {
        this( 2 );
    }

    //I changed "capacity" to "d". Note from Weiss' code:
    /**
     * Construct the binary heap.
     * //@param capacity the capacity of the binary heap.
     */
    public DHeap( int d ) {

        if(d < 2){
            throw new IllegalArgumentException();
        }
        this.numOfChild = d;
        this.currentSize = 0;
        array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1 ];
    }

    //Constructor from Weiss' code, never used here.
    //Note from Weiss' code:
    /**
     * Construct the binary heap given an array of items.
     */
//    public BinaryHeap( AnyType [ ] items )
//    {
//        currentSize = items.length;
//        array = (AnyType[]) new Comparable[ ( currentSize + 2 ) * 11 / 10 ];
//
//        int i = 1;
//        for( AnyType item : items )
//            array[ i++ ] = item;
//        buildHeap( );
//    }

    //Note from Weiss' code:
    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x ) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1);
        }
        if (currentSize == 0) {
            array[1] = x;
            currentSize++;
        } else{
            // Percolate up
            int hole = ++currentSize;
            for (; x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)) {
                array[hole] = array[parentIndex(hole)];
                if(parentIndex(hole) == 1){
                    array[1] = x;
                    return;
                }
            }
            array[hole] = x;
        }
    }

    //Not a part of Weiss' code.
    //Should be private, now package private due to testing.
    int parentIndex(int index){

        if(index <= 1){
            throw new IllegalArgumentException("The root node does not have a parent!");
        }
        return (index + numOfChild -2) / numOfChild;
    }

    //Not a part of Weiss' code.
    //Should be private, now package private due to testing.
    int firstChildIndex(int index){
        if(index < 1){
            throw new IllegalArgumentException("The index does not exist!");
        }
        return index * numOfChild - (numOfChild-2);
    }

    //Not a part of Weiss' code.
    private int lastChildIndex(int index){
        if(firstChildIndex(index)>currentSize){
            throw new IllegalArgumentException("The index does not exist!");
        }
        int i = firstChildIndex(index) + numOfChild-1;
        return i <= currentSize? i: currentSize;
    }

    //This is Weiss' code, I have not changed it.
    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    //I added the message "underFlow", except that I have not changed anything in the method.
    //Note from Weiss' code:
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty() )
            throw new UnderflowException("underFlow");
        return array[ 1 ];
    }

    //Added the marked row and the message "underFlow", except that I have not changed anything in the method.
    //Note from Weiss' code:
    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException("underFlow");

        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        array[currentSize+1] = null; //Added by me.
        percolateDown( 1 );

        return minItem;
    }

    //Method from Weiss' code, never used here.
    //Note from Weiss' code:
    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
//    private void buildHeap( )
//    {
//        for( int i = currentSize / 2; i > 0; i-- )
//            percolateDown( i );
//    }

    //I have not changed anything in this method, it is Weiss' code.
    //Note from Weiss' code:
    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    //I have not changed anything in this method, it is Weiss' code.
    //Note from Weiss' code:
    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    //Not a part of Weiss' code
    private int findMinChildIndex(int hole){

        int childFirst = firstChildIndex(hole);
        int childLast = lastChildIndex(hole);
        int minChildIndex = childFirst;

        for(int i = childFirst; i < childLast; i++) {

            if (array[i + 1].compareTo(array[minChildIndex]) < 0) {
                minChildIndex = i + 1;
            }
        }
        return minChildIndex;
    }

    //Note from Weiss' code:
    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole ) {

        int child;
        AnyType tmp = array[ hole ];

        for(; firstChildIndex(hole) <= currentSize; hole = child )
        {
            child = findMinChildIndex(hole);
            if(array[child].compareTo(tmp) < 0){
                array[hole] = array[child];
            }
            else{
                break;
            }
        }
        array[ hole ] = tmp;
    }

    // Test program
    public static void main( String [ ] args )
    {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }

    //Not a part of Weiss' code
    public int size(){ return currentSize; }

    //Only used for testing, not a part of Weiss' code.
    AnyType get(int index){
        return array[index]; }

    //Not a part of Weiss' code
    public String toString(){
        return Arrays.toString(array);
    }
}