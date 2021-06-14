import java.util.*

class Aluno(var nome: String,
            var sobrenome: String,
            var codigoAluno: Int){

    var cursosMatriculados = mutableListOf<Curso>()
    fun adicionaCurso(curso: Curso){
        cursosMatriculados.add(curso)
        println("Aluno ${nome} esta adicionado no curso ${curso.nome}")
    }
}
class Curso(var nome: String,
            var codigo: Int,
            var quantidadeMaximaAlunos: Int){
    var professorTitular: Titular? = null
    var professorAdjunto: Adjunto? = null

    var alunosMatriculados: MutableList<Aluno> = mutableListOf()

    fun adicionarAluno(aluno: Aluno):Boolean{
        if (alunosMatriculados.count() < quantidadeMaximaAlunos){
            alunosMatriculados.add(aluno)
            println("Aluno ${aluno.nome} adicionado com sucesso.")
            return true
        }
        println("Falha ao adicionar o aluno ${aluno.nome}.")
        return false
    }
    fun excluirAluno(aluno: Aluno){
        for(alunoArray in alunosMatriculados){
            if(alunoArray.nome == aluno.nome){
                alunosMatriculados.remove(alunoArray)
                println("Aluno ${aluno.nome} removido com sucesso.")
            }else{
                println("Aluno ${aluno.nome} não encontrado.")
            }
        }
    }
}

open class Professor(var nome: String,
                     var sobrenome: String,
                     var tempoDeCasa: Int,
                     var codigoProfessor: Int){

}

class Titular(nome: String,
              sobrenome: String,
              tempoDeCasa: Int,
              codigo: Int,
              var especialidade: String):Professor(nome, sobrenome, tempoDeCasa,codigo){

}

class Adjunto(nome:String,
              sobrenome: String,
              tempoDeCasa: Int,
              codigo: Int,
              var quantidadeHorasMonitoria: Int):Professor(nome,sobrenome, tempoDeCasa, codigo){

}
class Matricula(var aluno: Aluno,
                var curso: Curso,
                var dataMatricula: Date){

}

class DigitalHouseManager(var listaAlunos: MutableList<Aluno>,
                          var listaProfessor: MutableList<Professor>,
                          var listaMatricula: MutableList<Matricula>,
                          var listaCurso: MutableList<Curso>) {
    fun registrarCurso(nome: String, codigo: Int, quantidadeMaximaAlunos: Int) {
        var cursonovo = Curso(nome, codigo, quantidadeMaximaAlunos)
        listaCurso.add(cursonovo)
        println("Curso ${cursonovo.nome} adicionado com sucesso.")
    }

    fun excluirCurso(curso: Curso) {
        for (cursoArray in listaCurso) {
            if (cursoArray.nome == curso.nome) {
                listaCurso.remove(curso)
                println("Curso ${curso.nome} removido com sucesso.")
            } else {
                println("Curso ${curso.nome} não encontrado.")
            }
        }
    }

    fun registrarProfessorAdjunto(nome: String, sobrenome: String, codigo: Int, quantidadeHorasMonitoria: Int) {
        var novoAdjunto = Adjunto(nome, sobrenome, 0, codigo, quantidadeHorasMonitoria)
        listaProfessor.add(novoAdjunto)
        println("Professor ${novoAdjunto.nome}  adjunto adicionado com sucesso.")
    }

    fun registrarProfessorTitular(nome: String, sobrenome: String, codigo: Int, especialidade: String) {
        var novoTitular = Titular(nome, sobrenome, 0, codigo, especialidade)
        listaProfessor.add(novoTitular)
        println("Professor ${novoTitular.nome} titular adicionado com sucesso.")
    }

    fun excluirProfessor(codigoProfessor: Int) {
        for (professorArray in listaProfessor) {
            if (professorArray.codigoProfessor == codigoProfessor) {
                listaProfessor.remove(professorArray)
                println("Professor ${professorArray.nome} removido com sucesso.")
            } else {
                println("Professor ${professorArray.nome} não encontrado.")
            }
        }
    }

    fun registraAluno(nome: String, sobrenome: String, codigoAluno: Int) {
        var novoAluno = Aluno(nome, sobrenome, codigoAluno)
        listaAlunos.add(novoAluno)
        println("Aluno ${novoAluno.nome} registrado com sucesso.")
    }

    fun matriculaAluno(codigoAluno: Int, codigoCurso: Int){
        for (alunoArray in listaAlunos) {
            if (alunoArray.codigoAluno == codigoAluno) {
                for (cursoArray in listaCurso){
                    if (cursoArray.codigo == codigoCurso){
                        if(cursoArray.adicionarAluno(alunoArray)){
                            var novaMatricula = Matricula(alunoArray,cursoArray, Date())
                            listaMatricula.add(novaMatricula)
                            println("Matricula ${novaMatricula.aluno.nome} adicionada com sucesso.")
                            alunoArray.adicionaCurso(cursoArray)
                        }
                    }
                }
            }
        }
    }
    fun alocarProfessores(codigoCurso: Int, codigoProfessor: Int, codigoAdjunto: Int){
        for (professorArray in listaProfessor){
            if(professorArray.codigoProfessor == codigoProfessor || professorArray.codigoProfessor == codigoAdjunto){
                for(cursoArray in listaCurso){
                    if(cursoArray.codigo == codigoCurso){
                        when(professorArray){
                            is Titular -> {
                                println("Professor Titular ${professorArray.nome} alocado no curso ${cursoArray.nome} com sucesso.")
                                cursoArray.professorTitular = professorArray
                            }
                            is Adjunto -> {
                                println("Professor Adjunto ${professorArray.nome} alocado no curso ${cursoArray.nome} com sucesso.")
                                cursoArray.professorAdjunto = professorArray
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    var listaAlunos = mutableListOf<Aluno>()
    var listaMatricula = mutableListOf<Matricula>()
    var listaCurso = mutableListOf<Curso>()
    var listaProfessor = mutableListOf<Professor>()

    var digitalHouseManager = DigitalHouseManager(listaAlunos, listaProfessor, listaMatricula, listaCurso)

    var professorTitular1 = Titular("Marcella", "Anjos", 0, 1, "Vidente")
    var professorTitular2 = Titular("Juju", "Acc", 0, 2, "SpiderMan")

    var professorAdjunto1 = Adjunto("Angelo", "Gamer", 0, 3, 0)
    var professorAdjunto2 = Adjunto("Antonio", "Comercios", 0, 4, 0)

    var curso1 = Curso("Full Stack", 20001, 3)
    var curso2 = Curso("Android", 20002, 2)

    var aluno1 = Aluno("Daniel", "Roft", 1)
    var aluno2 = Aluno("Rafael", "Smug", 2)
    var aluno3 = Aluno("Otavio", "Lift", 3)
    var aluno4 = Aluno("Simon", "Congt", 4)
    var aluno5 = Aluno("Jack", "Rifftz", 5)

    digitalHouseManager.registrarProfessorTitular(
        professorTitular1.nome,
        professorTitular1.sobrenome,
        professorTitular1.codigoProfessor,
        professorTitular1.especialidade
    )

    digitalHouseManager.registrarProfessorTitular(professorTitular2.nome,
        professorTitular2.sobrenome,
        professorTitular2.codigoProfessor,
        professorTitular2.especialidade)

    digitalHouseManager.registrarProfessorAdjunto(
        professorAdjunto1.nome,
        professorAdjunto1.sobrenome,
        professorAdjunto1.codigoProfessor,
        professorAdjunto1.quantidadeHorasMonitoria
    )
    digitalHouseManager.registrarProfessorAdjunto(
        professorAdjunto2.nome,
        professorAdjunto2.sobrenome,
        professorAdjunto2.codigoProfessor,
        professorAdjunto2.quantidadeHorasMonitoria
    )
    digitalHouseManager.registrarCurso(curso1.nome, curso1.codigo, curso1.quantidadeMaximaAlunos)
    digitalHouseManager.registrarCurso(curso2.nome, curso2.codigo, curso2.quantidadeMaximaAlunos)

    digitalHouseManager.alocarProfessores(curso1.codigo,
        professorTitular1.codigoProfessor,
        professorAdjunto1.codigoProfessor)

    digitalHouseManager.alocarProfessores(curso2.codigo,
        professorAdjunto2.codigoProfessor,
        professorTitular2.codigoProfessor)

    digitalHouseManager.registraAluno(aluno1.nome, aluno1.sobrenome, aluno1.codigoAluno)
    digitalHouseManager.registraAluno(aluno2.nome, aluno2.sobrenome, aluno2.codigoAluno)
    digitalHouseManager.registraAluno(aluno3.nome, aluno3.sobrenome, aluno3.codigoAluno)
    digitalHouseManager.registraAluno(aluno4.nome, aluno4.sobrenome, aluno4.codigoAluno)
    digitalHouseManager.registraAluno(aluno5.nome, aluno5.sobrenome, aluno5.codigoAluno)

    digitalHouseManager.matriculaAluno(aluno1.codigoAluno,curso1.codigo)
    digitalHouseManager.matriculaAluno(aluno2.codigoAluno,curso1.codigo)

    digitalHouseManager.matriculaAluno(aluno3.codigoAluno,curso2.codigo)
    digitalHouseManager.matriculaAluno(aluno4.codigoAluno,curso2.codigo)
    digitalHouseManager.matriculaAluno(aluno5.codigoAluno,curso2.codigo)
}
