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

    public Message addMessage(Message message) {          
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            messageDAO.deleteMessageById(messageId);
            return message;
        }
        return null;
    }
    
    public Message updateMessageById(int messageId, String message) {
        return messageDAO.updateMessageById(messageId, message);
    }
    
    public List<Message> getAllMessagesbyAccountId(int accountId) {
        return messageDAO.getAllMessagesbyAccountId(accountId);
    }
}
