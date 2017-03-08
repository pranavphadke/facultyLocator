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
public class FindNum extends ArraySequencer{
    int loc=0;
    public boolean findNum(int[] set, int num){
        boolean exist=false;
        for(Integer j=0;j<set.length;j++){
                if (num==set[j]){
                    exist=true;
                    loc=j;
                    break;
                }
        }
        return exist;
    }
    public int getNumLoc(){
        return loc;
    }
    public int [] removeNum(int[] set,int num){
        int[] opArray=new int[set.length-1];
        int j=0;
        for (int i=0;i<set.length-1;i++){
            if(set[i]!=num){
                opArray[j]=set[i];
                j++;
            }
        }
        return opArray;
    }
    
}
