package br.ufg.inf;

import br.ufg.inf.gaf6.Member;
import br.ufg.inf.gaf6.Population;

public class MainF {

    private static final Double CROSSOVER_RATE = 0.85;

    private static final Double MUTATION_RATE = 0.035;
    
    public static void main(String[] args) {

        // Initialize a population with parametrized size.
        Integer generation = 0;

        // Also, every member of this initial population
        // is evaluated with fitness function F6 applied in constructor calling
        Population initialPopulation = new Population(100);
        initialPopulation.setGeneration(generation);

        // Stop condition below
        Integer generationsCounter = 2000;

        //Here, we begin to develop the initial population with an iteration until the stop condition
        Population backPopulation = initialPopulation; // Starting the evolutionary iteration
        System.out.println("Generation #" + backPopulation.getGeneration());

        Member bestSoFar = null;
        Double bestSoFarFitness = 0.0;

        while (generation < generationsCounter) {
            // Selecting the members of the previous generation by roulette selecting method.
            // The selected members will be the parents of next population
            Population parentPopulation = backPopulation.selectMembersToFormNewPopulationRoulette();
            parentPopulation.setGeneration(generation);

            // Recombining parents to generate offspring
            Population offspringPopulation = new Population();
            offspringPopulation = parentPopulation.crossover(CROSSOVER_RATE);
            // Applying mutation on new population
            offspringPopulation.mutate(MUTATION_RATE);

            // Choose what will be next population: the offspring - 1 members and with the best so far parent
            Member bestSoFarParent = parentPopulation.selectTheBestSoFar();
            offspringPopulation.removeRandomlyOffspring();
            offspringPopulation.getPopMembers().add(bestSoFarParent);
            offspringPopulation.setPopSize(offspringPopulation.getPopMembers().size());
            // After selecting the best so far parent, all parents die
            parentPopulation = new Population();
            offspringPopulation.evaluatePopulation();
            offspringPopulation.calculatePrecedentMemberFitness();

            bestSoFar = offspringPopulation.selectTheBestSoFar().getFitness() > bestSoFarFitness ?
                    offspringPopulation.selectTheBestSoFar() : bestSoFar;
            bestSoFarFitness = offspringPopulation.selectTheBestSoFar().getFitness() > bestSoFarFitness ?
                    offspringPopulation.selectTheBestSoFar().getFitness() : bestSoFarFitness;

//            System.out.println("Best population member: " + offspringPopulation.selectTheBestSoFar().getFitness());
//            System.out.println("Best population member cromossoma: " + offspringPopulation.selectTheBestSoFar().getCromosoma());
//            System.out.println("x e y: " + offspringPopulation.selectTheBestSoFar().getXY());

            System.out.println("Best member so far: " + Double.valueOf(Math.round(bestSoFar.getFitness()*100000)/100000.0));
            System.out.println("Best cromossoma so far: " + bestSoFar.getCromosoma());
            System.out.println("xMax e yMax: " + bestSoFar.getXY());

            // Starting a new iteration and exploration
            generation++;
            if (generation == generationsCounter) {
//                if (bestSoFarFitness != 1.0)
//                    generationsCounter += 100;
//                else {
                    System.out.println("Final results");
                    System.out.println("Best member so far: " + Double.valueOf(Math.round(bestSoFar.getFitness()*100000)/100000.0));
                    System.out.println("Best cromossoma so far: " + bestSoFar.getCromosoma());
                    System.out.println("xMax e yMax: " + bestSoFar.getXY());
                    return;
//                }

            }
            backPopulation = offspringPopulation;
            backPopulation.setGeneration(generation);
            System.out.println("Generation #" + backPopulation.getGeneration());
        }
    }
}