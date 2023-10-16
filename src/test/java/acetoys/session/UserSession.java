package acetoys.session;

import io.gatling.javaapi.core.ChainBuilder;

import java.text.DecimalFormat;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class UserSession {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static ChainBuilder initSession =
            exec(flushSessionCookies())
                    .exec(session -> session.set("productsListPageNumber", 1))
                    .exec(session -> session.set("customerLoggedIn", false))
                    .exec(session -> session.set("itemsInBasket", 0))
                    .exec(session -> session.set("basketTotal", 0.00));

    public static ChainBuilder increaseItemsInBasketForSession =
            exec(session -> {
                var itemsInBasket = session.getInt("itemsInBasket");
                return session.set("itemsInBasket", itemsInBasket + 1);
            });

    public static ChainBuilder decreaseItemsInBasketForSession =
            exec(session -> {
                var itemsInBasket = session.getInt("itemsInBasket");
                return session.set("itemsInBasket", itemsInBasket - 1);
            });

    public static ChainBuilder increaseSessionBasketTotal =
            exec(session -> {
                var currentBasketTotal = session.getDouble("basketTotal");
                var itemPrice = session.getDouble("price");
                var updateBasketTotal = currentBasketTotal + itemPrice;
                return session.set("basketTotal", df.format(updateBasketTotal));
            });

    public static ChainBuilder decreaseSessionBasketTotal =
            exec(session -> {
                var currentBasketTotal = session.getDouble("basketTotal");
                var itemPrice = session.getDouble("price");
                var updateBasketTotal = currentBasketTotal - itemPrice;
                return session.set("basketTotal", df.format(updateBasketTotal));
            });
}
