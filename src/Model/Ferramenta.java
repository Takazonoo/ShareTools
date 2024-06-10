package Model;

public class Ferramenta {

    private int ferramentaId; // ID da ferramenta
    private String ferramentaNome; // Nome da ferramenta
    private String ferramentaMarca; // Marca da ferramenta
    private double ferramentaCustoAquisicao; // Custo de aquisição da ferramenta

    // Construtor padrão
    public Ferramenta() {
    }

    // Construtor sem ID (usado para criação de novas ferramentas, ID será auto-incrementado)
    public Ferramenta(String nome, String marca, double custoAquisicao) {
        this.ferramentaNome = nome;
        this.ferramentaMarca = marca;
        this.ferramentaCustoAquisicao = custoAquisicao;
    }

    // Construtor com todos os atributos (usado para seleção de ferramentas do banco de dados)
    public Ferramenta(int id, String nome, String marca, double custoAquisicao) {
        this.ferramentaId = id;
        this.ferramentaNome = nome;
        this.ferramentaMarca = marca;
        this.ferramentaCustoAquisicao = custoAquisicao;
    }

    // Getter para o ID da ferramenta
    public int getId() {
        return ferramentaId;
    }

    // Setter para o ID da ferramenta
    public void setId(int id) {
        this.ferramentaId = id;
    }

    // Getter para o nome da ferramenta
    public String getNome() {
        return ferramentaNome;
    }

    // Setter para o nome da ferramenta
    public void setNome(String nome) {
        this.ferramentaNome = nome;
    }

    // Getter para a marca da ferramenta
    public String getMarca() {
        return ferramentaMarca;
    }

    // Setter para a marca da ferramenta
    public void setMarca(String marca) {
        this.ferramentaMarca = marca;
    }

    // Getter para o custo de aquisição da ferramenta
    public double getCustoAquisicao() {
        return ferramentaCustoAquisicao;
    }

    // Setter para o custo de aquisição da ferramenta
    public void setCustoAquisicao(double custoAquisicao) {
        this.ferramentaCustoAquisicao = custoAquisicao;
    }

}
