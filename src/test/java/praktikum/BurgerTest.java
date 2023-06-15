package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {


    String bunName = "тестовая булка";
    float bunPrice = 7.75F;
    float ingredientPrice = 6.75F;

    Burger burger;
    Database database;
    Ingredient firstIngredient = mock(Ingredient.class);
    Ingredient secondIngredient = mock(Ingredient.class);
    Bun bun = mock(Bun.class);

    @Before
    public void setUp() {
        burger = new Burger();
        database = new Database();
    }

    // можно указать булку для бургера
    @Test
    public void checkSettingTheBun() {
        burger.setBuns(bun);
        Mockito.when(bun.getName()).thenReturn(bunName);
        String actual = burger.bun.getName();
        assertEquals("The bun name is not set!", actual, bunName);
    }

    //можно добавить ингредиент в бургер
    @Test
    public void checkOfAddingTheIngredient() {
        burger.addIngredient(firstIngredient);
        assertFalse("The ingredient was not added!", burger.ingredients.isEmpty());
    }

    //ингредиент можно убрать из бургера
    @Test
    public void checkOfRemovingTheIngredient() {
        burger.addIngredient(firstIngredient);
        burger.removeIngredient(0);
        assertTrue("The ingredient was not removed!", burger.ingredients.isEmpty());
    }

    //ингредиенты бургера можно менять местами в списке
    @Test
    public void checkOfMovingTheIngredient() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.moveIngredient(1, 0);
        assertEquals("The ingredient has not been moved!", burger.ingredients.indexOf(firstIngredient), 1);
    }

    //после выбора булки и ингредиента цена бургера !=0
    @Test
    public void checkGetOfPriceTheBurger() {
        burger.setBuns(bun);
        burger.addIngredient(firstIngredient);
        Mockito.when(bun.getPrice()).thenReturn(bunPrice);
        Mockito.when(firstIngredient.getPrice()).thenReturn(ingredientPrice);
        float correctPrice = bunPrice * 2 + ingredientPrice;
        assertEquals("Incorrect price!", burger.getPrice(), correctPrice, 0);
    }

    //Проверяем формат чека бургера

    @Test
    public void receiptHasBunNameTest(){
        burger.setBuns(database.availableBuns().get(0));
        String bunName = database.availableBuns().get(0).name;
        assertTrue("Нет булочки в чеке", burger.getReceipt().contains(bunName));
    }

    @Test
    public void receiptHasBunPriceTest(){
        burger.setBuns(database.availableBuns().get(0));
        int burgerPrice = (int)burger.getPrice();
        assertTrue("Нет названия в чеке", burger.getReceipt().contains(Integer.toString(burgerPrice)));
    }

    @Test
    public void receiptHasIngredientTypeTest(){
        burger.setBuns(bun);
        burger.addIngredient(database.availableIngredients().get(0));
        String ingredientType = database.availableIngredients().get(0).type.toString();
        assertTrue("Нет начинки в чеке",burger.getReceipt().contains(ingredientType.toLowerCase()));
    }
}
