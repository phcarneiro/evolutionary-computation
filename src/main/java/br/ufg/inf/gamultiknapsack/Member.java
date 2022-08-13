package br.ufg.inf.gamultiknapsack;

import java.util.*;

public class Member {
    private Integer fitness; // in this case, it is the sum memberSumValues and memberSumWeights or zero - does not fit the weight limit
    private String chromosome;
    private Integer memberSumValues;

    private Boolean candidateFitsWeightLimit;

    private Integer sumWeightsN1;
    private Integer sumWeightsN2;
    private Integer sumWeightsN3;
    private Integer sumWeightsN4;
    private Integer sumWeightsN5;

    public Boolean getCandidateFitsWeightLimit() {
        return candidateFitsWeightLimit;
    }

    public void setCandidateFitsWeightLimit(Boolean candidateFitsWeightLimit) {
        this.candidateFitsWeightLimit = candidateFitsWeightLimit;
    }

    public Integer getSumWeightsN1() {
        return sumWeightsN1;
    }

    public Integer getSumWeightsN2() {
        return sumWeightsN2;
    }

    public Integer getSumWeightsN3() {
        return sumWeightsN3;
    }

    public Integer getSumWeightsN4() {
        return sumWeightsN4;
    }

    public Integer getSumWeightsN5() {
        return sumWeightsN5;
    }

    public void setSumWeightsN1(Integer sumWeightsN1) {
        this.sumWeightsN1 = sumWeightsN1;
    }

    public void setSumWeightsN2(Integer sumWeightsN2) {
        this.sumWeightsN2 = sumWeightsN2;
    }

    public void setSumWeightsN3(Integer sumWeightsN3) {
        this.sumWeightsN3 = sumWeightsN3;
    }

    public void setSumWeightsN4(Integer sumWeightsN4) {
        this.sumWeightsN4 = sumWeightsN4;
    }

    public void setSumWeightsN5(Integer sumWeightsN5) {
        this.sumWeightsN5 = sumWeightsN5;
    }

    public Integer getMemberSumValues() {
        return memberSumValues;
    }

    public void setMemberSumValues(Integer memberSumValues) {
        this.memberSumValues = memberSumValues;
    }

    public Member(StringBuilder builder) {
        this.chromosome = builder.toString();
        this.fitness = 0;
        this.initiateArrays();
    }

    public String getChromosome() {
        return this.chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getFitness() {
        return this.fitness;
    }

    public void setFitness(Integer fitness) {
        this.fitness = fitness;
    }

    public Member() {
        Random rn = new Random();
        StringBuilder chromosomeStr = new StringBuilder();
        int contador = 0;
        while (contador < 100) {
            String gen = (rn.nextBoolean() == Boolean.FALSE) ? "0" : "1";
            chromosomeStr.append(gen);
            contador++;
        }
        this.chromosome = chromosomeStr.toString();

        this.fitness = 0;
        this.initiateArrays();
    }

    private void initiateArrays() {
        setSumWeightsN1(0);
        setSumWeightsN2(0);
        setSumWeightsN3(0);
        setSumWeightsN4(0);
        setSumWeightsN5(0);
    }

    // Here I need to decode individual chromosome into a candidate solution for each knapsack problem.
    public void evaluateMemberFitness(Bag bag) {
        Integer sumValues = 0;

        Integer sumWeightsN1 = 0;
        Integer sumWeightsN2 = 0;
        Integer sumWeightsN3 = 0;
        Integer sumWeightsN4 = 0;
        Integer sumWeightsN5 = 0;
        // Identifying solution candidates that appears on the randomly generated member
        // Calculating member p(m) weights
        for (int i = 0; i < getChromosome().length(); i++) {
            if (getChromosome().charAt(i) == '1') {
                // This combination of values and weights will be on the bag.
                sumValues += bag.getValues().get(i);

                sumWeightsN1 = sumWeightsN1 + bag.getWeightsN1().get(i);
                sumWeightsN2 = sumWeightsN2 + bag.getWeightsN2().get(i);
                sumWeightsN3 = sumWeightsN3 + bag.getWeightsN3().get(i);
                sumWeightsN4 = sumWeightsN4 + bag.getWeightsN4().get(i);
                sumWeightsN5 = sumWeightsN5 + bag.getWeightsN5().get(i);
            }
        }
        setMemberSumValues(sumValues);
        setSumWeightsN1(sumWeightsN1);
        setSumWeightsN2(sumWeightsN2);
        setSumWeightsN3(sumWeightsN3);
        setSumWeightsN4(sumWeightsN4);
        setSumWeightsN5(sumWeightsN5);
        if (getSumWeightsN1() > bag.getWeightsLimits().get(0) || getSumWeightsN2() > bag.getWeightsLimits().get(1) || getSumWeightsN3() > bag.getWeightsLimits().get(2) || getSumWeightsN4() > bag.getWeightsLimits().get(3) || getSumWeightsN5() > bag.getWeightsLimits().get(4)) {
            setCandidateFitsWeightLimit(Boolean.FALSE);
        } else {
            setCandidateFitsWeightLimit(Boolean.TRUE);
        }
        // the member that does not fit the weight limit does not get a fitness score
        setFitness(getCandidateFitsWeightLimit() ? getMemberSumValues() : 0);
    }

    public void removeItems(Bag bag) {
        Random rn = new Random();
        while (!this.getCandidateFitsWeightLimit()) {
            int randomPosition = rn.nextInt(0, this.getChromosome().length());
            while (this.getChromosome().charAt(randomPosition) == '0')
                randomPosition = rn.nextInt(0, this.getChromosome().length());
            bitFlip(randomPosition);
            this.evaluateMemberFitness(bag);
        }
    }

    public void putItems(Bag bag) {
        int credits = 0;
        for (int c = 0; c < this.getChromosome().length(); c++) {
            if (this.getChromosome().charAt(c) == '0')
                credits++;
        }
        Random rn = new Random();
        int index = 0;
        while (index < credits) {
            int randomPosition = rn.nextInt(0, this.getChromosome().length());
            while (this.getChromosome().charAt(randomPosition) == '1')
                randomPosition = rn.nextInt(0, this.getChromosome().length());
            String flipped = bitFlipForTestTheSolutionCandidate(randomPosition);
            Member candidate = new Member(new StringBuilder(flipped));
            candidate.evaluateMemberFitness(bag);
            if (candidate.getCandidateFitsWeightLimit()) {
                updateMember(candidate);
            }
            index++;
        }
    }

    private String bitFlipForTestTheSolutionCandidate(int randomPosition) {
        String s = this.getChromosome();
        char[] chars = new char[s.length()];
        for (int c = 0; c < s.length(); c++) {
            if (c == randomPosition) {
                // flip the bottom bit so 0 flips to 1 and 1 flips to 0
                chars[c] = (char) (s.charAt(c) ^ 1);
            } else {
                chars[c] = s.charAt(c);
            }
        }
        String flipped = new String(chars);
        return flipped;
    }

    private void updateMember(Member newCandidate) {
        this.setChromosome(newCandidate.getChromosome());
        this.setMemberSumValues(newCandidate.getMemberSumValues());
        this.setSumWeightsN1(newCandidate.getSumWeightsN1());
        this.setSumWeightsN2(newCandidate.getSumWeightsN2());
        this.setSumWeightsN3(newCandidate.getSumWeightsN3());
        this.setSumWeightsN4(newCandidate.getSumWeightsN4());
        this.setSumWeightsN5(newCandidate.getSumWeightsN5());
        this.setFitness(newCandidate.getFitness());
        this.setCandidateFitsWeightLimit(newCandidate.getCandidateFitsWeightLimit());
    }

    private void bitFlip(int randomPosition) {
        String s = this.getChromosome();
        char[] chars = new char[s.length()];
        for (int c = 0; c < s.length(); c++) {
            if (c == randomPosition) {
                // flip the bottom bit so 0 flips to 1 and 1 flips to 0
                chars[c] = (char) (s.charAt(c) ^ 1);
            } else {
                chars[c] = s.charAt(c);
            }
        }
        String flipped = new String(chars);
        this.setChromosome(flipped);
    }
}