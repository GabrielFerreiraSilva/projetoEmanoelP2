package br.upe.controller;

import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.event.TableModelListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Importação das bibliotecas File e FileWriter
import java.io.File;
import java.io.FileWriter;

public class TarefaControlador {

    // Atributos
    private TarefaTableModel tarefaTableModel;

    //Arquivo de texto que armazenará as atividades ativas
    File txtTarefasAtivas = new File("tarefasAtivas.txt");

    //Construtor
    public TarefaControlador() {
        tarefaTableModel = new TarefaTableModel();
    }

    //Metodos de negocio
    public void adicionarTarefaAtiva(Tarefa tarefa) {
        this.tarefaTableModel.getTarefasAtivas().add(tarefa);

        //Cada nova tarefa é adicionada à lista de tarefas ativas
        //O código abaixo irá inserir a descrição da tarefa em um arquivo de texto

        try{

            //Declaração de um FileWriter para adicionar a tarefa ao arquivo
            FileWriter escritor = new FileWriter(txtTarefasAtivas, true);
            //O FileWriter escreve no arquivo a descrição da tarefa
            escritor.write(tarefa.getDescricao() + "\n");
            escritor.close();

        }
        catch(IOException e){

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
