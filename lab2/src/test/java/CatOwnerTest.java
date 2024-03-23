import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.breeds.Breed;
import ru.itmo.cats.Cat;
import ru.itmo.cats.CatServiceImpl;
import ru.itmo.colors.Color;
import ru.itmo.owners.OwnerServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CatOwnerTest {
    private CatServiceImpl catService;
    private OwnerServiceImpl ownerService;

    @BeforeEach
    public void setup() {
        catService = Mockito.mock(CatServiceImpl.class);
        ownerService = Mockito.mock(OwnerServiceImpl.class);
    }

    @Test
    public void createOwnerCreateCat_addCatToOwner_GetAllCats() {

        Mockito.doNothing().when(catService).addNewCat(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(ownerService).addNewOwner(Mockito.anyString(), Mockito.anyString());
        Mockito.when(ownerService.getAllCatsId(Mockito.anyInt())).thenReturn(new ArrayList<>(Arrays.asList(0)));

        ownerService.addNewOwner("test owner", "2006-02-01");
        catService.addNewCat(0, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK");

        ArrayList<Integer> cats = ownerService.getAllCatsId(0);

        assertEquals(1, cats.size());
    }
    @Test
    public void createCat_addFriendToCat_GetListFriends(){
        Cat friend = new Cat("test friend", Date.valueOf("2006-02-01"), Breed.BRITMAN, Color.BLUE);

        Mockito.doNothing().when(catService).addNewCat(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(ownerService).addNewOwner(Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(catService).addFriend(Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(catService.getAllFriends(Mockito.anyInt())).thenReturn(new ArrayList<>(Arrays.asList(friend)));

        ownerService.addNewOwner("test owner", "2006-02-01");
        catService.addNewCat(0, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK");
        catService.addNewCat(0, "test friend", "2006-02-01", "BRITMAN", "BLUE");
        catService.addFriend(0, 1);

        ArrayList<Cat> friends = catService.getAllFriends(0);
        assertEquals(1, friends.size());
    }
    @Test
    public void createCat_addToNonexistentOwner_GetException() {
        Mockito.doThrow(new IllegalArgumentException("Incorrect owner id")).when(catService).addNewCat(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        assertThrows(IllegalArgumentException.class, () -> catService.addNewCat(0, "test cat", "2006-01-02", "AMERICAN_SHORTHAIR", "PINK"));
    }
}
