package br.ufg.inf.auditOP;

public class Municipio {

    private StringBuilder nome;

    private Porte porteEnumeration;

    public Porte getPorteEnumeration() {
        return porteEnumeration;
    }

    public void setPorteEnumeration(Porte porteEnumeration) {
        this.porteEnumeration = porteEnumeration;
    }

    public StringBuilder getNome() {
        return nome;
    }

    public void setNome(StringBuilder nome) {
        this.nome = nome;
    }

    public Municipio(StringBuilder nome, Porte porteEnumeration) {
        this.nome = nome;
        this.porteEnumeration = porteEnumeration;
    }
}
