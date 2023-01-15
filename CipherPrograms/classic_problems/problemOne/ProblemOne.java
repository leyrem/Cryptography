import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

import javax.print.DocFlavor.STRING;

public class ProblemOne{
    public static HashMap<String, String> readCodeBook(){
      HashMap<String, String> map = new HashMap<String, String>();
      try {
        File myObj = new File("codeBook_21.txt");
        Scanner myReader = new Scanner(myObj);
        
        while (myReader.hasNextLine()) {
          String data = myReader.nextLine();
          int c = 0;
          String keyName = "";
          String valName = "";
          for(int i = 0; i < data.length(); i ++){
            if(c < 3){
              keyName = keyName + data.charAt(i);
            }
            if ( c >= 6 ){
              valName = valName + data.charAt(i);
            }
            if( c == data.length()-1){
              map.put(keyName, valName);
            } 
            c++; 
          }
          keyName = "";
          valName = "";
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      return map;
    }
    public static LinkedList<String> readFile(String filename){
        LinkedList<String> myList = new LinkedList<String>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              counter ++;
              if (counter % 2 != 0){
                  int c = 0;
                  String word = "";
                  for(int i = 1; i < data.length(); i++){
                      if( c!= 5){
                        word = word + data.charAt(i);
                        c++;
                        if(i == data.length()-1){
                          myList.add(word);
                        }
                      } else {
                          c = 0;
                          myList.add(word);
                          word = "";
                      }
                  }
              }
              //System.out.println(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          return myList;
    }
    public static HashMap<String, Integer>  analyzeFrequencyAnalysis(LinkedList<String> list){
      HashMap<String, Integer> map = new HashMap<String, Integer>();
      for(String el: list){
        int count = map.containsKey(el) ? map.get(el) : 0;
        map.put(el, count + 1);
      }
      return map;
    }
    public static HashMap<Character, Integer> frequencyAnalysis(LinkedList<String> stringList){
      HashMap<Character, Integer> frequencyMap = new HashMap<Character, Integer>();
      
       for(String el: stringList){
        for(int i = 0; i < el.length(); i++){
          int count = frequencyMap.containsKey(el.charAt(i)) ? frequencyMap.get(el.charAt(i)) : 0;
          frequencyMap.put(el.charAt(i), count + 1);
        }
      }
      return frequencyMap;
    }
    public static void main (String args[]){
        LinkedList<String> list = readFile("Classic_Problem_1.txt");
        HashMap<String, String> codeBook = readCodeBook();
        //HashMap<Character, Integer> frequencies = frequencyAnalysis(list);
        HashMap<Character, Character> correspondences = new HashMap<>();
        correspondences.put('H', 'N');
        correspondences.put('T', 'H');
        correspondences.put('O', 'A');
        correspondences.put('R', 'G');
        correspondences.put('V', 'L');
        correspondences.put('W', 'Q');

        //correspondences.put('P', 'B');
        //correspondences.put('B', 'S');

        correspondences.put('P', 'Z');
        correspondences.put('B', 'M');

        //vOWELS A,E,I,O,U
        //correspondences.put('A', 'I');
        //correspondences.put('E', 'I');
        //correspondences.put('I', 'O');
        //correspondences.put('U', 'U');


       // System.out.println(frequencies);
        
        LinkedList<String> newList = new LinkedList<String>();

        for(String ciphertext: list){
          String neww = "";
          for(int i = 0; i < ciphertext.length();i++){
            if(correspondences.containsKey(ciphertext.charAt(i))){
              neww += correspondences.get(ciphertext.charAt(i));
            } else {
              neww += ciphertext.charAt(i);
            }
          }
          newList.add(neww);
        }
      

        String allCipherText = "";
        for(String i: list){
          allCipherText += i;
        }

        LinkedList<String> ciphertextGroupedInThree = new LinkedList<String>();
        for(int i = 0; i < allCipherText.length()-3; i = i +3){
          String threeletter = ""+allCipherText.charAt(i) + allCipherText.charAt(i+1) + allCipherText.charAt(i+2); 
          ciphertextGroupedInThree.add(threeletter);
        }

        HashMap<String, Integer> cipherFrequencies = new HashMap<String, Integer>();
        for(String l: ciphertextGroupedInThree){
          if(cipherFrequencies.containsKey(l)){
            cipherFrequencies.put(l, cipherFrequencies.get(l)+1);
          } else {
            cipherFrequencies.put(l, 1);
          }
        }
        int max = 0;
        String maxKey = "";
        for (String key : cipherFrequencies.keySet()) {
          if(cipherFrequencies.get(key) > max){
            max = cipherFrequencies.get(key);
            maxKey = key;
          }
        }
        System.out.println(maxKey);
        System.out.println(max);
        System.out.println(cipherFrequencies);

        String allText = "";
        for(String i: newList){
          allText += i;
        }
        String finall = "";
        for(int i = 0; i < allText.length()-3; i = i +3){
          String threeletter = ""+allText.charAt(i) + allText.charAt(i+1) + allText.charAt(i+2); 
          finall += codeBook.get(threeletter);
        }
        System.out.println(finall);
        //HashMap<String, Integer>  map = analyzeFrequencyAnalysis(list);
        //System.out.println(map);
        //HashMap<String, String>  codeBook = readCodeBook();
        //System.out.println(codeBook);
    }
}