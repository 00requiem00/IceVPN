package org.icevpn.Telegram;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.icevpn.Main;
import org.icevpn.Payment;

import static org.icevpn.Main.bot;

public class UpdateListener {
    public static void main(String[] args) {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message() != null) {
                    long chatId = update.message().chat().id();
                    String text = update.message().text();

                    if ("/start".equals(text)) {
                        if (update.message().from() != null) {
                            String firstName = update.message().from().firstName();
                            Main.menu.menuMessage(chatId, firstName);
                        } else {
                            System.out.println("Пользователь не определен (from() == null)");
                        }
                    }
                } else if (update.callbackQuery() != null) {
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String callbackData = callbackQuery.data();
                    Message message = callbackQuery.message();
                    long chatId = message.chat().id();
                    int messageId = message.messageId();

                    bot.execute(new AnswerCallbackQuery(callbackQuery.id()));
                    switch (callbackData) {
                        case "buy", "oneMonth":
                            Main.buy.buyMessage(callbackQuery);
                            break;
                        case "back_to_main":
                            String firstName = callbackQuery.from().firstName();
                            Main.menu.backToMainMessage(callbackQuery, firstName);
                            break;
                        case "info", "us", "devices", "location", "count", "security":
                            Main.help.helpMessages(callbackQuery);
                            break;
                        case "deletemessage":
                            Options.deleteMessage(chatId, messageId);
                            break;
                        case "bought":
                            Main.payment.paymentConfirmation(callbackQuery);
                            break;
                        default:
                            if (callbackData.startsWith("buy:")) {
                                String[] parts = callbackData.split(":");
                                Main.buy.handleBuy(chatId, parts[1], callbackQuery);
                            }
                            else if (callbackData.startsWith("selectinstruction:")) {
                                String[] parts = callbackData.split(":");
                                String targetButton = parts[1]; // Получаем "ios" из "selectinstruction:ios"
                                Options.updateButtonText(bot, callbackQuery, targetButton +":", "IOS ✅", "ios:checked");
                            }
                    }
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}