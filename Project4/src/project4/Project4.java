/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author dan
 */
public class Project4 extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox main = new VBox(15);
        GridPane sortAlgGrid = new GridPane();
        GridPane inputTypeGrid = new GridPane();
        GridPane inputBlockGrid = new GridPane();
        Label sortingAlgLabel = new Label("Sorting Algorithm");
        Label inputTypeLabel = new Label("Input Type");
        Label inputSizeLabel = new Label("Input Size");
        Label blockSizeLabel = new Label("Block Size");
        TextField inputSizeField = new TextField();
        TextField blockSizeField = new TextField();
        Button go = new Button("Go");
        RadioButton selection = new RadioButton("Selection");
        RadioButton bubble = new RadioButton("Bubble");
        RadioButton insertion = new RadioButton("Insertion");
        RadioButton quick = new RadioButton("Quick");
        ToggleGroup sortGroup = new ToggleGroup();
        selection.setToggleGroup(sortGroup);
        bubble.setToggleGroup(sortGroup);
        insertion.setToggleGroup(sortGroup);
        quick.setToggleGroup(sortGroup);

        RadioButton alreadySort = new RadioButton("Already sorted");
        RadioButton reverseOrd = new RadioButton("Reverse order");
        RadioButton random = new RadioButton("Random");
        ToggleGroup inputType = new ToggleGroup();
        alreadySort.setToggleGroup(inputType);
        reverseOrd.setToggleGroup(inputType);
        random.setToggleGroup(inputType);

        main.getChildren().add(sortAlgGrid);
        main.getChildren().add(inputTypeGrid);
        main.getChildren().add(inputBlockGrid);
        main.getChildren().add(go);
        sortAlgGrid.add(sortingAlgLabel, 0, 0);
        sortAlgGrid.add(selection, 0, 1);
        sortAlgGrid.add(bubble, 0, 2);
        sortAlgGrid.add(insertion, 0, 3);
        sortAlgGrid.add(quick, 0, 4);
        inputTypeGrid.add(inputTypeLabel, 0, 0);
        inputTypeGrid.add(alreadySort, 0, 1);
        inputTypeGrid.add(reverseOrd, 0, 2);
        inputTypeGrid.add(random, 0, 3);
        inputBlockGrid.add(inputSizeLabel, 0, 0);
        inputBlockGrid.add(inputSizeField, 1, 0);
        inputBlockGrid.add(blockSizeLabel, 0, 1);
        inputBlockGrid.add(blockSizeField, 1, 1);
        go.setPrefSize(280, 30);

        main.setPadding(new Insets(10, 10, 10, 10));
        sortAlgGrid.setPadding(new Insets(10, 10, 10, 10));
        inputTypeGrid.setPadding(new Insets(10, 10, 10, 10));
        inputBlockGrid.setPadding(new Insets(10, 10, 10, 10));
        sortingAlgLabel.setPadding(new Insets(0, 5, 5, 0));
        selection.setPadding(new Insets(0, 5, 5, 0));
        bubble.setPadding(new Insets(0, 5, 5, 0));
        insertion.setPadding(new Insets(0, 5, 5, 0));
        quick.setPadding(new Insets(0, 5, 0, 0));
        inputTypeLabel.setPadding(new Insets(0, 5, 5, 0));
        alreadySort.setPadding(new Insets(0, 5, 5, 0));
        reverseOrd.setPadding(new Insets(0, 5, 5, 0));
        random.setPadding(new Insets(0, 5, 0, 0));
        inputSizeLabel.setPadding(new Insets(0, 5, 5, 0));
        blockSizeLabel.setPadding(new Insets(0, 5, 5, 0));

        sortAlgGrid.setStyle("-fx-border-width: 1px; -fx-border-color: #000000;"
                + " -fx-border-radius: 6 6 6 6;");
        inputTypeGrid.setStyle("-fx-border-width: 1px; -fx-border-color: #000000;"
                + " -fx-border-radius: 6 6 6 6;");
        inputBlockGrid.setStyle("-fx-border-width: 1px; -fx-border-color: #000000;"
                + " -fx-border-radius: 6 6 6 6;");

        go.setOnAction(e -> {
            boolean inputPassedChecks = true;
            char sortFlag;
            boolean dataTypeSelected = false;

            // Error Handling
            if (inputSizeField.getText().trim().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Threaded Sorting Error");
                alert.setContentText("The Input Size Field is Empty!");
                alert.show();
                inputPassedChecks = false;

            } else if (blockSizeField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Threaded Sorting Error");
                alert.setContentText("The Block Size Field is Empty!");
                alert.show();
                inputPassedChecks = false;
            } else if (inputPassedChecks) {
                // added a flag so only 1 alert pops up per attempt. I don't want the user to be flooded by error messages
                boolean numberFlag = false;

                try {
                    int inputSizeConvert = Integer.parseInt(inputSizeField.getText());
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("Invalid Block Size. Please enter an integer!");
                    alert.show();
                    numberFlag = true;
                    inputPassedChecks = false;

                }

                if (numberFlag == false) {
                    try {
                        int blockSizeConvert = Integer.parseInt(blockSizeField.getText());
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Threaded Sorting Error");
                        alert.setContentText("Invalid Block Size. Please enter an integer!");
                        alert.show();
                        inputPassedChecks = false;

                    }
                }

            }

            if (inputPassedChecks) {
                if (Integer.parseInt(inputSizeField.getText()) < 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("The input size must be 0 or larger!");
                    alert.show();
                    inputPassedChecks = false;
                }
            }

            if (inputPassedChecks) {
                if (Integer.parseInt(blockSizeField.getText()) < 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("The block size must be 1 or larger!");
                    alert.show();
                    inputPassedChecks = false;
                }
            }

            // check if input types are selected
            if (selection.isSelected() && inputPassedChecks == true) {
                if (alreadySort.isSelected()) {
                    sortFlag = 's';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);
                    int[] sortedArray = new int[arraySizeInt];
                    loadAlreadySorted(sortedArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (reverseOrd.isSelected()) {
                    sortFlag = 's';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] reverseArray = new int[arraySizeInt];
                    loadReverseOrder(reverseArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (random.isSelected()) {
                    sortFlag = 's';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] randomArray = new int[arraySizeInt];
                    loadRandomOrder(randomArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (dataTypeSelected == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("No Input Type Selected!");
                    alert.show();
                }

            } else if (bubble.isSelected() && inputPassedChecks == true) {
                if (alreadySort.isSelected()) {
                    sortFlag = 'b';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] sortedArray = new int[arraySizeInt];
                    loadAlreadySorted(sortedArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (reverseOrd.isSelected()) {
                    sortFlag = 'b';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] reverseArray = new int[arraySizeInt];
                    loadReverseOrder(reverseArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (random.isSelected()) {
                    sortFlag = 'b';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] randomArray = new int[arraySizeInt];
                    loadRandomOrder(randomArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (dataTypeSelected == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("No Input Type Selected");
                    alert.show();
                }
            } else if (insertion.isSelected() && inputPassedChecks == true) {
                if (alreadySort.isSelected()) {
                    sortFlag = 'i';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] sortedArray = new int[arraySizeInt];
                    loadAlreadySorted(sortedArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (reverseOrd.isSelected()) {
                    sortFlag = 'i';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] reverseArray = new int[arraySizeInt];
                    loadReverseOrder(reverseArray, arraySizeInt, blockSizeField, sortFlag);
                   // dataTypeSelected = true;

                } else if (random.isSelected()) {
                    sortFlag = 'i';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] randomArray = new int[arraySizeInt];
                    loadRandomOrder(randomArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (dataTypeSelected == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("No Input Type Selected");
                    alert.show();
                }
            } else if (quick.isSelected() && inputPassedChecks == true) {
                if (alreadySort.isSelected()) {
                    sortFlag = 'q';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] sortedArray = new int[arraySizeInt];
                    loadAlreadySorted(sortedArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (reverseOrd.isSelected()) {
                    sortFlag = 'q';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] reverseArray = new int[arraySizeInt];
                    loadReverseOrder(reverseArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (random.isSelected()) {
                    sortFlag = 'q';
                    String arraySize = inputSizeField.getText();
                    int arraySizeInt = Integer.parseInt(arraySize);

                    int[] randomArray = new int[arraySizeInt];
                    loadRandomOrder(randomArray, arraySizeInt, blockSizeField, sortFlag);
                    //dataTypeSelected = true;

                } else if (dataTypeSelected == false) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Threaded Sorting Error");
                    alert.setContentText("No Input Type Selected");
                    alert.show();
                }
            } else if (inputPassedChecks == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Threaded Sorting Error");
                alert.setContentText("No Sorting Algorithm Selected!");
                alert.show();
            }

        });

        Scene scene = new Scene(main, 252, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void loadAlreadySorted(int[] arg, int size, TextField blockSizeField, char sFlag) {
        String blockS = blockSizeField.getText();
        int threadSize = Integer.parseInt(blockS);
        int numOfBlocks = size / threadSize;

        for (int i = 0; i < size; i++) {
            arg[i] = i + 1;
        }

        threadSpawner(arg, size, numOfBlocks, threadSize, sFlag);

    }

    public static void loadReverseOrder(int[] arg, int size, TextField blockSizeField, char sFlag) {
        String blockS = blockSizeField.getText();
        int threadSize = Integer.parseInt(blockS);
        int numOfBlocks = size / threadSize;
        for (int i = 0; i < size; i++) {
            arg[i] = size - i;
        }

        threadSpawner(arg, size, numOfBlocks, threadSize, sFlag);
    }

    public static void loadRandomOrder(int[] arg, int size, TextField blockSizeField, char sFlag) {
        String blockS = blockSizeField.getText();
        int threadSize = Integer.parseInt(blockS);
        int numOfBlocks = size / threadSize;

        for (int i = 0; i < size; i++) {
            arg[i] = (int) (0 + Math.random() * 99);
        }

        threadSpawner(arg, size, numOfBlocks, threadSize, sFlag);
    }

    // Creates n subarrays based block size
    public static void threadSpawner(int[] arg, int size, int numOfBlocks, int threadSize, char sFlag) {
        int count = 0;

        ArrayList<SortingAlg> threadList1 = new ArrayList(numOfBlocks);
        LinkedList<int[]> queue = new LinkedList<>();

        long startTime = System.currentTimeMillis();

        if (size != 1) {
            while (count < numOfBlocks) {
                int[] temp = new int[threadSize];

                for (int i = 0; i < threadSize; i++) {

                    temp[i] = arg[i + (threadSize * count)];
                }

                SortingAlg s1 = new SortingAlg(temp, sFlag);
                threadList1.add(s1);
                count++;

            }

            if (size % threadSize != 0) {

                int[] temp = new int[size % threadSize];
                for (int i = 0; i < (size % threadSize); i++) {
                    temp[i] = arg[arg.length - i - 1];
                }
                SortingAlg s1 = new SortingAlg(temp, sFlag);
                threadList1.add(s1);
            }

            for (int i = 0; i < threadList1.size(); i++) {
                threadList1.get(i).start();
            }

            for (int i = 0; i < threadList1.size(); i++) {
                try {
                    threadList1.get(i).join();
                } catch (InterruptedException ie) {
                    System.out.println("Thread join exception");
                }
            }
            if (size % threadSize != 0) {
                numOfBlocks += 1;
            }
            for (int i = 0; i < numOfBlocks; i++) {
                int[] temp = threadList1.get(i).getArr();
                queue.offer(temp);
            }
        }

        // If not divisible by blocksize then a new sub array stores the remainder and adds them to the threadlist
        if (size == 0) {
            System.out.println(Arrays.toString(arg));

        } else if (size == 1) {
            System.out.println(Arrays.toString(arg));
        }
        long endTime;
        // offer blocks to queue fix equation
        int[] finalMergedArray;
        if (size / threadSize != 0 && size != threadSize) {
            finalMergedArray = mergeThread(size, numOfBlocks, threadList1, threadSize, queue);
            endTime = System.currentTimeMillis();
            //Output a formatted version of the final merged array
            System.out.print("[");
            int commaCount = 0;
            for (int i = 0; i < finalMergedArray.length; i++) {
                if (i != finalMergedArray.length - 1) {
                    System.out.print(finalMergedArray[i] + ", ");
                } else if (i == finalMergedArray.length - 1) {
                    System.out.println(finalMergedArray[i] + "]");

                }

                commaCount++;
                if (commaCount == 19) {
                    commaCount = 0;
                    System.out.println("");
                }
            }
            System.out.println("");
        } else if (size == threadSize && size > 1) {
            endTime = System.currentTimeMillis();
            System.out.print("[");
            int commaCount = 0;
            for (int i = 0; i < queue.peek().length; i++) {
                if (i != queue.peek().length - 1) {
                    System.out.print(queue.peek()[i] + ", ");
                } else if (i == queue.peek().length - 1) {
                    System.out.println(queue.peek()[i] + "]");

                }

                commaCount++;
                if (commaCount == 19) {
                    commaCount = 0;
                    System.out.println("");
                }
            }
            System.out.println("");
        }else if(size < threadSize && size != 1 && size != 0){
            endTime = System.currentTimeMillis();
            System.out.print("[");
            int commaCount = 0;
            for (int i = 0; i < queue.peek().length; i++) {
                if (i != queue.peek().length - 1) {
                    System.out.print(queue.peek()[i] + ", ");
                } else if (i == queue.peek().length - 1) {
                    System.out.println(queue.peek()[i] + "]");

                }

                commaCount++;
                if (commaCount == 19) {
                    commaCount = 0;
                    System.out.println("");
                }
            }
            System.out.println("");
        }else{
            endTime = System.currentTimeMillis();
        }

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Finished");
        alert.setTitle("Threaded Sorting");
        alert.setContentText("Sort completed in " + (endTime - startTime) + " ms");
        alert.show();
    }

    // Recursive merge thread spawner
    public static int[] mergeThread(int size, int numOfBlocks, ArrayList<SortingAlg> threadList1, int threadSize, LinkedList<int[]> queue) {
        ArrayList<MergeThreads> mergeList = new ArrayList(numOfBlocks);
      

        int queueSize = queue.size() / 2;

        if (queue.size() == 1) {
            return queue.peek();
        }
        //
        for (int i = 0; i < queueSize; i++) {
            MergeThreads m1 = new MergeThreads(queue.poll(), queue.poll());
            mergeList.add(m1);
        }

        for (int i = 0; i < queueSize; i++) {
            mergeList.get(i).start();
        }
        for (int i = 0; i < queueSize; i++) {
            try {
                mergeList.get(i).join();
            } catch (InterruptedException ie) {
                System.out.println("Thread join exception");
            }
        }

        for (int i = 0; i < queueSize; i++) {
            queue.offer(mergeList.get(i).gettMerger());
        }

        return mergeThread(size, numOfBlocks, threadList1, threadSize, queue);

    }

}
