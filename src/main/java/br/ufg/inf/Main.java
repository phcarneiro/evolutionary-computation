package br.ufg.inf;

import br.ufg.inf.gaf6.Member;
import br.ufg.inf.gaf6.Population;

import java.text.DecimalFormat;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // Initialize a population with parametrized size.
        Integer generation = 0;

        // Also, every member of this initial population
        // is evaluated with fitness function F6 applied in constructor calling
        Population initialPopulation = new Population(100);
        initialPopulation.setGeneration(generation);

        // Stop condition below
        Integer generationsCounter = 5000;

        //Here, we begin to develop the initial population with an iteration until the stop condition
        Population backPopulation = initialPopulation; // Starting the evolutionary iteration
        System.out.println("Generation #" + backPopulation.getGeneration());
        System.out.println(backPopulation.getPopSize() + " : generation population size before operators");

        while (generation < generationsCounter) {
            // Selecting the members of the previous generation by roulette selecting method.
            // The selected members will be the parents of next population
            Population parentPopulation = backPopulation.selectMembersToFormNewPopulationRoulette();
            parentPopulation.setGeneration(generation);

            // Recombining parents to generate offspring
            Population offspringPopulation = new Population();
            offspringPopulation = parentPopulation.crossover();
            // Applying mutation on new population
            offspringPopulation.mutate();

            // Choose what will be next population: the offspring - 1 members and with the best so far parent
            Member bestSoFarParent = parentPopulation.selectTheBestSoFar();
            offspringPopulation.removeRandomlyOffspring();
            offspringPopulation.getPopMembers().add(bestSoFarParent);
            offspringPopulation.setPopSize(offspringPopulation.getPopMembers().size());
            parentPopulation = new Population(); // After selecting the best so far parent, all parents die
            System.out.println(offspringPopulation.getPopSize() + " : offspring population size after selecting members and applying operators");
            offspringPopulation.evaluatePopulation();
            offspringPopulation.calculatePrecedentMemberFitness();

            System.out.println("Best so far fitness: " + offspringPopulation.selectTheBestSoFar().getFitness());
            System.out.println("Best so far cromossoma: " + offspringPopulation.selectTheBestSoFar().getCromosoma());
            System.out.println("xMax e yMax: " + offspringPopulation.selectTheBestSoFar().getXY());

            // Valores ótimos próximos de 0.999 já apresentam boa condição de código. Depende parametrização.

            // Starting a new iteration and exploration
            generation++;
            if (generation == generationsCounter-1)
                return;
            backPopulation = offspringPopulation;
            backPopulation.setGeneration(generation);
            System.out.println("Generation #" + backPopulation.getGeneration());
            System.out.println(backPopulation.getPopSize() + " : population size before operators");
        }
    }
}