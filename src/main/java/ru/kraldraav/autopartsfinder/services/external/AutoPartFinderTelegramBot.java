package ru.kraldraav.autopartsfinder.services.external;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kraldraav.autopartsfinder.services.sources.AutopartsCatalogService;
import ru.kraldraav.autopartsfinder.utils.Strings;

@Log4j2
@Component
@RequiredArgsConstructor
public class AutoPartFinderTelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private final AutopartsCatalogService autopartsCatalogService;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();

        if (!message.hasText()) {
            log.error("There is no text in message");
            return;
        }

        var messageText = message.getText();

        if (messageText.equals("/start")) {
            sendAnswerMessage(message.getChatId(), "Введите part number для отображения стоимости запчасти");
            return;
        }

        var foundParts = autopartsCatalogService.getAutopartByArticle(messageText);

        if (foundParts == null) {
            sendAnswerMessage(message.getChatId(), "По вашему запросу ничего не найдено! Возможно вы указали неверный part number..");
        }

        var answerText = new StringBuilder();
        foundParts.forEach(x -> {
            log.info(x.toString());
            answerText.append("%s\n".formatted(x));
        });

        var answers = Strings.splitEqually(answerText.toString(), 4096);
        for (var answer : answers) {
            sendAnswerMessage(message.getChatId(), answer);
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    private void sendAnswerMessage(long chatId, String messageText) {
        var messageToSend = new SendMessage();
        messageToSend.setChatId(String.valueOf(chatId));
        messageToSend.setText(messageText);
        try {
            execute(messageToSend);
        } catch (TelegramApiException e) {
            log.error("Error while sending answer to chat! %s".formatted(e.getMessage()));
        }
    }
}
