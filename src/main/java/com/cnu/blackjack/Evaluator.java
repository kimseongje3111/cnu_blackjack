package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.PlayerDoesNotExistException;
import lombok.Data;

import java.util.Map;

@Data
public class Evaluator {

    private Map<String, Player> playerMap;
    private Dealer dealer;

    public Evaluator() {
        dealer = new Dealer();
    }

    public Evaluator(Map<String, Player> playerMap) {
        this.playerMap = playerMap;
        dealer = new Dealer();
        dealCardToPlayers();
    }

    public void start() {
        int dealerScore = dealer.getDealerScore();
        playerMap.forEach((name, player) -> {
            if (name == null || player == null){
                throw new PlayerDoesNotExistException();
            }
            ScoreCompare(dealerScore, name, player);
        });
    }

    public void ScoreCompare(int dealerScore, String name, Player player) {
        int player_score;
        if (dealerScore > 21){
            playerWin(player);
            System.out.println(name + " 플레이어 승리! : 딜러의 카드 합이 21 초과");
        }
        else{
            int temp_score = player.getHand().CardSum();
            if (temp_score <= 16){
                player.hitCard();
                if (player.getHand().CardSum() > 21) {
                    System.out.println(name + " 플레이어 패배! : 카드의 합이 21 초과");
                }
            }
            player_score = player.getHand().CardSum();
            if (player_score == 21){
                playerWin(player);
                System.out.println(name + " 플레이어 승리! : 블랙잭");
            }
            else if (player_score > dealerScore) {
                playerWin(player);
                System.out.println(name + " 플레이어 승리! 점수 : " + player_score);
            }
            else if (player_score <= dealerScore) {
                System.out.println(name + " 플레이어 패배! 점수 : " + player_score);
            }
            System.out.println(name + " 플레이어 턴종료");
        }
    }

    private void playerWin(Player player) {
        player.setBalance(player.getBalance() + player.getCurrentBet()*2);
    }

    private void dealCardToPlayers() {
        playerMap.forEach((name, player) -> {
            player.hitCard();
            player.hitCard();
        });
    }
}
