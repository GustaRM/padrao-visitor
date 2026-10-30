package padroescomportamentais.visitor;

/**
 * Segundo Visitor concreto: exibe os dados de Aluno, Professor e Funcionario
 * no formato JSON. Nenhuma classe do modelo (Pessoa e suas implementações)
 * precisou ser alterada — esse é o ganho do padrão Visitor.
 */
public class JsonVisitor implements Visitor {

    public String exibir(Pessoa pessoa) {
        return pessoa.aceitar(this);
    }

    @Override
    public String exibirAluno(Aluno aluno) {
        return "{"
                + "\"tipo\":\"Aluno\","
                + "\"matricula\":" + aluno.getMatricula() + ","
                + "\"nome\":\"" + escapar(aluno.getNome()) + "\","
                + "\"curso\":\"" + escapar(aluno.getNomeCurso()) + "\""
                + "}";
    }

    @Override
    public String exibirProfessor(Professor professor) {
        return "{"
                + "\"tipo\":\"Professor\","
                + "\"matricula\":" + professor.getMatricula() + ","
                + "\"nome\":\"" + escapar(professor.getNome()) + "\","
                + "\"titulacao\":\"" + escapar(professor.getTitulacao()) + "\""
                + "}";
    }

    @Override
    public String exibirFuncionario(Funcionario funcionario) {
        return "{"
                + "\"tipo\":\"Funcionario\","
                + "\"codigo\":" + funcionario.getCodigo() + ","
                + "\"nome\":\"" + escapar(funcionario.getNome()) + "\","
                + "\"salario\":" + funcionario.getSalario()
                + "}";
    }

    /** Escapa aspas e barras para o JSON continuar válido. */
    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
