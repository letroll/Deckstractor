package main.java.se.dma.deckstractor.services.interfaces;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public interface CardService {
    long saveCard(Card card);

    Card getCard(long id);

    ArrayList<Card> getAllCards();

    ArrayList<Card> getAllCardsByClass(HearthstoneClass hearthstoneClass);

    void removeCard(long id);

    void updateCard(Card card);

    void initializeCardDatabase();
}
