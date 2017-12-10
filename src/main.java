import BO.Message;
import DB.MessageDb;

import java.util.List;


/**
 * Created by douglas on 11/22/17.
 */
public class main {
    private static String Url ="http://localhost:8080/resource";

    public static void main(String[] args) {
        MessageDb messageDb = new MessageDb();
       List<Message> messages = messageDb.getMessagesByUserId(20);
        for (Message m: messages) {
            System.out.println(m.getDate());

        }
    }
}
