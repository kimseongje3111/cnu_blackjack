package com.cnu.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Deck deck;
    private List<Card> cardList = new ArrayList<Card>();

    public Hand(Deck deck) {
        this.deck = deck;
    }

    public Card drawCard() {
        Card card = deck.drawCard();
        cardList.add(card);
        return card;
    }

    public int CardSum(){
        int result = 0;
        for(int i = 0; i < cardList.size(); i++){
            if (cardList.get(i).getRank() > 10){
                result += 10;
            }
            else {
                result += cardList.get(i).getRank();
            }
        }
        return result;
    }

    public List<Card> getCardList() {
        return cardList;
    }
}
