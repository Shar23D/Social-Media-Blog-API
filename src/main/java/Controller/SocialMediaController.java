package Controller;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::addMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);
        app.start(8080);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            context.json(mapper.writeValueAsString(addedAccount));
        }
        else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account login = accountService.verifyLogin(account);
        if (login != null) {
            context.json(mapper.writeValueAsString(login));
        }
        else {
            context.status(401);
        }
    }

    private void addMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Account account = accountService.getAccountId(message.getPosted_by());
        Message addedMessage = messageService.addMessage(account.getAccount_id(), message.getMessage_text());
        if (addedMessage != null && account != null) {
            context.json(mapper.writeValueAsString(addedMessage));
        }
        else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message message = messageService.GetMessageById(messageId);
        context.json(message);
    }
    
    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message message = messageService.deleteMessageById(messageId);
        context.json(message);
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message updatedMessage = messageService.updateMessage(messageId, message);
        if (updatedMessage != null) {
            context.json(mapper.writeValueAsString(updatedMessage));
        }
        else {
            context.status(400);
        }
    }

    private void getAllMessagesByAcountIdHandler(Context context) {
        int accountId = Integer.parseInt(Objects.requireNonNull(context.pathParam("account_id")));
        List<Message> messages = messageService.getAllMessagesbyAccountId(accountId);
        context.json(messages);
    }





}