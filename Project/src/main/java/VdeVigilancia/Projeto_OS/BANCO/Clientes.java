package VdeVigilancia.Projeto_OS.BANCO;

public class Clientes {
    private int id;
    private String Nome;
    private String Email;
    private String CPF;
    private String Telefone;


    public Clientes(int id, String nome, String email, String CPF, String telefone) {
        this.id = id;
        Nome = nome;
        Email = email;
        this.CPF = CPF;
        Telefone = telefone;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}
