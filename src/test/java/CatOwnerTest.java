/*
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.breeds.Breed;
import ru.itmo.cats.Cat;
import ru.itmo.cats.CatRepositoryImpl;
import ru.itmo.cats.CatServiceImpl;
import ru.itmo.colors.Color;
import ru.itmo.owners.Owner;
import ru.itmo.owners.OwnerRepositoryImpl;
import ru.itmo.owners.OwnerServiceImpl;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CatOwnerTest {
    private CatRepositoryImpl catRepository;
    private OwnerRepositoryImpl ownerRepository;

    private CatServiceImpl catService;
    private OwnerServiceImpl ownerService;

    @BeforeEach
    public void setup() {
        catRepository = Mockito.mock(CatRepositoryImpl.class);
        ownerRepository = Mockito.mock(OwnerRepositoryImpl.class);

        catService = new CatServiceImpl(catRepository, ownerRepository);
        ownerService = new OwnerServiceImpl(catRepository, ownerRepository);

    }

    @Test
    public void createOwnerCreateCat_addCatToOwner_GetAllCats() {

        Mockito.doNothing().when(catRepository).addNewCat(Mockito.any(Cat.class));
        Mockito.doNothing().when(ownerRepository).addNewOwner(Mockito.any(Owner.class));
        Mockito.when(ownerRepository.getOwnerById(Mockito.anyInt())).thenReturn(new Owner("test owner", Date.valueOf("2006-02-01")));
        Mockito.when(ownerRepository.getAllCatsId(Mockito.anyInt())).thenReturn(Collections.singletonList(0));

        ownerService.addNewOwner("test owner", "2006-02-01");
        catService.addNewCat(1, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK");

        List cats = ownerService.getAllCatsId(1);
        assertEquals(1, cats.size());
    }
    @Test
    public void createCat_addFriendToCat_GetListFriends(){
        Owner owner = new Owner("test owner", Date.valueOf("2006-02-01"));
        Cat friend = new Cat("test friend", Date.valueOf("2006-02-01"), Breed.BRITMAN, Color.BLUE, owner);

        Mockito.doNothing().when(catRepository).addNewCat(Mockito.any(Cat.class));
        Mockito.doNothing().when(ownerRepository).addNewOwner(Mockito.any(Owner.class));
        Mockito.doNothing().when(catRepository).addFriend(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(ownerRepository.getOwnerById(Mockito.anyInt())).thenReturn(new Owner("test owner", Date.valueOf("2006-02-01")));
        Mockito.when(catRepository.getCatById(1)).thenReturn(new Cat("test cat", Date.valueOf("2006-01-02"), Breed.valueOf("AMERICAN_SHORTHAIR"), Color.valueOf("PINK"), owner));
        Mockito.when(catRepository.getCatById(2)).thenReturn(new Cat("test friend", Date.valueOf("2006-01-02"), Breed.valueOf("BRITMAN"), Color.valueOf("BLUE"), owner));
        Mockito.when(catRepository.getAllFriends(Mockito.anyInt())).thenReturn(Collections.singletonList(friend));

        ownerService.addNewOwner("test owner", "2006-02-01");
        catService.addNewCat(1, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK");
        catService.addNewCat(1, "test friend", "2006-02-01", "BRITMAN", "BLUE");
        catService.addFriend(1, 2);

        List<Cat> friends = catService.getAllFriends(1);
        assertEquals(1, friends.size());
    }

    @Test
    public void createCat_addToNonexistentOwner_GetException() {
        Mockito.doNothing().when(catRepository).addNewCat(Mockito.any(Cat.class));

        assertThrows(IllegalArgumentException.class,
                () -> catService.addNewCat(5, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK"));
    }

    @Test
    public void createCat_addNonexistentFriend_GetException(){
        Owner owner = new Owner("test owner", Date.valueOf("2006-02-01"));
        Mockito.doNothing().when(catRepository).addNewCat(Mockito.any(Cat.class));
        Mockito.doNothing().when(ownerRepository).addNewOwner(Mockito.any(Owner.class));
        Mockito.when(ownerRepository.getOwnerById(Mockito.anyInt())).thenReturn(new Owner("test owner", Date.valueOf("2006-02-01")));
        Mockito.when(catRepository.getCatById(1)).thenReturn(new Cat("test cat", Date.valueOf("2006-01-02"), Breed.valueOf("AMERICAN_SHORTHAIR"), Color.valueOf("PINK"), owner));
        Mockito.doNothing().when(catRepository).addFriend(Mockito.anyInt(), Mockito.anyInt());

        ownerService.addNewOwner("test owner", "2006-02-01");
        catService.addNewCat(1, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK");

        assertThrows(IllegalArgumentException.class,
                () -> catService.addFriend(1, 5));
    }
}
*/
