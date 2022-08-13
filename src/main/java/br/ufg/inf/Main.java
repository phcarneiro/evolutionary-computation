package br.ufg.inf;

import br.ufg.inf.auditOP.Bag;
import br.ufg.inf.graphic.G;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Main {

    private static final Double CROSSOVER_RATE = 1.0;
    private static final Double MUTATION_RATE = 0.005;
    private static final Integer RUNS = 30;
    private static final Integer DATASET = 1;
    private static final Integer POP_SIZE = 500;
    private static final Integer GENERATIONS = 10000;

    public static void main(String[] args) throws IOException {
        // Create a file input stream to read data from each files
        Bag bag = new Bag();
    }
}