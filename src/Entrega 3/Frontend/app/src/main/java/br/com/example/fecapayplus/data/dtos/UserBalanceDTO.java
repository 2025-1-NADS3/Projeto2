package br.com.example.fecapayplus.data.dtos;

import com.google.gson.annotations.SerializedName;

//DTO para lidar com o recebimento do saldo do usuário
public class UserBalanceDTO {
    @SerializedName("saldo")
    private double saldo;

    public double getSaldo() {
        return this.saldo;
    }
    public void setSaldo(double saldo){this.saldo = saldo;}
}
