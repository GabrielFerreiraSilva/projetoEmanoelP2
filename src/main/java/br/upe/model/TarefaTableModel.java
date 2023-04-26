package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


//importação das bibliotecas necessárias
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


public class TarefaTableModel extends AbstractTableModel {
    private List<Tarefa> tarefasFinalizadas;
    private List<Tarefa> tarefasAtivas;


    //Arquivos de texto que armazenam as tarefas ativas e finalizadas, respectivamente
    File txtTarefasAtivas = new File("tarefasAtivas.txt");
    File txtTarefasFinalizadas = new File("tarefasFinalizadas.txt");
    //Arquivo de texto auxiliar
    File txtAuxiliar = new File("auxiliar.txt");


    private boolean exibirFinalizadas;

    public TarefaTableModel() {
        tarefasAtivas = new ArrayList<>();
        tarefasFinalizadas = new ArrayList<>();


        /* O código abaixo insere na lista tarefasAtivas as tarefas armazenadas no
        documento tarefasAtivas.txt
         */
        try{
            /* É feita a leitura das linhas do arquivo e a criação de novas Tarefas
            que são adicionadas à lista tarefasAtivas
             */
            Scanner leitura = new Scanner(txtTarefasAtivas);

            while(leitura.hasNextLine()){

                tarefasAtivas.add(new Tarefa(leitura.nextLine(), 0));

            }

        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }


        //O mesmo ocorre para o arquivo de tarefas finalizadas
        try{
            Scanner leitura = new Scanner(txtTarefasFinalizadas);
            while(leitura.hasNextLine()){
                Tarefa tarefa = new Tarefa(leitura.nextLine(), 0);
                tarefasFinalizadas.add(tarefa);
                tarefa.setFinalizada(true);
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    public int getRowCount() {
        return tarefasAtivas.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefa = tarefasAtivas.get(rowIndex);
        switch (columnIndex) {
            case 0 : return tarefa.isFinalizada();
            case 1 : return tarefa.getDescricao();
        }
        return null;
    }

    public Class getColumnClass(int c) {
        switch (c) {
            case 0 : return Boolean.class;
            case 1 : return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tarefa tarefa = tarefasAtivas.get(rowIndex);
        tarefa.setFinalizada((Boolean) aValue);
        if (this.exibirFinalizadas) {
            if ((Boolean) aValue) {
                tarefasFinalizadas.add(tarefa);


                //Código que adiciona a descrição da tarefa ao arquivo de tarefas finalizadas
                try{
                    FileWriter escritor = new FileWriter(txtTarefasFinalizadas, true);
                    escritor.write(tarefa.getDescricao() + "\n");
                    escritor.close();
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }


            } else {
                tarefasFinalizadas.remove(tarefa);


                //Código que remove a tarefa do arquivo de tarefas finalizadas
                //Para isso é necessário o uso de um arquivo auxiliar
                try{
                    /*Será feita a leitura do arquivo de tarefas finalizadas e
                    a escrita no arquivo auxiliar */
                    Scanner leitura1 = new Scanner(txtTarefasFinalizadas);

                    FileWriter escritor1 = new FileWriter(txtAuxiliar, true);

                    while(leitura1.hasNextLine()){

                        String descricao = leitura1.nextLine();

                        /* São adicionadas ao arquivo auxiliar todas as linhas que forem
                        diferentes da linha que se deseja remover
                         */
                        if(!(tarefa.getDescricao().equals(descricao))){

                            escritor1.write(descricao + "\n");

                        }

                    }

                    escritor1.close();

                    //Todas as linhas do arquivo de tarefas finalizadas são apagadas
                    PrintWriter limpador1 = new PrintWriter(txtTarefasFinalizadas);

                    limpador1.flush();

                    limpador1.close();


                    /*
                    É feita a cópia de todas as linhas do arquivo auxiliar para
                    o arquivo de tarefas finalizadas
                     */
                    Scanner leitura2 = new Scanner(txtAuxiliar);

                    FileWriter escritor2 = new FileWriter(txtTarefasFinalizadas, true);

                    while(leitura2.hasNextLine()){

                        escritor2.write(leitura2.nextLine() + "\n");

                    }

                    escritor2.close();

                    //Todas as linhas do arquivo auxiliar são apagadas
                    PrintWriter limpador2 = new PrintWriter(txtAuxiliar);

                    limpador2.flush();

                    limpador2.close();


                }
                catch(Exception e){

                    System.out.println(e.getMessage());

                }



            }
        } else {
            if ((Boolean) aValue) {
                tarefasAtivas.remove(tarefa);

                //Código que remove a tarefa do arquivo de tarefas ativas
                try{
                    Scanner leitura1 = new Scanner(txtTarefasAtivas);
                    FileWriter escritor1 = new FileWriter(txtAuxiliar, true);
                    while(leitura1.hasNextLine()){
                        String descricao = leitura1.nextLine();
                        if(!(tarefa.getDescricao().equals(descricao))){
                            escritor1.write(descricao + "\n");
                        }
                    }
                    escritor1.close();
                    PrintWriter limpador1 = new PrintWriter(txtTarefasAtivas);
                    limpador1.flush();
                    limpador1.close();
                    Scanner leitura2 = new Scanner(txtAuxiliar);
                    FileWriter escritor2 = new FileWriter(txtTarefasAtivas, true);
                    while(leitura2.hasNextLine()){
                        escritor2.write(leitura2.nextLine() + "\n");
                    }
                    escritor2.close();
                    PrintWriter limpador2 = new PrintWriter(txtAuxiliar);
                    limpador2.flush();
                    limpador2.close();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }


                tarefasFinalizadas.add(tarefa);


                //Código que adiciona a tarefa ao arquivo de tarefas finalizadas
                try{
                    FileWriter escritor = new FileWriter(txtTarefasFinalizadas, true);
                    escritor.write(tarefa.getDescricao() + "\n");
                    escritor.close();
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }


            }
        }
        this.fireTableDataChanged();
    }


    public void setExibirFinalizadas(boolean selecionado) {
        this.exibirFinalizadas = selecionado;
        if (this.exibirFinalizadas) {
            this.tarefasAtivas.addAll(this.tarefasFinalizadas);
        } else {
            this.tarefasAtivas.removeAll(this.tarefasFinalizadas);
        }
        this.fireTableDataChanged();
    }

    public List<Tarefa> getTarefasFinalizadas() {
        return tarefasFinalizadas;
    }

    public void setTarefasFinalizadas(List<Tarefa> tarefasFinalizadas) {
        this.tarefasFinalizadas = tarefasFinalizadas;
    }

    public List<Tarefa> getTarefasAtivas() {
        return tarefasAtivas;
    }

    public void setTarefasAtivas(List<Tarefa> tarefasAtivas) {
        this.tarefasAtivas = tarefasAtivas;
    }

    public boolean isExibirFinalizadas() {
        return exibirFinalizadas;
    }

}
