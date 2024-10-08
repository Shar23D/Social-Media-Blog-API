package Controller;

import java.util.ArrayList;
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
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
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
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if ((addedAccount != null) && !(addedAccount.getUsername().isEmpty())
                && (addedAccount.getPassword().length() >= 4)) {
            context.json(mapper.writeValueAsString(addedAccount));
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account login = accountService.verifyLogin(account);
        if (login != null && (login.getPassword().length() >= 4)) {
            context.json(mapper.writeValueAsString(login));
        } else {
            context.status(401);
        }
    }

    private void addMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        Account account = accountService.getAccountById(message.getPosted_by());
        if ((account != null) && (addedMessage != null) && !(addedMessage.getMessage_text().isEmpty())
                && (addedMessage.getMessage_text().length() < 255)) {
            context.json(mapper.writeValueAsString(addedMessage));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        if (!messages.isEmpty()) {
            context.json(messages);
        } else {
            context.json(new ArrayList<>());
        }
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.json("");
        }
    }

    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message message = messageService.deleteMessageById(messageId);
        if (message != null) {
            context.json(message);
        } else {
            context.json("");
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int messageId = Integer.parseInt(Objects.requireNonNull(context.pathParam("message_id")));
        Message updatedMessage = messageService.updateMessageById(messageId, message.getMessage_text());
        if (updatedMessage != null && !(updatedMessage.getMessage_text().isEmpty())
                && (updatedMessage.getMessage_text().length() < 255)) {
            context.json(mapper.writeValueAsString(updatedMessage));
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesByAccountIdHandler(Context context) {
        int accountId = Integer.parseInt(Objects.requireNonNull(context.pathParam("account_id")));
        Account account = accountService.getAccountById(accountId);
        if (account != null && !(account.getUsername().isEmpty())) {
            List<Message> messages = messageService.getAllMessagesbyAccountId(accountId);
            if (!messages.isEmpty()) {
                context.json(messages);
            } 
        }
        else {
            context.json(new ArrayList<>());
        }
    }

    public void stopAPI(Javalin app) {
        app.stop();

    }
}