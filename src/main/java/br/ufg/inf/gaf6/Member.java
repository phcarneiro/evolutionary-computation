package br.ufg.inf.gaf6;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class Member {
    private Double fitness;
    private String cromosoma = new String();

    private Double sumPrecedentMembersFitness;

    public Member(StringBuilder builder) {
        this.cromosoma = builder.toString();
        this.fitness = 0.0;
    }

    public Double getSumPrecedentMembersFitness() {
        return sumPrecedentMembersFitness;
    }

    public void setSumPrecedentMembersFitness(Double sumPrecedentMembersFitness) {
        this.sumPrecedentMembersFitness = sumPrecedentMembersFitness;
    }

    public String getCromosoma() {
        return this.cromosoma;
    }

    public void setCromosoma(String cromosoma) {
        this.cromosoma = cromosoma;
    }

    public Double getFitness() {
        return this.fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Member() {
        Random rn = new Random();
        StringBuilder cromo = new StringBuilder();
        int contador = 0;
        while (contador < 44) {
            String gen = (rn.nextBoolean() == Boolean.FALSE) ? "0" : "1";
            cromo.append(gen);
            contador++;
        }
        this.cromosoma = cromo.toString();

        this.fitness = 0.0;
    }

    // Here I need to decode individual cromosome into two real values to be applied to the F6 function.
    public void calculateMemberFitness() {
        // Splitting cromosoma into two parts
        String xBin = this.cromosoma.substring(0, this.cromosoma.length()/2);
        String yBin = this.cromosoma.substring(this.cromosoma.length()/2, this.cromosoma.length());

        // Convert xBin and yBin to a decimal
        int xDec = Integer.parseInt(xBin, 2);
        int yDec = Integer.parseInt(yBin, 2);

        // Convert xDec and yDec to a real number
        // Sum to the minimum
        // Maximum to 5 digits after decimal
        Double xReal  = xDec * (200 / (Math.pow(2, 22) - 1));
        xReal = xReal + -100;
        Double yReal  = yDec * (200 / (Math.pow(2, 22) - 1));
        yReal = yReal + -100;

        xReal = Double.valueOf(Math.round(xReal*100000)/100000.0);
        yReal = Double.valueOf(Math.round(yReal*100000)/100000.0);

        // Applying xReal and yReal into the F6 function
        // Tests ok - here below
        Double xPow2AddyPow2 = Math.pow(xReal, 2) + Math.pow(yReal, 2);
        Double dividends = Math.pow(Math.sin(Math.sqrt(xPow2AddyPow2)), 2);
        dividends = dividends - 0.5;
        Double divisor = Math.pow(1 + (0.001 * (xPow2AddyPow2)), 2) ;
        Double f6 = (dividends/divisor);

        this.fitness = (0.5 - f6);
    }

    public String getXY() {
        // Splitting cromosoma into two parts
        String xBin = this.cromosoma.substring(0, this.cromosoma.length()/2);
        String yBin = this.cromosoma.substring(this.cromosoma.length()/2, this.cromosoma.length());

        // Convert xBin and yBin to a decimal
        int xDec = Integer.parseInt(xBin, 2);
        int yDec = Integer.parseInt(yBin, 2);

        // Convert xDec and yDec to a real number
        // Sum to the minimum
        // Maximum to 5 digits after decimal
        // DecimalFormat df = new DecimalFormat("0,00000");
        Double xReal  = xDec * (200 / (Math.pow(2, 22) - 1));
        xReal = xReal + -100;
        Double yReal  = yDec * (200 / (Math.pow(2, 22) - 1));
        yReal = yReal + -100;

        xReal = Double.valueOf(Math.round(xReal*100000)/100000.0);
        yReal = Double.valueOf(Math.round(yReal*100000)/100000.0);
        return xReal.toString() + " | " + yReal.toString();
    }
}