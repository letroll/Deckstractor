package main.java.se.dma.deckstractor.repository.mock;

import main.java.se.dma.deckstractor.domain.Card;
import main.java.se.dma.deckstractor.domain.HearthstoneClass;
import main.java.se.dma.deckstractor.repository.interfaces.ClassRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by palle on 21/01/15.
 */
public class MockClassRepository implements ClassRepository {

    private final Map<Long, HearthstoneClass> repo = new HashMap<>();

    @Override
    public long saveHearthstoneClass(HearthstoneClass hearthstoneClass) {
        repo.put(hearthstoneClass.getId(), hearthstoneClass);
        return hearthstoneClass.getId();
    }

    @Override
    public HearthstoneClass getHearthstoneClass(long id) {
        return repo.get(id);
    }

    @Override
    public HearthstoneClass getHearthstoneClassByName(String name) {
        for (int i = 0; i < repo.size(); i++) {
            if (repo.get((long) i).getName().equalsIgnoreCase(name)) {
                return repo.get((long) i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<HearthstoneClass> getAllHearthstoneClasses() {
        return new ArrayList<HearthstoneClass>(repo.values());
    }

    @Override
    public void removeHearthstoneClass(long id) {
        repo.remove(id);
    }

    @Override
    public void updateHearthstoneClass(HearthstoneClass hearthstoneClass) {
        repo.put(hearthstoneClass.getId(), hearthstoneClass);
    }

    @Override
    public void initializeClassDatabase() {
        //TODO:Implement
    }
}
