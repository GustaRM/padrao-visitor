package padroescomportamentais.visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonVisitorTest {

    private JsonVisitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new JsonVisitor();
    }

    @Test
    void deveExibirAlunoEmJson() {
        Aluno aluno = new Aluno(123, "Ana", new Curso("Sistemas de Informação"));

        String json = visitor.exibirAluno(aluno);

        assertEquals(
                "{\"tipo\":\"Aluno\",\"matricula\":123,\"nome\":\"Ana\",\"curso\":\"Sistemas de Informação\"}",
                json);
    }

    @Test
    void deveExibirProfessorEmJson() {
        Professor professor = new Professor(45, "Carlos", "Doutor");

        String json = visitor.exibirProfessor(professor);

        assertEquals(
                "{\"tipo\":\"Professor\",\"matricula\":45,\"nome\":\"Carlos\",\"titulacao\":\"Doutor\"}",
                json);
    }

    @Test
    void deveExibirFuncionarioEmJson() {
        Funcionario funcionario = new Funcionario(7, "Beatriz", 3500.5f);

        String json = visitor.exibirFuncionario(funcionario);

        assertEquals(
                "{\"tipo\":\"Funcionario\",\"codigo\":7,\"nome\":\"Beatriz\",\"salario\":3500.5}",
                json);
    }

    @Test
    void deveDespacharPeloMetodoAceitar() {
        // Garante o double dispatch: Pessoa.aceitar chama o método correto do Visitor
        Pessoa aluno = new Aluno(1, "Ana", new Curso("Direito"));
        Pessoa professor = new Professor(2, "Carlos", "Mestre");
        Pessoa funcionario = new Funcionario(3, "Beatriz", 2000f);

        assertTrue(aluno.aceitar(visitor).startsWith("{\"tipo\":\"Aluno\""));
        assertTrue(professor.aceitar(visitor).startsWith("{\"tipo\":\"Professor\""));
        assertTrue(funcionario.aceitar(visitor).startsWith("{\"tipo\":\"Funcionario\""));
    }

    @Test
    void deveExibirColecaoHeterogeneaViaExibir() {
        List<Pessoa> pessoas = List.of(
                new Aluno(1, "Ana", new Curso("Direito")),
                new Professor(2, "Carlos", "Mestre"),
                new Funcionario(3, "Beatriz", 2000f));

        String[] esperados = {
                "{\"tipo\":\"Aluno\",\"matricula\":1,\"nome\":\"Ana\",\"curso\":\"Direito\"}",
                "{\"tipo\":\"Professor\",\"matricula\":2,\"nome\":\"Carlos\",\"titulacao\":\"Mestre\"}",
                "{\"tipo\":\"Funcionario\",\"codigo\":3,\"nome\":\"Beatriz\",\"salario\":2000.0}"
        };

        for (int i = 0; i < pessoas.size(); i++) {
            assertEquals(esperados[i], visitor.exibir(pessoas.get(i)));
        }
    }

    @Test
    void deveEscaparAspasNoNome() {
        Aluno aluno = new Aluno(9, "João \"Jota\" Silva", new Curso("Física"));

        String json = visitor.exibirAluno(aluno);

        assertEquals(
                "{\"tipo\":\"Aluno\",\"matricula\":9,\"nome\":\"João \\\"Jota\\\" Silva\",\"curso\":\"Física\"}",
                json);
    }

    @Test
    void doisVisitorsProduzemFormatosDiferentesParaMesmoObjeto() {
        Aluno aluno = new Aluno(1, "Ana", new Curso("Direito"));
        PessoaVisitor textoVisitor = new PessoaVisitor();

        String texto = aluno.aceitar(textoVisitor);
        String json = aluno.aceitar(visitor);

        assertEquals("Aluno{matricula=1, nome='Ana', curso=Direito}", texto);
        assertEquals("{\"tipo\":\"Aluno\",\"matricula\":1,\"nome\":\"Ana\",\"curso\":\"Direito\"}", json);
    }
}
