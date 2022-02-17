package presentation;
import java.awt.*;

/**
 * <p>
 *     Clasa care este folosita pentru rularea programului , aici se creeaza cele 3 interfete
 * </p>
 * @author Vlad-Andrei Balcanu
 */
public class Controller {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientGUI clientGUI = new ClientGUI();
                    OrdersGUI ordersGUI=new OrdersGUI();
                    ProductGUI productGUI=new ProductGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
