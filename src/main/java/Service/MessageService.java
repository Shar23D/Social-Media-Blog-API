package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(int accountId, String messageText) {
        Message message = new Message();
        message.setPosted_by(accountId);
        message.setMessage_text(messageText);
        return message;
    }

    public List<Message> getAllMessages() {

        return ;
    }



}
