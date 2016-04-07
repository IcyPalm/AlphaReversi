package alphareversi;

import alphareversi.lobby.LobbyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by wouter on 16-3-2016.
 */

public class Main extends Application {
    static Scene baseScene;
    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("lobby/lobby.fxml"));
        Parent root = loader.load();

        this.primaryStage = primaryStage;
        baseScene = new Scene(root);
        primaryStage.setScene(baseScene);

        LobbyController controller =
                loader.<LobbyController>getController();
        controller.setMainApp(this);
        primaryStage.show();


    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
//=======
//import alphareversi.model.TicTacToeModel;
//import alphareversi.views.ticTacToeView.TicTacToeViewController;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//public class Main extends Application{
//	
//	private Stage primaryStage;
//    private BorderPane rootLayout;
//    private TicTacToeModel ticTacToeModel;
//    
//    public Main(){
//    	ticTacToeModel = new TicTacToeModel("Opponent");
//    }
//    
//    public static void main(String[] args) {
//        // AlphaReversi
//    	launch();
//    }
//    
//    @Override
//	public void start(Stage primaryStage){
//        try{
//        	this.primaryStage = primaryStage;
//            this.primaryStage.setTitle("Tic Tac Toe");
//			initRootLayout();
//	        showTicTacToeView();
//        }
//        catch(Exception e){
//        	e.printStackTrace();
//        }
//	}
//	
//	private void initRootLayout() throws Exception{
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("views/ticTacToeView/rootLayout.fxml"));
//        rootLayout = (BorderPane) loader.load();
//
//        Scene scene = new Scene(rootLayout);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//	
//	private void showTicTacToeView() throws Exception{
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("views/ticTacToeView/ticTacToeView.fxml"));
//        BorderPane ticTacToeView = (BorderPane) loader.load();
//        
//        rootLayout.setCenter(ticTacToeView);
//        
//        TicTacToeViewController controller = loader.getController();
//        ticTacToeModel.setViewController(controller);
//        controller.setTicTacToeModel(ticTacToeModel);
//        
//>>>>>>> tictactoe view
    }
}