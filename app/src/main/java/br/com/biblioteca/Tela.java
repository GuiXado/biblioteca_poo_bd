package br.com.biblioteca;

import javafx.scene.layout.Pane;
// interface para as telas do sistema, cada tela tem que implementar essa interface e implementar o método render() que retorna um Pane com os componentes da tela
public interface Tela {
    Pane render();
}