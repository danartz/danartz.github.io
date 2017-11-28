/*
 * Card back from http://res.freestockphotos.biz/pictures/15/15686-illustration-of-a-play-card-back-pv.png
 * v1.2
 * 
 */
package project3;

import java.util.Collections;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author dan
 */
public class Project3 extends Application {

    /**
     * @param args the command line arguments
     */
    //boolean value = true;
    boolean aiThinking = false;
    int discardSizeBefore;
    boolean aiWin = false;
    boolean humanWin = false;

    //int count = 0;
    public static void main(String[] args) {
        // TODO code application logic here
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainBpane = new BorderPane();
        BorderPane gameUi = new BorderPane();
        HBox playerCards = new HBox(15);
        VBox aiUpdates = new VBox(15);
        HBox aiCards = new HBox(15);
        VBox cardPiles = new VBox(5);

        VBox holdHandInstruc = new VBox(15);
        Label draw = new Label("Draw Pile");
        Label discard = new Label("Discard Pile");
        Label discardInstruc = new Label("DRAW A CARD");
        Deck d1 = new Deck();
        AiPlayer a1 = new AiPlayer();
        d1.Shuffle();

        // Shuffle added a null that im removing
        for (int i = 0; i < d1.getDrawPile().size(); i++) {
            if (d1.getDrawPile().get(i).getValue() == null) {
                d1.getDrawPile().remove(i);
            }
        }

        
        for (int i = 0; i < 4; i++) {
            d1.getHumanHand().add(d1.getDrawPile().pollLast());
        }

        for (int i = 0; i < 4; i++) {
            a1.getAiHand().add(d1.getDrawPile().pollLast());
        }

        // Card pile Images
        ImageView imageViewDiscard = new ImageView();
        ImageView imageViewDraw = new ImageView("file:resources/cardBack.png");
        //Player Card Images
        ImageView imageView1 = new ImageView(d1.getHumanHand().get(0).getFileName());
        ImageView imageView2 = new ImageView(d1.getHumanHand().get(1).getFileName());
        ImageView imageView3 = new ImageView(d1.getHumanHand().get(2).getFileName());
        ImageView imageView4 = new ImageView(d1.getHumanHand().get(3).getFileName());
        ImageView imageView5 = new ImageView();
        //AI Card Images
        ImageView imageView6 = new ImageView("file:resources/cardBack.png");
        ImageView imageView7 = new ImageView("file:resources/cardBack.png");
        ImageView imageView8 = new ImageView("file:resources/cardBack.png");
        ImageView imageView9 = new ImageView("file:resources/cardBack.png");
        ImageView imageView10 = new ImageView();

        Button discardPile = new Button("discard");
        Button deckDraw = new Button("deckDraw");

        imageViewDiscard.setFitHeight(250);
        imageViewDiscard.setFitWidth(172);
        imageViewDraw.setFitHeight(250);
        imageViewDraw.setFitWidth(172);
        imageView1.setFitHeight(250);
        imageView1.setFitWidth(172);
        imageView2.setFitHeight(250);
        imageView2.setFitWidth(172);
        imageView3.setFitHeight(250);
        imageView3.setFitWidth(172);
        imageView4.setFitHeight(250);
        imageView4.setFitWidth(172);
        imageView5.setFitHeight(250);
        imageView5.setFitWidth(172);
        imageView6.setFitHeight(250);
        imageView6.setFitWidth(172);
        imageView7.setFitHeight(250);
        imageView7.setFitWidth(172);
        imageView8.setFitHeight(250);
        imageView8.setFitWidth(172);
        imageView9.setFitHeight(250);
        imageView9.setFitWidth(172);
        imageView10.setFitHeight(250);
        imageView10.setFitWidth(172);

        // Load gui
        playerCards.getChildren().add(imageView1);
        playerCards.getChildren().add(imageView2);
        playerCards.getChildren().add(imageView3);
        playerCards.getChildren().add(imageView4);
        playerCards.getChildren().add(imageView5);

        holdHandInstruc.getChildren().add(discardInstruc);
        holdHandInstruc.getChildren().add(playerCards);

        gameUi.setBottom(holdHandInstruc);
        gameUi.setLeft(aiUpdates);

        gameUi.setTop(aiCards);
        aiCards.getChildren().add(imageView6);
        aiCards.getChildren().add(imageView7);
        aiCards.getChildren().add(imageView8);
        aiCards.getChildren().add(imageView9);
        aiCards.getChildren().add(imageView10);
        cardPiles.getChildren().add(discard);
        cardPiles.getChildren().add(imageViewDiscard);
        cardPiles.getChildren().add(draw);
        cardPiles.getChildren().add(imageViewDraw);

        gameUi.setRight(cardPiles);
        
        // Styling
        aiCards.setAlignment(Pos.CENTER);
        playerCards.setAlignment(Pos.CENTER);
        mainBpane.setStyle("-fx-background-color: #00b300;");
        playerCards.setPadding(new Insets(-350, 100, 100, 15));
        aiUpdates.setPadding(new Insets(0, 0, 0, 50));

        discardInstruc.setPadding(new Insets(15, 15, 15, 15));
        discardInstruc.setAlignment(Pos.CENTER);
        holdHandInstruc.setAlignment(Pos.CENTER);
        aiCards.setPadding(new Insets(15, 100, 50, 15));
        cardPiles.setPadding(new Insets(-220, 30, 0, 0));
        draw.setPadding(new Insets(0, 0, 0, 45));
        discard.setPadding(new Insets(0, 0, 0, 39));
        discard.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 17");
        draw.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 17");
        discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
        aiUpdates.setStyle("-fx-font-size: 15;"
                + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");

    

        mainBpane.setCenter(gameUi);
        // Load scene
        Scene scene = new Scene(mainBpane, 1300, 800);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Draw from discard or draw pile
        imageViewDiscard.setOnMouseClicked(e -> {

            if (d1.getDiscardPile().size() > 0 && d1.getHumanHand().size() < 5 && aiThinking == false) {
                d1.getHumanHand().add(d1.getDiscardPile().pop());
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
                discardInstruc.setText("DISCARD A CARD");
                imageViewDiscard.setFitHeight(250);
                imageViewDiscard.setFitWidth(172);
            }
        });

        imageViewDraw.setOnMouseClicked(e -> {
            if (d1.getHumanHand().size() < 5 && aiThinking == false) {
                drawCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
                discardInstruc.setText("DISCARD A CARD");
                imageViewDraw.setFitHeight(250);
                imageViewDraw.setFitWidth(172);
            }

        });

        // Hover graphics for cards
        imageView1.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() > 4) {
                if (imageView1.isHover()) {
                    imageView1.setFitHeight(240);
                    imageView1.setFitWidth(165.12);
                } else {
                    imageView1.setFitHeight(250);
                    imageView1.setFitWidth(172);
                }
            }
        });

        imageView2.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() > 4) {
                if (imageView2.isHover()) {
                    imageView2.setFitHeight(240);
                    imageView2.setFitWidth(165.12);
                } else {
                    imageView2.setFitHeight(250);
                    imageView2.setFitWidth(172);
                }
            }
        });
        imageView3.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() > 4) {
                if (imageView3.isHover()) {
                    imageView3.setFitHeight(240);
                    imageView3.setFitWidth(165.12);
                } else {
                    imageView3.setFitHeight(250);
                    imageView3.setFitWidth(172);
                }
            }
        });
        imageView4.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() > 4) {
                if (imageView4.isHover()) {
                    imageView4.setFitHeight(240);
                    imageView4.setFitWidth(165.12);
                } else {
                    imageView4.setFitHeight(250);
                    imageView4.setFitWidth(172);
                }
            }
        });
        imageView5.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() > 4) {
                if (imageView5.isHover()) {
                    imageView5.setFitHeight(240);
                    imageView5.setFitWidth(165.12);
                } else {
                    imageView5.setFitHeight(250);
                    imageView5.setFitWidth(172);
                }
            }
        });
        imageViewDiscard.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() < 5 && aiThinking == false) {
                if (imageViewDiscard.isHover()) {
                    imageViewDiscard.setFitHeight(240);
                    imageViewDiscard.setFitWidth(165.12);
                } else {
                    imageViewDiscard.setFitHeight(250);
                    imageViewDiscard.setFitWidth(172);
                }
            }
        });

        imageViewDraw.hoverProperty().addListener(cl -> {
            if (d1.getHumanHand().size() < 5 && aiThinking == false) {
                if (imageViewDraw.isHover()) {
                    imageViewDraw.setFitHeight(240);
                    imageViewDraw.setFitWidth(165.12);
                } else {
                    imageViewDraw.setFitHeight(250);
                    imageViewDraw.setFitWidth(172);
                }
            }
        });

        // Discard cards
        // Image view 1 has relevant comments for imageview 1 through 5. I didn't repeat comments in each imageView
        imageView1.setOnMouseClicked(e -> {
            if (d1.getHumanHand().size() > 4) {

                d1.getDiscardPile().push(d1.getHumanHand().get(0));
                d1.getHumanHand().remove(0);
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);

                // Check if player won
                if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                        && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
                    boolean cardEmpty = true;
                    // If player wins show Ai's hand at the end
                    a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONGRATULATIONS!!");
                    alert.setContentText("YOU WIN!!!");

                    if (alert.showAndWait().isPresent()) {
                        primaryStage.close();
                    } else if (alert.showAndWait().get() == ButtonType.OK) {
                        primaryStage.close();
                    } else {
                        primaryStage.close();
                    }

                }
                aiThinking = true; // flag to lock the game while AI is making its move since it runs on a timeline
                discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #990000; -fx-font-size: 15;"
                        + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #990000; -fx-font-weight: bold");
                discardInstruc.setText("AI IS DRAWING A CARD");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {
                            aiThinking = true;
                            boolean cardEmpty = false;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                            aiWin = a1.aiMove(d1, imageView6, imageView7, imageView8, imageView9, imageView10, imageViewDiscard);

                        })
                );
                boolean timerFlag = false;
                timer.play();
                
                // animation timelines
                Timeline timer2 = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {

                            boolean cardEmpty = true;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
              

                        })
                );
                
                // Chained timelines together so they go sequentially
                timer.setOnFinished(eh -> {
                    // If AI wins update card graphics to show the AI's winning hand
                    if(aiWin == true){
                        boolean cardEmpty = true;
                        a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    }
                   
                    timer2.play();
                    discardInstruc.setText("AI IS DISCARDING A CARD");
                });
                timer2.setOnFinished(eeh -> {
                    aiThinking = false;

                    discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                            + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
                    discardInstruc.setText("DRAW A CARD");
                    // If AI Wins can't use show and wait for alerts during an animation so I had to show the alert and then thread sleep for 1.7 seconds before closing the stage
                    if (aiWin == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("SORRY!");
                        alert.setContentText("YOU LOSE !!!");
                        alert.show();

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException em) {

                        }

                        primaryStage.close();
                    }
                });

                imageView1.setFitHeight(250);
                imageView1.setFitWidth(172);

            }

        });
        imageView2.setOnMouseClicked(e -> {
            if (d1.getHumanHand().size() > 4) {

                d1.getDiscardPile().push(d1.getHumanHand().get(1));
                d1.getHumanHand().remove(1);
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
              
                if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                        && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
                    boolean cardEmpty = true;
                    a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONGRATULATIONS!!");
                    alert.setContentText("YOU WIN!!!");

                    if (alert.showAndWait().isPresent()) {
                        primaryStage.close();
                    } else if (alert.showAndWait().get() == ButtonType.OK) {
                        primaryStage.close();
                    } else {
                        primaryStage.close();
                    }
                }
                aiThinking = true;
                discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #990000; -fx-font-size: 15;"
                        + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #990000; -fx-font-weight: bold");
                discardInstruc.setText("AI IS DRAWING A CARD");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {
                            aiThinking = true;
                            boolean cardEmpty = false;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                            aiWin = a1.aiMove(d1, imageView6, imageView7, imageView8, imageView9, imageView10, imageViewDiscard);

                        })
                );
                boolean timerFlag = false;
                timer.play();
                Timeline timer2 = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {

                            boolean cardEmpty = true;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
              

                        })
                );
                timer.setOnFinished(eh -> {

                   if(aiWin == true){
                        boolean cardEmpty = true;
                        a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    }
                    timer2.play();
                    discardInstruc.setText("AI IS DISCARDING A CARD");
                });
                timer2.setOnFinished(eeh -> {
                    aiThinking = false;

                    discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                            + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
                    discardInstruc.setText("DRAW A CARD");
                    if (aiWin == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("SORRY!");
                        alert.setContentText("YOU LOSE !!!");
                        alert.show();

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException em) {

                        }

                        primaryStage.close();
                    }
                });

                imageView2.setFitHeight(250);
                imageView2.setFitWidth(172);
            }

        });
        imageView3.setOnMouseClicked(e -> {
            if (d1.getHumanHand().size() > 4) {

                d1.getDiscardPile().push(d1.getHumanHand().get(2));
                d1.getHumanHand().remove(2);
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
              
                if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                        && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
                    boolean cardEmpty = true;
                    a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONGRATULATIONS!!");
                    alert.setContentText("YOU WIN!!!");

                    if (alert.showAndWait().isPresent()) {
                        primaryStage.close();
                    } else if (alert.showAndWait().get() == ButtonType.OK) {
                        primaryStage.close();
                    }
                }
                aiThinking = true;
                discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #990000; -fx-font-size: 15;"
                        + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #990000; -fx-font-weight: bold");
                discardInstruc.setText("AI IS DRAWING A CARD");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {
                            aiThinking = true;
                            boolean cardEmpty = false;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                            aiWin = a1.aiMove(d1, imageView6, imageView7, imageView8, imageView9, imageView10, imageViewDiscard);

                        })
                );
                boolean timerFlag = false;
                timer.play();
                Timeline timer2 = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {

                            boolean cardEmpty = true;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
              

                        })
                );
                timer.setOnFinished(eh -> {

                   if(aiWin == true){
                        boolean cardEmpty = true;
                        a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    }
                    timer2.play();
                    discardInstruc.setText("AI IS DISCARDING A CARD");
                });
                timer2.setOnFinished(eeh -> {
                    aiThinking = false;

                    discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                            + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
                    discardInstruc.setText("DRAW A CARD");
                    if (aiWin == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("SORRY!");
                        alert.setContentText("YOU LOSE !!!");
                        alert.show();

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException em) {

                        }

                        primaryStage.close();
                    }
                });

                imageView3.setFitHeight(250);
                imageView3.setFitWidth(172);
            }

        });

        imageView4.setOnMouseClicked(e -> {
            if (d1.getHumanHand().size() > 4) {

                d1.getDiscardPile().push(d1.getHumanHand().get(3));
                d1.getHumanHand().remove(3);
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
             
                if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                        && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
                    boolean cardEmpty = true;
                    a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONGRATULATIONS!!");
                    alert.setContentText("YOU WIN!!!");

                    if (alert.showAndWait().isPresent()) {
                        primaryStage.close();
                    } else if (alert.showAndWait().get() == ButtonType.OK) {
                        primaryStage.close();
                    }
                }
                aiThinking = true;
                discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #990000; -fx-font-size: 15;"
                        + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #990000; -fx-font-weight: bold");
                discardInstruc.setText("AI IS DRAWING A CARD");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {
                            aiThinking = true;
                            boolean cardEmpty = false;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                            aiWin = a1.aiMove(d1, imageView6, imageView7, imageView8, imageView9, imageView10, imageViewDiscard);

                        })
                );
                boolean timerFlag = false;
                timer.play();
                Timeline timer2 = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {

                            boolean cardEmpty = true;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
              

                        })
                );
                timer.setOnFinished(eh -> {

                   if(aiWin == true){
                        boolean cardEmpty = true;
                        a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    }
                    timer2.play();
                    discardInstruc.setText("AI IS DISCARDING A CARD");
                });
                timer2.setOnFinished(eeh -> {
                    aiThinking = false;

                    discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                            + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
                    discardInstruc.setText("DRAW A CARD");
                    if (aiWin == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("SORRY!");
                        alert.setContentText("YOU LOSE !!!");
                        alert.show();

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException em) {

                        }

                        primaryStage.close();
                    }
                });

                imageView4.setFitHeight(250);
                imageView4.setFitWidth(172);
            }

        });
        imageView5.setOnMouseClicked(e -> {

            if (d1.getHumanHand().size() > 4) {

                d1.getDiscardPile().push(d1.getHumanHand().get(4));
                d1.getHumanHand().remove(4);
                updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
             
                if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                        && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue()))) {
                    boolean cardEmpty = true;
                    a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONGRATULATIONS!!");
                    alert.setContentText("YOU WIN!!!");

                    if (alert.showAndWait().isPresent()) {
                        primaryStage.close();
                    } else if (alert.showAndWait().get() == ButtonType.OK) {
                        primaryStage.close();
                    }
                }
                aiThinking = true;
                discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #990000; -fx-font-size: 15;"
                        + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #990000; -fx-font-weight: bold");
                discardInstruc.setText("AI IS DRAWING A CARD");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {
                            aiThinking = true;
                            boolean cardEmpty = false;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                            aiWin = a1.aiMove(d1, imageView6, imageView7, imageView8, imageView9, imageView10, imageViewDiscard);

                        })
                );
                boolean timerFlag = false;
                timer.play();
                Timeline timer2 = new Timeline(
                        new KeyFrame(Duration.seconds(1.3), event -> {

                            boolean cardEmpty = true;
                            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
              

                        })
                );
                timer.setOnFinished(eh -> {

                   if(aiWin == true){
                        boolean cardEmpty = true;
                        a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
                    }
                    timer2.play();
                    discardInstruc.setText("AI IS DISCARDING A CARD");
                });
                timer2.setOnFinished(eeh -> {
                    aiThinking = false;

                    discardInstruc.setStyle("-fx-border-width: 10; -fx-border-color: #ffffff; -fx-font-size: 15;"
                            + " -fx-border-radius: 10 10 10 10; -fx-text-fill: #ffffff; -fx-font-weight: bold");
                    discardInstruc.setText("DRAW A CARD");
                    if (aiWin == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("SORRY!");
                        alert.setContentText("YOU LOSE !!!");
                        alert.show();

                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException em) {

                        }

                        primaryStage.close();
                    }
                });

                imageView5.setFitHeight(250);
                imageView5.setFitWidth(172);
            }

        });

        // Check if starting hand is a winning hand
        if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue())) && d1.getHumanHand().size() == 4) {
            boolean cardEmpty = true;
            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("CONGRATULATIONS!!");
            alert.setContentText("YOU WIN!!!");

            if (alert.showAndWait().isPresent()) {
                primaryStage.close();
            } else if (alert.showAndWait().get() == ButtonType.OK) {
                primaryStage.close();
            } else {
                primaryStage.close();
            }
        } else if ((d1.getHumanHand().get(0).getValue().equals(d1.getHumanHand().get(1).getValue())) && (d1.getHumanHand().get(1).getValue().equals(d1.getHumanHand().get(2).getValue()))
                && (d1.getHumanHand().get(2).getValue().equals(d1.getHumanHand().get(3).getValue())) && d1.getHumanHand().size() < 4) {
            System.out.println("You won but the stage closed before your hand fully loaded.");
        }

        if ((a1.getAiHand().get(0).getValue().equals(a1.getAiHand().get(1).getValue())) && (a1.getAiHand().get(1).getValue().equals(a1.getAiHand().get(2).getValue()))
                && (a1.getAiHand().get(2).getValue().equals(a1.getAiHand().get(3).getValue())) && a1.getAiHand().size() == 4) {
            boolean cardEmpty = true;
            a1.updateAiCard(d1, imageView6, imageView7, imageView8, imageView9, imageView10, cardEmpty, imageViewDiscard);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sorry!");
            alert.setContentText("YOU LOSE!!!");

            if (alert.showAndWait().isPresent()) {
                primaryStage.close();
            } else if (alert.showAndWait().get() == ButtonType.OK) {
                primaryStage.close();
            } else {
                primaryStage.close();
            }
        } else if ((a1.getAiHand().get(0).getValue().equals(a1.getAiHand().get(1).getValue())) && (a1.getAiHand().get(1).getValue().equals(a1.getAiHand().get(2).getValue()))
                && (a1.getAiHand().get(2).getValue().equals(a1.getAiHand().get(3).getValue())) && a1.getAiHand().size() < 4) {
            System.out.println("You lost but the stage closed before the Ai's hand fully loaded");
        }
    }

    // Updates graphics on cards depending on the situation
    public void updateCard(Deck d1, ImageView imageView1, ImageView imageView2,
            ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageViewDiscard) {

        String newCard1 = d1.getHumanHand().get(0).getFileName();
        imageView1.setStyle("-fx-image: url(\"" + newCard1 + "\");");
        String newCard2 = d1.getHumanHand().get(1).getFileName();
        imageView2.setStyle("-fx-image: url(\"" + newCard2 + "\");");
        String newCard3 = d1.getHumanHand().get(2).getFileName();
        imageView3.setStyle("-fx-image: url(\"" + newCard3 + "\");");
        String newCard4 = d1.getHumanHand().get(3).getFileName();
        imageView4.setStyle("-fx-image: url(\"" + newCard4 + "\");");

        if (d1.getHumanHand().size() == 5) {
            String newCard5 = d1.getHumanHand().get(4).getFileName();
            imageView5.setStyle("-fx-image: url(\"" + newCard5 + "\");");
        } else {
            imageView5.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
        }

        if (d1.getDiscardPile().size() > 0) {
            String newCard5 = d1.getDiscardPile().peek().getFileName();
            imageViewDiscard.setStyle("-fx-image: url(\"" + newCard5 + "\");");
        } else {
            imageViewDiscard.setStyle("-fx-image: url(\"" + "file:resources/empty.png" + "\");");
        }

    }

    // Draws card from draw pile and sorts discard pile and shuffles then moves to draw pile if discard pile is empty
    public void drawCard(Deck d1, ImageView imageView1, ImageView imageView2,
            ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageViewDiscard) {

        if (d1.getDrawPile().size() > 0 && d1.getHumanHand().size() < 5) {
            d1.getHumanHand().add(d1.getDrawPile().pollLast());
            updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);
        }
        if (d1.getDrawPile().size() == 0) {

            for (int i = 0; i < 43; i++) {
                d1.getDrawPile().push(d1.getDiscardPile().pop());
                if (d1.getDiscardPile().size() == 0) {
                    break;
                }
            }

            Collections.shuffle(d1.getDrawPile());
            for (int i = 0; i < d1.getDrawPile().size(); i++) {
                if (d1.getDrawPile().get(i).getValue() == null) {
                    d1.getDrawPile().remove(i);
                }
            }
        
            updateCard(d1, imageView1, imageView2, imageView3, imageView4, imageView5, imageViewDiscard);

        }

    }

}
