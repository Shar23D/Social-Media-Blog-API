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
        return messageDAO.insertMessage(accountID, messageText);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        return messageDAO.deleteMessageById(messageId);
    }
    
    public Message updateMessageById(int messageId, String message) {
        return messageDAO.updateMessageById(messageId, message);
    }
    
    public List<Message> getAllMessagesbyAccountId(int accountId) {
        return messageDAO.getAllMessagesbyAccountId(accountId);
    }
}
