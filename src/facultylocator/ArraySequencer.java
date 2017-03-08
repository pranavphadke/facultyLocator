/*
 *  Application designed by Pranav Phadke
 *  Free to share and modify with credit to the original author
 *  Used to track faculty members at TAMU-K
 */
package facultylocator;

/**
 *
 * @author Pranav Phadke
 */
public class ArraySequencer {
    public int[] getIntArray(int from, int to){
        int count=(to-from)+1;
        int[] opArray=new int[count];
        for (int i=0;i<count;i++){
            opArray[i]=from+i;
        }
        return opArray;
    }
}
