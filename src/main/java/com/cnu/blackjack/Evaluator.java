package com.cnu.blackjack;

import java.util.Map;

public class Evaluator {

    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
        dealer = new Dealer();
        dealCardToPlayers();
    }

    public void start() {
        int dealer_score = dealer.getDealerScore();
        playerMap.forEach((name, player) -> {
            ScoreCompare(dealer_score, name, player);
        });
    }

    private void ScoreCompare(int dealer_score, String name, Player player) {
        int player_score;

        int temp_score = player.getHand().CardSum();
        if (temp_score <= 16){
            player.hitCard();
            if (player.getHand().CardSum() > 21) {
                System.out.println(name + " 플레이어 패배 : 카드의 합이 21 초과");
            }
        }
        player_score = player.getHand().CardSum();
        if (player_score == 21){
            System.out.println(name + " 플레이어 승리 : 블랙잭");
        }
        else if (player_score > dealer_score) {
            player.setBalance(player.getBalance() + player.getCurrentBet()*2);
            System.out.println(name + " 플레이어 승리");
        }
        else if (player_score <= dealer_score) {
            System.out.println(name + " 플레이어 패배");
        }
        System.out.println(name + " 플레이어 턴종료");
    }

    private void dealCardToPlayers() {
        playerMap.forEach((name, player) -> {
            player.hitCard();
            player.hitCard();
        });
    }
}
