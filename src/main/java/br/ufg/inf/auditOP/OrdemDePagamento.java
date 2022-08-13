package br.ufg.inf.auditOP;

import java.math.BigInteger;

public class OrdemDePagamento {
    private Integer opID;
    private Integer numeroOP;
    private TipoOrdemPagamento tipo;
    private Double valor;
    private RelevanciaMontantePagamento relevanciaMontantePagamento;

    public OrdemDePagamento(Integer opID, Integer numeroOP, TipoOrdemPagamento tipo, Double valor) {
        this.opID = opID;
        this.numeroOP = numeroOP;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Integer getOpID() {
        return opID;
    }

    public void setOpID(Integer opID) {
        this.opID = opID;
    }

    public Integer getNumeroOP() {
        return numeroOP;
    }

    public void setNumeroOP(Integer numeroOP) {
        this.numeroOP = numeroOP;
    }

    public TipoOrdemPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrdemPagamento tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public RelevanciaMontantePagamento getRelevanciaMontantePagamento() {
        return relevanciaMontantePagamento;
    }

    public void setRelevanciaMontantePagamento(RelevanciaMontantePagamento relevanciaMontantePagamento) {
        this.relevanciaMontantePagamento = relevanciaMontantePagamento;
    }
}
