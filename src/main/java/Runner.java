import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Runner {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        IScorer scorer = new PlayerScorerByRankOrder();
        Game game = new Game(deck, scorer);
        System.out.println("Welcome to BlackJack!");
//        System.out.println("How many players would you like to play?");

//        String input = scanner.next();
//        int players = parseInt(input);

//        for(int i = 0; i < players; i++){
//            String prompt = String.format("Player %s, enter your name: ", (i + 1));
//            System.out.println(prompt);
//            String playerName = scanner.next();
//            Player player = new Player(playerName);
//            game.addPlayer(player);
//        }

        String prompt = String.format("Enter your name: ");
        System.out.println(prompt);
        String playerName = scanner.next();
        Player player1 = new Player(playerName);
        game.addPlayer(player1);

        Player dealer = new Player("Del");
        game.addPlayer(dealer);
//        System.out.println("How many cards are we playing with?");
//        int noOfCards = parseInt(scanner.next());

        game.start(2);
//
//        System.out.println("Dealer has:");
//        System.out.println(dealer.showCard(0));

        String output = String.format("%s has:", dealer.getName());
        System.out.println(output);
        for(int i = 0; i < dealer.cardCount(); i ++){
            System.out.println(dealer.showCard(i));
        }
        System.out.println(String.format("Hand total: %s", scorer.getScore(dealer)));

//        for(Player player: game.getPlayers()){
            String output2 = String.format("%s has:", player1.getName());
            System.out.println(output2);
            for(int i = 0; i < player1.cardCount(); i ++){
                System.out.println(player1.showCard(i));
            }
            System.out.println(String.format("Hand total: %s", scorer.getScore(player1)));
//        }

        if(game.checkDraw()){
            System.out.println("It's a draw!");
        } else {
            Player winner = game.checkWinner();
            String winnerName = winner.getName();
            String output3 = String.format("%s wins!", winnerName);
            System.out.println(output3);
        }

    }
}
