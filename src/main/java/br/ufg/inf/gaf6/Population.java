package br.ufg.inf.gaf6;

import java.util.*;

public class Population {

    private Integer popSize;
    private ArrayList<Member> popMembers;

    private Integer generation;

    public Integer getPopSize() {
        return this.popSize;
    }

    public ArrayList<Member> getPopMembers() {
        return this.popMembers;
    }

    public Integer getGeneration() {
        return this.generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public void setPopSize(Integer popSize) {
        this.popSize = popSize;
    }

    public void setPopMembers(ArrayList<Member> popMembers) {
        this.popMembers = popMembers;
    }

    // Constructor default. Generation 0.
    public Population(Integer popSize) {
        this.popMembers = new ArrayList<Member>();
        for (int i = 0; i < popSize; i++) {
            Member member = new Member();
            member.calculateMemberFitness();
            this.popMembers.add(member);
        }
        this.popSize = popSize;
    }

    // Constructor for building a new population on selected members
    public Population(ArrayList<Member> selectedMembers) {
        this.popMembers = new ArrayList<>();
        for (int i = 0; i < selectedMembers.size(); i++
        ) {
            this.popMembers.add(selectedMembers.get(i));
        }
        this.popSize = selectedMembers.size();
    }


    // Selecting individuals members randomly with roulette to get higher chance to recombine the fittest members
    public Population selectMembersToFormNewPopulationRoulette() {
        // First, we are going to build a new population with 70% size of the original one
        ArrayList<Member> selectedMembers = new ArrayList<>();
        ArrayList<Member> members = this.getPopMembers();
        // Next, we are going to get the sum of fitness members of the original population
        Double totalSum = getTotalSumFitnessMembers(members);
        this.calculatePrecedentMemberFitness();
        Random rn = new Random();
        for (int i = 0; i < members.size(); i++) {
            // Generating a random number
            double rand = rn.nextDouble(0.0, totalSum);
            for (Member candidateParent : members) {
                if ((candidateParent.getSumPrecedentMembersFitness() + candidateParent.getFitness()) >= rand) {
                    // Selecting the first member which summing fitness and precedent fitness
                    // is equal or greater than the random
                    selectedMembers.add(candidateParent);
                    break;
                }
            }
        }

        return new Population(selectedMembers);
    }

    public void calculatePrecedentMemberFitness() {
        Double sumPrecedentFitness = 0.0;
        for (Member member : this.popMembers
        ) {
            member.setSumPrecedentMembersFitness(member.getFitness() + sumPrecedentFitness);
            sumPrecedentFitness = member.getSumPrecedentMembersFitness();
        }
    }

    public Population crossover(Double crossoverRate) {
        // Here, we need to recombine members to generate offspring and add it to population
        Random rn = new Random();
        ArrayList<Member> offspringPopulation = new ArrayList<>();
        ArrayList<Member> parentPopulation = this.getPopMembers();
        for (int i = 0; i < parentPopulation.size()/2; i++) {
            //Randomly looking for parents to apply 1 point crossover
            int parent1 = rn.nextInt(0, parentPopulation.size()-1);
            int parent2 = rn.nextInt(0, parentPopulation.size()-1);
            StringBuilder parentA = new StringBuilder(parentPopulation.get(parent1).getCromosoma());
            StringBuilder parentB = new StringBuilder(parentPopulation.get(parent2).getCromosoma());

            // Crossover rate check
            Double chanceToGenOffspring = rn.nextDouble(0.0, 1.0);
            if (chanceToGenOffspring <= crossoverRate) {
                int length = parentA.length();
                int cutPosition = rn.nextInt(0, length-1);
                String sTailA = parentA.substring(cutPosition);
                String sTailB = parentB.substring(cutPosition);
                StringBuilder tailA = new StringBuilder(sTailA);
                StringBuilder tailB = new StringBuilder(sTailB);
                StringBuilder tailOffspringA1 = parentA.replace(cutPosition, parentA.length(), tailB.toString());
                StringBuilder tailOffspringB1 = parentB.replace(cutPosition, parentB.length(), tailA.toString());
                Member offspringA1 = new Member(tailOffspringA1);
                Member offspringB1 = new Member(tailOffspringB1);
                offspringPopulation.add(offspringA1);
                offspringPopulation.add(offspringB1);
            } else {
                // Out the Crossover rate
                Member offspringACopy = new Member(parentA);
                Member offspringBCopy = new Member(parentB);
                offspringPopulation.add(offspringACopy);
                offspringPopulation.add(offspringBCopy);
            }
        }

        Population offspring = new Population(offspringPopulation);
        offspring.evaluatePopulation();
        offspring.calculatePrecedentMemberFitness();

        return offspring;
    }

    public void mutate(Double mutationRate) {
        Random rn = new Random();
        for (Member member: this.getPopMembers()
             ) {
            for (int i = 0; i < member.getCromosoma().length(); i++) {
                Double chanceToBitFlipGen = rn.nextDouble(0.0, 1.0);
                if (chanceToBitFlipGen <= mutationRate) {
                    // If it is inside mutation range interval then the bit at index i is flipped
                    String s = member.getCromosoma();
                    char[] chars = new char[s.length()];
                    for(int c = 0; c < s.length(); c++) {
                        if (c==i) {
                            // flip the bottom bit so 0 flips to 1 and 1 flips to 0
                            chars[c] = (char) (s.charAt(c) ^ 1);
                        } else {
                            chars[c] = s.charAt(c);
                        }
                    }
                    String flipped = new String(chars);
                    member.setCromosoma(String.valueOf(flipped));
                }
            }
        }
    }

    public void evaluatePopulation() {
        this.popSize = this.popMembers.size();

        for (Member member : this.popMembers
        ) {
            member.calculateMemberFitness();
        }
    }

    private Double getTotalSumFitnessMembers(ArrayList<Member> members) {
        Double total = 0.0;
        for (Member member : members)
            total = total + member.getFitness();

        return total;
    }

    public Population() {
        this.setPopSize(0);
        this.setPopMembers(new ArrayList<>());
    }

    public Member selectTheBestSoFar() {
        Member bestSoFar = null;
        Double bestSoFarFitness = 0.0;
        for (Member candidate: this.getPopMembers()
             ) {
            bestSoFar = candidate.getFitness() > bestSoFarFitness ? candidate : bestSoFar;
            bestSoFarFitness = bestSoFar.getFitness();
        }
        return bestSoFar;
    }

    public void removeRandomlyOffspring() {
        Random rn = new Random();
        Integer removeIndex = rn.nextInt(0, this.getPopMembers().size()-1);
        Member memberOff = this.getPopMembers().get(removeIndex);
        this.getPopMembers().remove(memberOff);
    }
}