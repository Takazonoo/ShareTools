package Model;

import java.sql.Date;

public class Emprestimo {

    private int emprestimoId; // ID do empréstimo
    private Amigo emprestimoAmigo; // Amigo associado ao empréstimo
    private Ferramenta emprestimoFerramenta; // Ferramenta associada ao empréstimo
    private Date dataEmprestimo; // Data em que a ferramenta foi emprestada
    private Date dataDevolucao; // Data em que a ferramenta foi devolvida
    private boolean emprestimoStatus; // Status do empréstimo (concluído ou pendente)

    // Construtor padrão
    public Emprestimo() {
    }

    // Construtor com todos os atributos
    public Emprestimo(int id, Amigo amigo, Ferramenta ferramenta, Date dataEmprestimo, Date dataDevolucao) {
        this.emprestimoId = id;
        this.emprestimoAmigo = amigo;
        this.emprestimoFerramenta = ferramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.emprestimoStatus = (dataDevolucao != null); // Status é verdadeiro se a data de devolução não for nula
    }

    // Construtor para cadastro de um novo empréstimo sem ID (normalmente gerado automaticamente pelo banco de dados)
    public Emprestimo(Amigo amigo, Ferramenta ferramenta, Date dataEmprestimo, Date dataDevolucao) {
        this.emprestimoAmigo = amigo;
        this.emprestimoFerramenta = ferramenta;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.emprestimoStatus = (dataDevolucao != null); // Status é verdadeiro se a data de devolução não for nula
    }

    // Getter para o ID do empréstimo
    public int getId() {
        return emprestimoId;
    }

    // Setter para o ID do empréstimo
    public void setId(int id) {
        this.emprestimoId = id;
    }

    // Getter para o amigo associado ao empréstimo
    public Amigo getAmigo() {
        return emprestimoAmigo;
    }

    // Setter para o amigo associado ao empréstimo
    public void setAmigo(Amigo amigo) {
        this.emprestimoAmigo = amigo;
    }

    // Getter para a ferramenta associada ao empréstimo
    public Ferramenta getFerramenta() {
        return emprestimoFerramenta;
    }

    // Setter para a ferramenta associada ao empréstimo
    public void setFerramenta(Ferramenta ferramenta) {
        this.emprestimoFerramenta = ferramenta;
    }

    // Getter para a data do empréstimo
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    // Setter para a data do empréstimo
    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    // Getter para a data de devolução
    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    // Setter para a data de devolução
    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    // Getter para o status do empréstimo (concluído ou pendente)
    public boolean isStatus() {
        return emprestimoStatus;
    }

    // Setter para o status do empréstimo
    public void setStatus(boolean status) {
        this.emprestimoStatus = status;
    }

    // Método toString para retornar uma representação textual do empréstimo
    @Override
    public String toString() {
        String pendencia;
        if(this.isStatus()){
            pendencia = "CONCLUIDO"; // Status se a devolução foi realizada
        } else {
            pendencia = "PENDENTE"; // Status se a devolução não foi realizada
        }
        return "\n ID do empréstimo: " + this.getId()
                + "\n Nome do amigo: " + this.getAmigo().getNome()
                + "\n Nome da ferramenta: " + this.getFerramenta().getNome()
                + "\n Data do empréstimo: " + this.getDataEmprestimo()
                + "\n Data de devolução: " + this.getDataDevolucao()
                + "\n Status: " + pendencia
                + "\n -----------";
    }

}
