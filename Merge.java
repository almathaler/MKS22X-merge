import java.util.Arrays;
public class Merge{
  public static void main(String[] args){
    int[] data = {3, 2, 1, 5, 4, 6, 3, 9, 0};
    mergesort(data);
    System.out.println("Now your data: " + Arrays.toString(data));
  }
  /*sort the array from least to greatest value. This is a wrapper function*/
  public static void mergesort(int[]data){
    mergesortHelp(data, 0, data.length-1);
  }

  private static void mergesortHelp(int[] data, int lo, int hi){
    if (lo < hi){ //so that internal msH doesn't do anything once lo == hi and array is size 1
      int pivot;
      if (data.length % 2 == 0){
        pivot = (data.length / 2); // so in array of size 4, pivot will be index 2
      }else{
        pivot = (data.length / 2) + 1; // in array of size 5, pivot witll be index 3 (right will be shorter than left by 1)
      }
      int[] tempA = Arrays.copyOfRange(data, lo, pivot); //pivot is excluded
      int[] tempB = Arrays.copyOfRange(data, pivot, hi); //pivot included
      mergesortHelp(tempA, 0, tempA.length-1); //give indecies in terms of tempA, bc tempA will be considered data within that call
      mergesortHelp(tempB, 0, tempB.length-1);
      //now merge the two arrays that have been sorted 3 2 1 0 1 2 3 is how this recursion looks
      //have 3 counters
      int c = 0; //index of data
      int i = 0; //index of tempA
      int k = 0; //index of tempB
      while (c<data.length){
          System.out.println("Building back up the array. TempA: " + Arrays.toString(tempA) + "\t tempB: " + Arrays.toString(tempB));
          System.out.println("data: " + Arrays.toString(data));
          try{
            if (tempA[i] < tempB[k]){ //if a is smaller, move pointer up one and compare next w pointer of tempB that is in same place
              data[c] = tempA[i];     //ofc before u move add this smaller element to data[c], and then increase data c too
              i++;
              c++;
            }else{                    //if they're = or b is smaller, move pointer of b up one and compare next w pointer of tempA that is in same place
              data[c] = tempB[k];     //ofc copy to data before u change the values
              k++;
              c++;
            } //if the if statement breaks the code, see which part is faulty
          }catch (ArrayIndexOutOfBoundsException e){ //hit end of one array, so fill data w what's left of the other
            if (i >= tempA.length){ //if u ran out of A vals, that means everything left in B is larger than A and can now be added
              data[c] = tempB[k];
              c++;
              k++;
            }else if (k >= tempB.length){ //if u ran out of B vals, add all left from A back into data in right order
              data[c] = tempA[i];
              c++;
              i++;
            }
          } //this will occur again and again in the loop until u fill up data, will happen at same time as u run out of left values
      }
    }
  }
  //end
}
