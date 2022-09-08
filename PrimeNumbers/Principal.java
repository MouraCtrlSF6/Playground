package PrimeNumbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

interface ArrayHelper {
  static <T> List<T> copy(List<T> array) {
    List<T> copied = new ArrayList<>();

    array.forEach(item -> copied.add(item));

    return copied;
  }
}

interface PrimeNumber {
  static Long generateNextPrime(List<Long> currentPrimes) 
  throws Exception {
    try {
      Long verify = currentPrimes.get(currentPrimes.size() - 1);

      while(true) {
        verify += 2;
        
        Boolean isItPrime = isPrime(verify, currentPrimes);
  
        if(isItPrime) return verify;
      }

    } catch(Exception e) {
      throw e;
    }
  }

  static Boolean isPrime(Long number) 
  throws Exception {
    try {
      return isPrime(number, Arrays.asList(2L, 3L, 5L));
    } catch(Exception e) {
      throw e;
    }
  }

  static Boolean isPrime(Long number, List<Long> primesKnown)
  throws Exception {
    try {
      List<Boolean> isItPrime = new ArrayList<Boolean>(){{
        add(null);
      }};
  
      primesKnown.forEach((prime) -> {
        Long rest = number % prime;
        Double division = (double) number / prime;
        Long maxCheck = Double.valueOf(Math.sqrt(number)).longValue();

        if(isItPrime.get(0) != null) {
          return;
        }

        if(primesKnown.contains(number)) {
          isItPrime.set(0, true);
          return;
        }
   
        if(rest == 0) {
          isItPrime.set(0, false);
          return;
        }
  
        if(rest > division.intValue()) {
          isItPrime.set(0, true);
          return;
        }

        if(prime > maxCheck) {
          isItPrime.set(0, true);
          return;
        }
  
        if(isItPrime.get(0) == null && prime == primesKnown.get(primesKnown.size() - 1)) {
          try {
            List<Long> updatedPrimeList = ArrayHelper.copy(primesKnown);

            updatedPrimeList.add(generateNextPrime(primesKnown));

            isItPrime.set(0, isPrime(number, updatedPrimeList));
            
          } catch (Exception e) {
            System.out.println("Error on generation block: " + e.getMessage());
            return;
          }
        }
      });
  
      return isItPrime.get(0);
    } catch(Exception e) {
      throw e;
    }
  }
}

public class Principal {
  public static void main(String[] args) {
    try {
      Long suspect = Long.valueOf(readLine());
      System.out.println(suspect + " is a prime number: " + PrimeNumber.isPrime(suspect));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }

  public static String readLine() {
    System.out.print("Verify number: ");
    return (new Scanner(System.in)).nextLine();
  }
}
