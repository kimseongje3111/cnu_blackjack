package com.cnu.blackjack;

import com.cnu.blackjack.exceptions.NotYNException;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        while(true){
            System.out.println("게임을 시작합니다.");
            Scanner scan = new Scanner(System.in);
            System.out.println( "게임을 하려는 플레이어의 수를 입력하세요");
            int numplayers = scan.nextInt();
            Deck decks = new Deck(1);
            Game game = new Game(decks);

            for(int i=0; i<numplayers; i++) {//플레이어들의 정보 초기화
                System.out.println((i + 1) + "번째 플레이어의 이름과 초기 금액을 입력하세요");
                String playername = scan.next();
                int seedmoneys = scan.nextInt();
                game.addPlayer(playername, seedmoneys);
            }

            game.getPlayerList().forEach((name, player) -> {
                System.out.println(name + "(이)가 배팅할 금액을 입력하세요");
                int betmoneys = scan.nextInt();
                game.placeBet(name, betmoneys);
                System.out.println( betmoneys +"원을 배팅했습니다.");
            });
            game.start();
            Evaluator evaluator = new Evaluator(game.getPlayerList());
            evaluator.start();
            System.out.println("게임을 다시 시작하시겠습니까? Y/N");
            char yesno = scan.next().charAt(0);
            if(yesno == 'N' || yesno == 'n'){
                break;
            }
            else if(yesno != 'Y' || yesno != 'y'){
                throw new NotYNException();
            }
        }
    }
}
