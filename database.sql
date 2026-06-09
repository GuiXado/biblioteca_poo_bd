CREATE DATABASE biblioteca;
GO

USE biblioteca;
GO

CREATE TABLE Livro (
    idLivro INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    editora VARCHAR(100),
    dataPublicacao DATE,
    quantidade INT NOT NULL
);

CREATE TABLE Estudante (
    idEstudante INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    curso VARCHAR(100),
    telefone VARCHAR(20)
);

CREATE TABLE Emprestimo (
    idEmprestimo INT IDENTITY(1,1) PRIMARY KEY,
    idLivro INT NOT NULL,
    idEstudante INT NOT NULL,
    dataEmprestimo DATE NOT NULL,
    dataPrevista DATE NOT NULL,
    dataDevolucao DATE NULL,

    CONSTRAINT FK_Emprestimo_Livro
        FOREIGN KEY (idLivro)
        REFERENCES Livro(idLivro),

    CONSTRAINT FK_Emprestimo_Estudante
        FOREIGN KEY (idEstudante)
        REFERENCES Estudante(idEstudante)
);