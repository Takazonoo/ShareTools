package Model;

public class Amigo {

    private int amigoId;
    private String amigoNome;
    private long amigoTelefone;
    private int emprestimosTotal; // Contador para saber quem tem mais emprestimos futuramente

    public Amigo() {
    }

    // Construtor para inserção (sem ID, pois é auto-incremento)
    public Amigo(String nome, long telefone, int totalEmprestimos) {
        this.amigoNome = nome;
        this.amigoTelefone = telefone;
        this.emprestimosTotal = totalEmprestimos;
    }

    // Construtor para atualizações
    public Amigo(int id, String nome, long telefone) {
        this.amigoId = id;
        this.amigoNome = nome;
        this.amigoTelefone = telefone;
    }

    // Construtor completo para leituras
    public Amigo(int id, String nome, long telefone, int totalEmprestimos) {
        this.amigoId = id;
        this.amigoNome = nome;
        this.amigoTelefone = telefone;
        this.emprestimosTotal = totalEmprestimos;
    }

    // Getters e Setters com os mesmos nomes, já que são padrão Java
    public int getId() {
        return amigoId;
    }

    public void setId(int id) {
        this.amigoId = id;
    }

    public String getNome() {
        return amigoNome;
    }

    public void setNome(String nome) {
        this.amigoNome = nome;
    }

    public long getTelefone() {
        return amigoTelefone;
    }

    public void setTelefone(long telefone) {
        this.amigoTelefone = telefone;
    }

    public int getQuantEmprest() {
        return emprestimosTotal;
    }

    public void setQuantEmprest(int totalEmprestimos) {
        this.emprestimosTotal = totalEmprestimos;
    }

    // Override do método toString para representação textual do objeto
    @Override
    public String toString() {
        return "\n ID: " + getId()
                + "\n Nome: " + getNome()
                + "\n Telefone: " + getTelefone()
                + "\n Total de Empréstimos: " + getQuantEmprest()
                + "\n -----------";
    }

}
