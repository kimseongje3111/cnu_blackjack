package com.cnu.blackjack;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class EvaluatorTest {

    @Test
    public void 게임초기화시_모든플레이어는_2장의카드를_받는다() {
        Game game = new Game(new Deck(2));
        game.addPlayer("conch", 5000);
        game.addPlayer("ian", 10000);
        Map<String, Player> playerMap = game.getPlayerList();
        Evaluator evaluator = new Evaluator(playerMap);//생성자에 카드를 두장씩 받는 함수 포함
        //evaluator.start();
        playerMap.forEach((name, player) -> {
            assertTrue(player.getHand().getCardList().size() == 2);
        });
    }

    @Test
    public void 각_플레이어는_16이하면_히트한다() {
        Game game = new Game(new Deck(2));
        game.addPlayer("conch", 5000);
        game.addPlayer("ian", 10000);

        Map<String, Player> playerMap = game.getPlayerList();

        Evaluator evaluator = new Evaluator(playerMap);
        evaluator.setPlayerMap(null);
        evaluator.setPlayerMap(playerMap);

        Card card1 = new Card(10, Suit.SPADES);
        Card card2 = new Card(6, Suit.SPADES);
        playerMap.forEach((s, player) -> {
            player.getHand().getCardList().add(card1);
            player.getHand().getCardList().add(card2);
        });

        playerMap.forEach((name, player) -> {
            if(player.getHand().CardSum() <= 16){
                int before = player.getHand().getCardList().size();
                player.hitCard();
                assertTrue(before == player.getHand().getCardList().size());
            }
        });
    }


    @Test
    public void 블랙잭이나오면_2배로_보상받고_해당_플레이어의_턴은_끝난다() {
        Game game = new Game(new Deck(2));
        game.addPlayer("conch", 10000);

        Card card1 = new Card(10, Suit.SPADES);
        Card card2 = new Card(5, Suit.SPADES);
        Card card3 = new Card(6, Suit.SPADES);

        Map<String, Player> playerMap = game.getPlayerList();

        playerMap.forEach((s, player) -> {
            player.placeBet(5000);
            player.getHand().getCardList().add(card1);
            player.getHand().getCardList().add(card2);
            player.getHand().getCardList().add(card3);
        });

        Evaluator evaluator = new Evaluator(playerMap);

        playerMap.forEach((s, player) -> {
            evaluator.ScoreCompare(18,s, player);
            assertTrue(player.getBalance() == 15000);

        });

    }

    @Test
    public void 각_플레이어는_17이상이면_스테이한다() {
        Game game = new Game(new Deck(2));
        game.addPlayer("conch", 10000);
        game.addPlayer("ian", 10000);

        Map<String, Player> playerMap = game.getPlayerList();

        Evaluator evaluator = new Evaluator(playerMap);
        evaluator.setPlayerMap(null);
        evaluator.setPlayerMap(playerMap);

        Card card1 = new Card(10, Suit.SPADES);
        Card card2 = new Card(7, Suit.SPADES);
        playerMap.forEach((s, player) -> {
            player.getHand().getCardList().add(card1);
            player.getHand().getCardList().add(card2);
        });

        playerMap.forEach((s, player) -> {
            if(player.getHand().CardSum() >= 17){
                int before = player.getHand().CardSum();
                evaluator.ScoreCompare(18, s, player);
                assertTrue(before == player.getHand().CardSum());
            }
        });
    }
}
