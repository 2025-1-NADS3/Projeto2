package br.com.example.fecapayplus.data.model;


//Classe de usuário
public class User {
    private String nome;
    private String sobrenome;
    private Integer ra;
    private String email;
    private String senha;

    public User(String nome, String sobrenome, Integer ra, String email, String senha){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.ra = ra;
        this.email = email;
        this.senha = senha;
    }

    public User(String nome, String sobrenome, String email){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public User(Integer ra, String senha){
        this.ra = ra;
        this.senha = senha;
    }

    public String getName(){return nome;}
    public String getSurname(){return sobrenome;}
    public Integer getRa(){return ra;}
    public String getEmail(){return email;}
    public String getPassword(){return senha;}

}
