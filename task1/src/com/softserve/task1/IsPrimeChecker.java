package com.softserve.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ${JDEEK} on ${03.11.2018}.
 */
public class IsPrimeChecker extends Thread {

    private int begin;
    private int end;
    private List<Integer> primeList;

    public IsPrimeChecker(int begin, int end, List<Integer> primeList) {
        this.begin = begin;
        this.end = end;
        this.primeList = primeList;
    }

    @Override
    public void run() {
        while (begin <= end){
            boolean isPrime = primeChecker(begin);
            if (isPrime){
                primeList.add(begin);
            }
            begin += 1;
        }

        System.out.println("Checker #"+getName()+" has finished work!");
    }

    ///////CHECK PRIME NUMBERS:
    public boolean primeChecker(int num){
        for (int i=2; i<=num/2; i++) {
            int temp = num % i;
            if (temp == 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args){
        ///////INPUT DATA:
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter begin of interval: ");
        int beginOfInterval = scanner.nextInt();
        System.out.println("Enter end of interval: ");
        int endOfInterval = scanner.nextInt();
        System.out.println("Enter count of threads: ");
        int countOfThreads = scanner.nextInt();
        int lastElement2 = endOfInterval / countOfThreads;

        ///////THREAD ARRAY:
        IsPrimeChecker checker[] = new IsPrimeChecker[countOfThreads];
        ///////LIST FOR THREAD DATA SAVE:
        List[] copies = new ArrayList[countOfThreads];

        ///////INTERVAL DISTRIBUTION:
        for (int i = 0; i < countOfThreads; i ++){
            List<Integer> listForEvenThread = new ArrayList<>();
            checker[i] = new IsPrimeChecker(lastElement2*i, endOfInterval,listForEvenThread);
            checker[i].setName("Cheker #"+i);
            copies[i] = listForEvenThread;
        }
        ///////INTERVAL DISTRIBUTION AND RUN:
        for (int i = 0; i < countOfThreads; i ++){
            List<Integer> listForEvenThread = new ArrayList<>();
            checker[0] = new IsPrimeChecker(beginOfInterval,lastElement2,listForEvenThread);
            checker[i].start();
            checker[0].run();
            copies[0] = listForEvenThread;
        }
        ///////FOR THREAD BLOCKING:
        try {
            for (int i = 0 ; i < countOfThreads; i ++){
                checker[i].join();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("List 2: "+ Arrays.toString(copies));
    }

    @Override
    public String toString() {
        return "Simple change";
    }
}
