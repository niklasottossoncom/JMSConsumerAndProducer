import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;


public class Consumer {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination destination = (Destination) context.lookup("test");
        MessageConsumer consumer = session.createConsumer(destination);

        Message message;

        try {
            while ((message = consumer.receive(3000)) != null) {
                System.out.println("Message: " + message);
            }
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();

            session.rollback();
        }

        session.close();
        connection.close();
        context.close();

    }
}