package br.ufg.inf.gamultiknapsack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bag {
    private ArrayList<Integer> values;
    private ArrayList<Integer> weightsN1;
    private ArrayList<Integer> weightsN2;
    private ArrayList<Integer> weightsN3;
    private ArrayList<Integer> weightsN4;
    private ArrayList<Integer> weightsN5;
    private ArrayList<Integer> weightsLimits;

    public ArrayList<Integer> getWeightsN1() {
        return weightsN1;
    }

    public void setWeightsN1(ArrayList<Integer> weightsN1) {
        this.weightsN1 = weightsN1;
    }

    public ArrayList<Integer> getWeightsN2() {
        return weightsN2;
    }

    public void setWeightsN2(ArrayList<Integer> weightsN2) {
        this.weightsN2 = weightsN2;
    }

    public ArrayList<Integer> getWeightsN3() {
        return weightsN3;
    }

    public void setWeightsN3(ArrayList<Integer> weightsN3) {
        this.weightsN3 = weightsN3;
    }

    public ArrayList<Integer> getWeightsN4() {
        return weightsN4;
    }

    public void setWeightsN4(ArrayList<Integer> weightsN4) {
        this.weightsN4 = weightsN4;
    }

    public ArrayList<Integer> getWeightsN5() {
        return weightsN5;
    }

    public void setWeightsN5(ArrayList<Integer> weightsN5) {
        this.weightsN5 = weightsN5;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    private void extractProblemsFromDataSet(int dataSet) {
        int line = 1;
        int limit = 93;
        int bagSize = 0;
        int totalBags = 0;
        setWeightsN1(new ArrayList<>());
        setWeightsN2(new ArrayList<>());
        setWeightsN3(new ArrayList<>());
        setWeightsN4(new ArrayList<>());
        setWeightsN5(new ArrayList<>());
        setWeightsLimits(new ArrayList<>());
        setValues(new ArrayList<>());

        try {
            File initialFile = new File("src/main/resources/data/multiknapsack/mknapcb" + dataSet + ".txt");
            Scanner myReader = new Scanner(initialFile);

            while (line <= limit) {
                String data = myReader.nextLine();

                if (line == 1) {
                    // Number of problems
                }
                if (line == 2) {
                    // Number of objects and number of bags
                    String s = data.substring(1, 4);
                    bagSize = Integer.parseInt(s);
                    String s1 = data.substring(5, 6);
                    totalBags = Integer.parseInt(s1);
                }
                while (line < 17 && line > 2) { // extracting values
                    extractingValues(getValues(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 17) { // last line of values
                    extractingValues(getValues(), data);
                }
                while (line < 32 && line > 17) { // extracting weights from bag 1
                    extractingWeights(getWeightsN1(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 32) { // last line of weights for bag 1
                    extractingWeights(getWeightsN1(), data);
                }
                while (line < 47 && line > 32) {
                    extractingWeights(getWeightsN2(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 47) {
                    extractingWeights(getWeightsN2(), data);
                }
                while (line < 62 && line > 47) {
                    extractingWeights(getWeightsN3(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 62) {
                    extractingWeights(getWeightsN3(), data);
                }
                while (line < 77 && line > 62) {
                    extractingWeights(getWeightsN4(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 77) {
                    extractingWeights(getWeightsN4(), data);
                }
                while (line < 92 && line > 77) {
                    extractingWeights(getWeightsN5(), data);
                    line++;
                    data = myReader.nextLine();
                }
                if (line == 92) {
                    extractingWeights(getWeightsN5(), data);
                }
                if (line == 93) {
                    extractingWeightsLimits(getWeightsLimits(), data);
                }
                line++;
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void extractingWeightsLimits(ArrayList<Integer> weightsLimits, String data) {
        int beginIndex = 0;
        int endIndex = 0;
        for (int index = 0; index < data.length(); index++) {
            if (!Character.isWhitespace(data.charAt(index))) {
                if (beginIndex == 0) {
                    beginIndex++;
                    endIndex++;
                }
                endIndex++;
            } else {
                if (beginIndex != 0) {
                    weightsLimits.add(Integer.valueOf(data.substring(beginIndex, endIndex)));
                    endIndex++;
                    beginIndex = endIndex;
                }
            }
        }
    }

    private void extractingValues(ArrayList<Integer> values, String data) {
        int beginIndex = 0;
        int endIndex = 0;
        for (int index = 0; index < data.length(); index++) {
            if (!Character.isWhitespace(data.charAt(index))) {
                if (beginIndex == 0) {
                    beginIndex++;
                    endIndex++;
                }
                endIndex++;
            } else {
                if (beginIndex != 0) {
                    values.add(Integer.valueOf(data.substring(beginIndex, endIndex)));
                    endIndex++;
                    beginIndex = endIndex;
                }
            }
        }
    }

    private void extractingWeights(ArrayList<Integer> weights, String data) {
        int beginIndex = 0;
        int endIndex = 0;
        for (int index = 0; index < data.length(); index++) {
            if (!Character.isWhitespace(data.charAt(index))) {
                if (beginIndex == 0) {
                    beginIndex++;
                    endIndex++;
                }
                endIndex++;
            } else {
                if (beginIndex != 0) {
                    weights.add(Integer.valueOf(data.substring(beginIndex, endIndex)));
                    endIndex++;
                    beginIndex = endIndex;
                }
            }
        }
    }

    public Bag(Integer dataSet) {
        extractProblemsFromDataSet(dataSet);
    }

    public ArrayList<Integer> getWeightsLimits() {
        return weightsLimits;
    }

    public void setWeightsLimits(ArrayList<Integer> weightsLimits) {
        this.weightsLimits = weightsLimits;
    }
}