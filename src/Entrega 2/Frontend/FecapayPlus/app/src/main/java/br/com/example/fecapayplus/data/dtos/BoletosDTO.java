package br.com.example.fecapayplus.data.dtos;

import java.util.List;

import br.com.example.fecapayplus.data.model.Boleto;

//DTO para lidar com a lista de boletos
public class BoletosDTO {
    private List<Boleto> boletos;
    public BoletosDTO(List<Boleto> boletos){
        this.boletos = boletos;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public void setBoletos(List<Boleto> boletos) {
        this.boletos = boletos;
    }
}
