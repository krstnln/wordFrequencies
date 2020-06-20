/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poemanalyzer;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

/**
 * @author Kris Lane To count word frequencies found in a file
 */

public class PoemAnalyzer extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Label instructions = new Label("This program will find the top 20 mots frequent words in a file."); 
        Label inputLabel = new Label("Please enter the location of the file:"); 
        TextField inputText = new TextField();
        Button enter = new Button("Enter");  

        GridPane gridPane = new GridPane();    
        gridPane.setMinSize(550, 75);      
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));  
        gridPane.setVgap(10); 
        gridPane.setHgap(10);       
        gridPane.setAlignment(Pos.TOP_CENTER); 
        gridPane.add(inputLabel, 0, 1);       
        gridPane.add(inputText, 1, 1); 
        gridPane.add(enter, 2, 1);      

        enter.setStyle("-fx-background-color: lightgreen; -fx-textfill: white;"); 
        inputLabel.setStyle("-fx-font: normal bold 15px 'serif' "); 
        gridPane.setStyle("-fx-background-color: beige;");       

        Scene scene = new Scene(gridPane);   
        stage.setTitle("Word Frequency Analyzer"); 
        stage.setScene(scene);  
        stage.show(); 

        enter.setOnAction(enterac -> 
        {
            String inputFile = inputText.getText();

            File fileName = new File(inputFile);
            FileReader read = null;
            try {
                read = new FileReader(fileName);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PoemAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader buff = new BufferedReader(read);

            Map<String, Integer> frequency = new HashMap<>();
            ArrayList<Map.Entry<String, Integer>> arrayList;
            arrayList = new ArrayList<>();
            LinkedHashMap<String, Integer> sortedFreq;
            sortedFreq = new LinkedHashMap<String, Integer>();

            String Poem;
            String[] Word;

            try {
                while((Poem = buff.readLine()) != null){
                    Word = Poem.split(" ");
                    for (String Current : Word) {
                        Current = format(Current);
                        frequency.compute(Current, (k,v) -> (v == null) ? v = 1 : v + 1);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(PoemAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            }

            for(Map.Entry<String, Integer> e: frequency.entrySet())
                arrayList.add(e);

            Comparator<Map.Entry<String, Integer>> comp;
            comp = (Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> {
                Integer int1 = e1.getValue();
                Integer int2 = e2.getValue();
                return int2.compareTo(int1);
            };

            Collections.sort(arrayList, comp);   

            GridPane gridPane2 = new GridPane();   
            Scene secondScene = new Scene(gridPane2, 350, 350);
            Stage newWindow = new Stage();
            newWindow.setTitle("Top 20 Most Frequent Words");
            newWindow.setScene(secondScene);

            newWindow.show();
            gridPane2.setMinSize(300, 300);      
            gridPane2.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));  
            gridPane2.setVgap(10); 
            gridPane2.setHgap(75);       
            gridPane2.setAlignment(Pos.TOP_CENTER);    

            gridPane2.setStyle("-fx-background-color: beige;");      
            
             int i = 0;
            int j = 0;
            int k = 2;
            

            for(Map.Entry<String, Integer> e: arrayList) {
                sortedFreq.put(e.getKey(), e.getValue());
                Label word = new Label(e.getKey() + ", " + e.getValue() +"\n");
                gridPane2.add(word, j, k);
                word.setStyle("-fx-font: normal bold 15px 'serif' "); 

                if (i == 9) {
                    j = 1;
                    k = 2;
                    i++;
                }
                else if (i == 19) {
                    break;
                        }
                else {
                    k++;
                    i++;
                } 
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
       
    public static String format(String Current) {
     Current = Current.toLowerCase();

     Current = Current.replaceAll(" ", "");
     Current = Current.replaceAll(" , ", "");
     Current = Current.replaceAll("\\,", "");
     Current = Current.replaceAll(",?", "");
     Current = Current.replaceAll("!", "");
     Current = Current.replaceAll("\\?", "");
     Current = Current.replaceAll("—", "");
     Current = Current.replaceAll("\"", "");
     Current = Current.replaceAll(";", "");
     Current = Current.replaceAll("\\.", "");

     return Current;
    }   
}

    

