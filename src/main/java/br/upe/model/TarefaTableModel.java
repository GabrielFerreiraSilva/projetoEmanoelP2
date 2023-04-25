package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.io.*;

public class TarefaTableModel extends AbstractTableModel {
    private List<Tarefa> tarefasFinalizadas;

    File txtTarefasAtivas = new File("tarefasAtivas.txt");

    File txtTarefasFinalizadas = new File("tarefasFinalizadas.txt");

    File auxiliar = new File("txtAuxiliar.txt");

    File auxiliar2 = new File("txtAuxiliar2.txt");

    private List<Tarefa> tarefasAtivas;

    private boolean exibirFinalizadas;

    public TarefaTableModel() {
        tarefasAtivas = new ArrayList<>();
        tarefasFinalizadas = new ArrayList<>();


        try{

            Scanner leitura = new Scanner(txtTarefasAtivas);

            Scanner leitura2 = new Scanner(txtTarefasFinalizadas);

            while(leitura.hasNextLine()){

                Tarefa tarefa = new Tarefa(leitura.nextLine(), 0);

                this.tarefasAtivas.add(tarefa);

            }

            while(leitura2.hasNextLine()){

                Tarefa tarefa = new Tarefa(leitura2.nextLine(), 0);

                tarefa.setFinalizada(true);

                this.tarefasFinalizadas.add(tarefa);

            }

        }
        catch(Exception e){

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

                try{

                    FileWriter escritor = new FileWriter(txtTarefasFinalizadas, true);

                    escritor.write(tarefa.getDescricao() + "\n");

                    escritor.close();

                }
                catch(Exception e){

                    System.out.println(e.getMessage());

                }

            } else {
                tarefasFinalizadas.remove(tarefa);

                try{

                    Scanner leitura = new Scanner(txtTarefasFinalizadas);

                    FileWriter escritor = new FileWriter(auxiliar, true);

                    while(leitura.hasNextLine()){

                        String linha = leitura.nextLine();

                        if(!(linha.equals(tarefa.getDescricao()))){

                            escritor.write(linha + "\n");

                        }

                    }



                    escritor.close();

                    PrintWriter limpador = new PrintWriter(txtTarefasFinalizadas);
                    limpador.flush();
                    limpador.close();

                    Scanner leitura2 = new Scanner(auxiliar);

                    FileWriter escritor2 = new FileWriter(txtTarefasFinalizadas, true);

                    while(leitura2.hasNextLine()){

                        escritor2.write(leitura2.nextLine() + "\n");

                    }

                    escritor2.close();

                    PrintWriter limpador2 = new PrintWriter(auxiliar);
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



                try{

                    Scanner leitura = new Scanner(txtTarefasAtivas);

                    //Acho que dá pra eliminar esse esse auxiliar 2

                    FileWriter escritor = new FileWriter(auxiliar2, true);

                    while(leitura.hasNextLine()){

                        String linha = leitura.nextLine();

                        if(!(linha.equals(tarefa.getDescricao()))){

                            escritor.write(linha + "\n");

                        }

                    }



                    escritor.close();

                    PrintWriter limpador = new PrintWriter(txtTarefasAtivas);
                    limpador.flush();
                    limpador.close();

                    Scanner leitura2 = new Scanner(auxiliar2);

                    FileWriter escritor2 = new FileWriter(txtTarefasAtivas, true);

                    while(leitura2.hasNextLine()){

                        escritor2.write(leitura2.nextLine() + "\n");

                    }

                    escritor2.close();

                    PrintWriter limpador2 = new PrintWriter(auxiliar2);
                    limpador2.flush();
                    limpador2.close();

                }
                catch(Exception e){

                    System.out.println(e.getMessage());

                }


                tarefasFinalizadas.add(tarefa);

                try{

                    FileWriter escritor = new FileWriter(txtTarefasFinalizadas, true);

                    escritor.write(tarefa.getDescricao() + "\n");

                    escritor.close();

                }
                catch(Exception e){

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
