import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Runner {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        PlayerScorerByRankOrder scorer = new PlayerScorerByRankOrder();
        Game game = new Game(deck, scorer);
        System.out.println("Welcome to BlackJack!");
        System.out.println("How many players would you like to play?");

        String input = scanner.next();
        int players = parseInt(input);

        for(int i = 0; i < players; i++){
            String prompt = String.format("Player %s, enter your name: ", (i + 1));
            System.out.println(prompt);
            String playerName = scanner.next();
            Player player = new Player(playerName);
            game.addPlayer(player);
        }

        game.start(2);
        Player dealer = game.getDealer();

        String output = String.format("%s has:", dealer.getName());
        System.out.println(output);
        System.out.println(dealer.showCard(0));
        System.out.println();

        String choice = "";
        int score = 0;
        for (Player player : game.getPlayers()) {
            do {
                String output2 = String.format("%s has:", player.getName());
                System.out.println(output2);
                for (int i = 0; i < player.cardCount(); i++) {
                    System.out.println(player.showCard(i));
                }
                if (game.isBlackJack(player)) {
                    System.out.println("BlackJack!!!");
                    System.out.println();
                } else {
                    score = scorer.getScore(player);
                    System.out.println(String.format("Hand total: %s", score));
                    if (scorer.isBust(score)) {
                        String bustMessage = String.format("%s is Bust!", player.getName());
                        System.out.println(bustMessage);
                        System.out.println();
                    } else {
                        String choiceRequest = String.format("%s - Stand or Twist?", player.getName());
                        System.out.println(choiceRequest);
                        choice = scanner.next();
                        while (!choice.equals("Twist") && !choice.equals("Stand")) {
                            System.out.println("Please type 'Stand' or 'Twist'?");
                            choice = scanner.next();
                        }
                        if (choice.equals("Twist")) {
                            Card card = deck.dealOne();
                            player.takeCard(card);
                        }
                    }
                }
            } while (!scorer.isBust(score) && !choice.equals("Stand") && !game.isBlackJack(player));
        }

        String dealerChoice = "";
        int dealerScore = 0;
        do {
            String output2 = String.format("%s has:", dealer.getName());
            System.out.println(output2);
            for (int i = 0; i < dealer.cardCount(); i++) {
                System.out.println(dealer.showCard(i));
            }
            if (game.isBlackJack(dealer)) {
                System.out.println("BlackJack!!!");
                System.out.println();
            } else {
                dealerScore = scorer.getScore(dealer);
                System.out.println(String.format("Hand total: %s", dealerScore));
                Thread.sleep(2000);
                if (scorer.isBust(dealerScore)) {
                    System.out.println("Dealer is Bust!");
                    System.out.println();
                } else if (dealerScore < 16) {
                    dealerChoice = "Twist";
                    System.out.println("Dealer Twists");
                    Thread.sleep(2000);
                    Card card = deck.dealOne();
                    dealer.takeCard(card);
                } else {
                    dealerChoice = "Stand";
                    System.out.println("Dealer Stands");
                    Thread.sleep(1000);
                }
            }
        } while (!scorer.isBust(dealerScore) && !dealerChoice.equals("Stand") && !game.isBlackJack(dealer));


        for (Player player : game.getPlayers()) {
            String resultStatement = String.format("In %s's game: ", player.getName());
            System.out.println(resultStatement);
            Thread.sleep(2000);
            if (game.checkDraw(player)) {
                System.out.println("It's a draw - the Dealer wins!");
            } else {
                Player winner = game.checkWinner(player);
                String winnerName = winner.getName();
                String output3 = String.format("%s wins!", winnerName);
                System.out.println(output3);
            }
            System.out.println();
        }

    }
}
