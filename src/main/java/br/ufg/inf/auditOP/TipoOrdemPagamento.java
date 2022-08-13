package br.ufg.inf.auditOP;

public enum TipoOrdemPagamento {
    Despesa(2), Restos(3), ServicoDivida(8), DespesaExtra(9);

    public int valor;

    TipoOrdemPagamento(int i) {
        valor = i;
    }
}
