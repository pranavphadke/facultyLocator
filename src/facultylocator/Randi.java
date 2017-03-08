/*
*   Copyright 2016-2017 Pranav Phadke

*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at

*       http://www.apache.org/licenses/LICENSE-2.0

*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License. 
*/
package facultylocator;

/**
 *
 * @author Pranav Phadke
 */
public class Randi extends FindNum{
    static int minimum=1,maximum,randNum;
    // Constructor to get count number of random numbers between min and max
    public int[] getRandiNums(int min, int max,int count){
        int[] randSet=new int[count];
        int num;
        for (int i=0;i<count;i++){
            num=getRandiNum(min,max);
            while (findNum(randSet,num)==true){
                num=getRandiNum(min,max);
            }
            randSet[i]=num;
        }
        return randSet;
    }
    
    // Constructor to get a random number between min and max
    public int getRandiNum(int min,int max){
        minimum=min;
        maximum=max;
        setRandNum();
        return randNum;
    }
    
    // Constructor to get a random number between 1 and max
    public int getRandi(int max){
        maximum=max;
        setRandNum();
        return randNum;
    }
    
    // get a random number between minimum and maximum
    public void setRandNum(){
        randNum=minimum + (int)(Math.random() * ((maximum - minimum) + 1));
    }
//    public static void main(String[] args) {
//        int[] num=Randi(1,5,3);
//        System.out.printf("Random number generated between 1 and 5 is: %s %n",num[0]);
//        System.out.printf("Random number generated between 1 and 5 is: %s %n",num[1]);
//        System.out.printf("Random number generated between 1 and 5 is: %s %n",num[2]);
//    }
    
    // identify if number is duplicate

}
