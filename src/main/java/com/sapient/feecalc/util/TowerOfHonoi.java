package com.sapient.feecalc.util;

public class TowerOfHonoi {

    public static int count = 0;

    public void move (int noOfDiscs, char fromTower, char toTower, char interTower) {
        count++;
        if(noOfDiscs == 1){
            System.out.println("Moving Disc 1 From " + fromTower + " To " + toTower);
        }else {
            move(noOfDiscs - 1, fromTower, interTower, toTower);
            System.out.println("Moving "+noOfDiscs+" From " + fromTower + " To " + toTower);
            move(noOfDiscs - 1, interTower, toTower, fromTower);
        }
    }

    public static void main(String[] args) {
        TowerOfHonoi th = new TowerOfHonoi();
        th.move(64, 'A', 'C', 'B');
        System.out.println("No Of Hours "+count);
    }
}
