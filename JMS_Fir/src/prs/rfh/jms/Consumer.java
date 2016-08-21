package prs.rfh.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * ������
 * @author Swift
 *
 */
public class Consumer {
	//Ĭ�������û���
     static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //Ĭ����������
     static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //Ĭ�����ӵ�ַ
     static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //���͵���Ϣ����
     static final int SENDNUM = 10;

    public static void main(String[] args) {
        //���ӹ���
        ConnectionFactory connectionFactory;
        //����
        Connection connection = null;
        //�Ự ���ܻ��߷�����Ϣ���߳�
        Session session;
        //��Ϣ��Ŀ�ĵ�
        Destination destination;
        //��Ϣ������
        MessageProducer messageProducer;
        //ʵ�������ӹ���
        connectionFactory = new ActiveMQConnectionFactory(Producer.USERNAME, Producer.PASSWORD, Producer.BROKEURL);

        try {
            //ͨ�����ӹ�����ȡ����
            connection = connectionFactory.createConnection();
            //��������
            connection.start();
            //����session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //����һ������ΪHelloWorld����Ϣ����
            destination = session.createQueue("HelloWorld");
            //������Ϣ������
            messageProducer = session.createProducer(destination);
            //������Ϣ
            sendMessage(session, messageProducer);

            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * ������Ϣ
     * @param session
     * @param messageProducer  ��Ϣ������
     * @throws Exception
     */
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < Producer.SENDNUM; i++) {
            //����һ���ı���Ϣ 
            TextMessage message = session.createTextMessage("ActiveMQ ������Ϣ" +i);
            System.out.println("������Ϣ��Activemq ������Ϣ" + i);
            //ͨ����Ϣ�����߷�����Ϣ 
            messageProducer.send(message);
        }

    }
}