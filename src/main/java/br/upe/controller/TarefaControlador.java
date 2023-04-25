package br.upe.controller;

import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class TarefaControlador {

    // Atributos
    private TarefaTableModel tarefaTableModel;

    //Construtor
    public TarefaControlador() {
        tarefaTableModel = new TarefaTableModel();
    }

    //Metodos de negocio
    public void adicionarTarefaAtiva(Tarefa tarefa) {
        this.tarefaTableModel.getTarefasAtivas().add(tarefa);

        try{

            FileWriter escritor = new FileWriter("tarefasAtivas.txt", true);

            escritor.write(tarefa.getDescricao() + "\n");

            escritor.close();

        }
        catch(Exception e){

            System.out.println(e.getMessage());

        }

    }

    public void exibirFinalizadas(boolean exibir) {
        tarefaTableModel.setExibirFinalizadas(exibir);
    }


    //Getter e Setters

    public TarefaTableModel getTarefaTableModel() {
        return tarefaTableModel;
    }

    public void setTarefaTableModel(TarefaTableModel tarefaTableModel) {
        this.tarefaTableModel = tarefaTableModel;
    }

}
