import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Producer {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();

        // Start a non-transacted session for sending messages
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = (Destination) context.lookup("test");
        MessageProducer consumer = session.createProducer(destination);

        // Create text message
        Message message = session.createTextMessage("Hello World");

        // Send 10 "Hello World" messages to queue
        for (int i = 0; i < 10; i++) {
            consumer.send(message);
        }

        // Clean up
        session.close();
        connection.close();
        context.close();

    }
}