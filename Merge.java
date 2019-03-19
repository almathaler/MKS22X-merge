import java.util.Arrays;
public class Merge{
  public static void main(String[]args){
    int[] data = {3, 4, 2, 5, 1, 8 , 6, 5, 9, 10};
    mergesortOpt(data);
    System.out.println(Arrays.toString(data));
    /*
    System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
    int[]MAX_LIST = {1000000000,500,10};
    for(int MAX : MAX_LIST){
      for(int size = 31250; size < 2000001; size*=2){
        long qtime=0;
        long btime=0;
        //average of 5 sorts.
        for(int trial = 0 ; trial <=5; trial++){
          int []data1 = new int[size];
          int []data2 = new int[size];
          for(int i = 0; i < data1.length; i++){
            data1[i] = (int)(Math.random()*MAX);
            data2[i] = data1[i];
          }
          long t1,t2;
          t1 = System.currentTimeMillis();
          mergesort(data2);
          t2 = System.currentTimeMillis();
          qtime += t2 - t1;
          t1 = System.currentTimeMillis();
          Arrays.sort(data1);
          t2 = System.currentTimeMillis();
          btime+= t2 - t1;
          if(!Arrays.equals(data1,data2)){
            System.out.println("FAIL TO SORT!");
            System.exit(0);
          }
        }
        System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
      }
      System.out.println();
    }
    */
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

      //System.out.println("data: " + Arrays.toString(data) + "\t pivot: " + pivot);

      int[] tempA = Arrays.copyOfRange(data, lo, pivot); //pivot is excluded
      int[] tempB = Arrays.copyOfRange(data, pivot, (hi+1)); //pivot included

      //System.out.println("tempA: " + Arrays.toString(tempA));
      //System.out.println("tempB: " + Arrays.toString(tempB));

      mergesortHelp(tempA, 0, tempA.length-1); //give indecies in terms of tempA, bc tempA will be considered data within that call
      mergesortHelp(tempB, 0, tempB.length-1);

      //System.out.println("Sorted tempA: " + Arrays.toString(tempA));
      //System.out.println("Sorted tempB: " + Arrays.toString(tempB));

      //now merge the two arrays that have been sorted 3 2 1 0 1 2 3 is how this recursion looks
      //have 3 counters

      //System.out.println("Merging sorted temps back into data: ");

      int c = 0; //index of data
      int i = 0; //index of tempA
      int k = 0; //index of tempB
      while (c<data.length){
          //System.out.println("Building back up the array. TempA: " + Arrays.toString(tempA) + "\t tempB: " + Arrays.toString(tempB));
          //System.out.println("data: " + Arrays.toString(data));
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
  //optimized mergesort
  public static void mergesortOpt(int[] data){
    int[] temp = Arrays.copyOf(data, data.length);
    mergesortOH(data, temp, 0, data.length - 1);
  }
  private static void mergesortOH(int[]data, int[]temp, int lo, int hi){
    if (lo < hi){
      //System.out.println("Entered a new mOH call w lo: " + lo + " and hi: " + hi);
      int pivot;
      if ((hi-lo) % 2 == 0){
        pivot = ((hi+lo) / 2); // so in array of size 4, pivot will be index 2
      }else{
        pivot = ((hi+lo) / 2) + 1; // in array of size 5, pivot witll be index 3 (right will be shorter than left by 1)
      }
      //System.out.println("The pivot: " + pivot);
      //System.out.println("Will mergesort sections " + lo + " to " + (pivot-1) + " and " + pivot +  " to " + hi);
      //
      mergesortOH(temp, data, lo, pivot-1);
      mergesortOH(temp, data, pivot, hi); //right side will be +1 bigger than left is array is odd
      //
      System.out.println("\nMerging the 2 sorted blocks of the temp array: " + lo + ", " + (pivot-1) + " & " + pivot + ", " + hi);
      System.out.println("Temp array parts to be copied into data: " + Arrays.toString(temp));
      System.out.println("Data before copying: " + Arrays.toString(data));
      mergeOpt(data, temp, lo, pivot, hi);
      /*
      int c = lo; //fill later, keep below hi of this call
      int i = lo; //from i - k and k - hi have been sorted
      int k = pivot; //read up on both sections simultaneously
      while (c<=hi){
        // do same check as normal mergesort however the catch is like this:
        // if i=pivot, fill rest of data up until hi w k values
        // if k = hi, fill rest of data up with i values until pivot
        if (k > hi){ //should always be writing into what is considered data
          System.out.println("k  is > hi: " + k + ", hi: " + hi);
          data[c] = temp[i];
          i++;
          c++;
          System.out.println("Written on data: " + Arrays.toString(data));
        }else if (i > pivot){
          System.out.println("i  is > pivot: " + i + ", pivot: " + pivot);
          data[c] = temp[k];
          k++;
          c++;
          System.out.println("Written on data: " + Arrays.toString(data));
        }else if (temp[k] < temp[i]){
          System.out.println("Indecies of k and i: " + k + ", " + i);
          System.out.println("temp[k] < temp[i]: " + temp[k] + ", " + temp[i]);
          data[c] = temp[k];
          k++;
          c++;
          System.out.println("Written on data: " + Arrays.toString(data));
        }else {
          System.out.println("Indecies of k and i: " + k + ", " + i);
          System.out.println("temp[i] <= temp[k]: " + temp[i] + ", " + temp[k]);
          data[c] = temp[i];
          i++;
          c++;
          System.out.println("Written on data: " + Arrays.toString(data));
        }
      }
      */
      System.out.println("Written on data: " + Arrays.toString(data));
    }
  }
  //end
  //c should be the lower value
  //hi is determined as
  public static void mergeOpt(int[] data, int[] temp, int lo, int pivot, int hi){
    int c = lo; //fill later, keep below hi of this call
    int i = lo; //from i - k and k - hi have been sorted
    int k = pivot; //read up on both sections simultaneously
    System.out.println("The temp array: " + Arrays.toString(temp));
    System.out.println("The data that will be written on: " + Arrays.toString(data));
    System.out.println("lo, pivot, hi: " + lo +", "+pivot+", " + hi);
    while (c<=hi){
      // do same check as normal mergesort however the catch is like this:
      // if i=pivot, fill rest of data up until hi w k values
      // if k = hi, fill rest of data up with i values until pivot
      if (k > hi){ //should always be writing into what is considered data
        System.out.println("k  is > hi: " + k + ", hi: " + hi);
        data[c] = temp[i];
        i++;
        c++;
        System.out.println("Written on data: " + Arrays.toString(data));
      }else if (i >= pivot){
        System.out.println("i  is > pivot: " + i + ", pivot: " + pivot);
        data[c] = temp[k];
        k++;
        c++;
        System.out.println("Written on data: " + Arrays.toString(data));
      }else if (temp[k] < temp[i]){
        System.out.println("Indecies of k and i: " + k + ", " + i);
        System.out.println("temp[k] < temp[i]: " + temp[k] + ", " + temp[i]);
        data[c] = temp[k];
        k++;
        c++;
        System.out.println("Written on data: " + Arrays.toString(data));
      }else {
        System.out.println("Indecies of k and i: " + k + ", " + i);
        System.out.println("temp[i] <= temp[k]: " + temp[i] + ", " + temp[k]);
        data[c] = temp[i];
        i++;
        c++;
        System.out.println("Written on data: " + Arrays.toString(data));
      }
    }
  }
}
