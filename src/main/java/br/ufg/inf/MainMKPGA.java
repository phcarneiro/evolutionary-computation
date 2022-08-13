package br.ufg.inf;

import br.ufg.inf.gamultiknapsack.Bag;
import br.ufg.inf.gamultiknapsack.Member;
import br.ufg.inf.gamultiknapsack.Population;
import br.ufg.inf.graphic.G;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MainMKPGA {

    private static final Double CROSSOVER_RATE = 1.0;
    private static final Double MUTATION_RATE = 0.005;
    private static final Integer RUNS = 30;
    private static final Integer DATASET = 1;
    private static final Integer POP_SIZE = 500;
    private static final Integer GENERATIONS = 10000;

    public static void main(String[] args) throws IOException {
        // Create a file input stream to read data from each files
        Bag bag = new Bag(DATASET);

        int runs = 0;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        G g = new G();
        Member bestSoFarAllRuns = new Member();
        ArrayList<Integer> bestFeasibleResults = new ArrayList<>();

        while (runs < RUNS) {
            // Initialize a population with parametrized size.
            Integer generation = 0;

            // Also, every member of this initial population
            Population initialPopulation = new Population(POP_SIZE, bag);
            initialPopulation.setGeneration(generation);

            // Stop condition below
            int generationsCounter = GENERATIONS;

            //Here, we begin to develop the initial population with an iteration until the stop condition
            Population backPopulation = initialPopulation; // Starting the evolutionary iteration
            System.out.println("Generation #" + backPopulation.getGeneration());
            System.out.println("Run #" + runs);

            Member bestSoFar = null;
            Integer bestSoFarFitness = 0;

            while (generation < generationsCounter) {
                // Selecting the members of the previous generation by tournaments selecting method.
                // The selected members will be the parents of the evolved population
                backPopulation.fixMembersThatDoesNotFit(bag);

                Population parentPopulation = backPopulation.selectMembersToFormNewPopulationThroughTournaments();
                parentPopulation.setGeneration(generation);

                // Recombining parents to generate offspring
                Population offspringPopulation = parentPopulation.uniformCrossover(CROSSOVER_RATE);
                // Applying mutation on new population
                offspringPopulation.mutate(MUTATION_RATE);

                //Second time of repairing
                offspringPopulation.evaluatePopulation(bag);
                offspringPopulation.fixMembersThatDoesNotFit(bag);

                // Choose what will be next population: the offspring - 1 members and with the best so far parent
//                Member bestSoFarParent = parentPopulation.selectTheBestSoFar();
//                offspringPopulation.removeRandomlyOffspring();
//                offspringPopulation.getPopMembers().add(bestSoFarParent);
//                offspringPopulation.setPopSize(offspringPopulation.getPopMembers().size());

                // After selecting the best so far parent, all parents die
                parentPopulation = null;

//                offspringPopulation.evaluatePopulation(bag);

                Member theBestSoFar = offspringPopulation.selectTheBestSoFar();
                bestSoFar = theBestSoFar.getFitness() >= bestSoFarFitness ? theBestSoFar : bestSoFar;
                bestSoFarFitness = bestSoFar.getFitness() >= bestSoFarFitness ? bestSoFar.getFitness() : bestSoFarFitness;

                if (bestSoFar.getCandidateFitsWeightLimit()) {
                    System.out.println("Best solution: " + bestSoFar.getChromosome());
                    System.out.println("Best solution score: " + bestSoFar.getMemberSumValues());
                } else {
                    System.out.println("The solution that does not fit: " + bestSoFar.getChromosome());
                }

                // Starting a new iteration and exploration
                generation++;
                backPopulation = offspringPopulation;
                backPopulation.setGeneration(generation);
                System.out.println("Generation #" + backPopulation.getGeneration());
                if (generation == generationsCounter - 1) {
                    try {
                        if (bestSoFar.getCandidateFitsWeightLimit()) {
                            FileWriter myWriter = new FileWriter("src/main/resources/results/mknapcbres" + runs + ".txt");
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            myWriter.write("Final results:" + timestamp + "\n");
                            myWriter.write("Best cromossoma so far: " + bestSoFar.getChromosome() + "\n");
                            myWriter.write("Best feasible results: " + bestSoFar.getMemberSumValues() + "\n");
                            g.getCoordinates().add(bestSoFar.getMemberSumValues());
                            bestFeasibleResults.add(bestSoFar.getMemberSumValues());
                            myWriter.write("Does solution fit weight limits? " + bestSoFar.getCandidateFitsWeightLimit() + "\n");
                            myWriter.write("Weights N1: " + bestSoFar.getSumWeightsN1() + "\n");
                            myWriter.write("Weights N2: " + bestSoFar.getSumWeightsN2() + "\n");
                            myWriter.write("Weights N3: " + bestSoFar.getSumWeightsN3() + "\n");
                            myWriter.write("Weights N4: " + bestSoFar.getSumWeightsN4() + "\n");
                            myWriter.write("Weights N5: " + bestSoFar.getSumWeightsN5() + "\n");
                            myWriter.write("Weights limits: " + bag.getWeightsLimits().toString() + "\n");
                            myWriter.write("########################" + "\n");
                            myWriter.close();

                            assert bestSoFarAllRuns != null;
                            bestSoFarAllRuns = bestSoFar.getFitness() > bestSoFarAllRuns.getFitness() ? bestSoFar : bestSoFarAllRuns;

                        } else {
                            System.out.println("The solution that does not fit: " + bestSoFar.getChromosome());
                        }

                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
            runs++;
        }
        frame.add(g);
//        JPanel panel = new JPanel();
//        panel.add(new JLabel());
//        frame.setContentPane(panel);
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setVisible(true);
        FileWriter myWriter = new FileWriter("src/main/resources/results/mknapcbresults.txt");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        myWriter.write("Final results after " + runs + ":" + timestamp + "\n");
        myWriter.write("Best cromossoma so far after all runs: " + bestSoFarAllRuns.getChromosome() + "\n");
        myWriter.write("Best results so far all runs: " + bestSoFarAllRuns.getMemberSumValues() + "\n");
        myWriter.write("Does solution fit weight limits? " + bestSoFarAllRuns.getCandidateFitsWeightLimit() + "\n");
        myWriter.write("Weights N1: " + bestSoFarAllRuns.getSumWeightsN1() + "\n");
        myWriter.write("Weights N2: " + bestSoFarAllRuns.getSumWeightsN2() + "\n");
        myWriter.write("Weights N3: " + bestSoFarAllRuns.getSumWeightsN3() + "\n");
        myWriter.write("Weights N4: " + bestSoFarAllRuns.getSumWeightsN4() + "\n");
        myWriter.write("Weights N5: " + bestSoFarAllRuns.getSumWeightsN5() + "\n");
        myWriter.write("Weights limits: " + bag.getWeightsLimits().toString() + "\n");
        myWriter.write("Items: " + printItemsSelected(bag, bestSoFarAllRuns.getChromosome()) + "\n");

        myWriter.write("Best feasible results after all runs: " + calculateMedia(bestFeasibleResults) + "\n");


        myWriter.write("########################" + "\n");

        myWriter.close();


    }

    private static String printItemsSelected(Bag bag, String chromosome) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < chromosome.length(); i++) {
            if (chromosome.charAt(i) == '1')
                items.add(String.valueOf(bag.getValues().get(i)));

        }
        return items.toString();
    }

    private static int calculateMedia(ArrayList<Integer> bestFeasibleResults) {
        Integer sum = 0;
        for (Integer i : bestFeasibleResults
        ) {
            sum += i;
        }
        int i = sum / bestFeasibleResults.size();
        return i;
    }
}