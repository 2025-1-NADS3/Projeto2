package br.com.example.fecapayplus.data.model;

public class TransferenciaRequest {
    private int raOrigem;
    private int raDestino;
    private double valor;

    public TransferenciaRequest(int raOrigem, int raDestino, double valor) {
        this.raOrigem = raOrigem;
        this.raDestino = raDestino;
        this.valor = valor;
    }


    public int getRaOrigem() { return raOrigem; }
    public int getRaDestino() { return raDestino; }
    public double getValor() { return valor; }
}