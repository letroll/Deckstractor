package main.java.se.dma.deckstractor.services.implementations;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.CardRepository;
import main.java.se.dma.deckstractor.repository.persistence.XstreamCardRepository;
import main.java.se.dma.deckstractor.services.interfaces.CardService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by palle on 21/01/15.
 */
public class CardServiceImpl implements CardService {

    private CardRepository repo = new XstreamCardRepository();

    @Override
    public long saveCard(Card card) {
        return repo.saveCard(card);
    }

    @Override
    public Card getCard(long id) {
        return repo.getCard(id);
    }

    @Override
    public ArrayList<Card> getAllCards() {
        return repo.getAllCards();
    }

    @Override
    public ArrayList<Card> getAllCardsByClass(HearthstoneClass hearthstoneClass) {
        return repo.getAllCardsByClass(hearthstoneClass);
    }

    @Override
    public void removeCard(long id) {
        repo.removeCard(id);
    }

    @Override
    public void updateCard(Card card) {
        repo.updateCard(card);
    }

    @Override
    public void initializeCardDatabase() {
        repo.initializeCardDatabase();
    }
}
