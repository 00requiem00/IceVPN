package org.icevpn.Telegram;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import org.icevpn.Main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Options {
    private static TelegramBot bot = Main.bot;
    public static void updateButtonText(TelegramBot bot, CallbackQuery callbackQuery, String targetButton, String newButtonText, String newCallbackData) {
        long chatId = callbackQuery.message().chat().id();
        int messageId = callbackQuery.message().messageId();

        InlineKeyboardMarkup currentKeyboard = (InlineKeyboardMarkup) callbackQuery.message().replyMarkup();
        List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();

        for (InlineKeyboardButton[] row : currentKeyboard.inlineKeyboard()) {
            List<InlineKeyboardButton> newRow = new ArrayList<>();

            for (InlineKeyboardButton button : row) {
                if (button.callbackData().equals(targetButton)) {
                    InlineKeyboardButton updatedButton = new InlineKeyboardButton(newButtonText)
                            .callbackData(newCallbackData != null ? newCallbackData : button.callbackData());
                    newRow.add(updatedButton);
                } else {
                    newRow.add(button);
                }
            }

            keyboardRows.add(newRow.toArray(new InlineKeyboardButton[0]));
        }

        InlineKeyboardMarkup updatedKeyboard = new InlineKeyboardMarkup(keyboardRows.toArray(new InlineKeyboardButton[0][]));

        bot.execute(new EditMessageReplyMarkup(chatId, messageId).replyMarkup(updatedKeyboard));
    }
    public void editPhotoAndLore(long chatId, int messageId, InputStream newPhotoStream, String newCaption, InlineKeyboardMarkup keyboard) {
        try {                                                                           //поменять
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = newPhotoStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            byte[] imageBytes = baos.toByteArray();

            InputMediaPhoto newPhoto = new InputMediaPhoto(imageBytes).caption(newCaption).parseMode(ParseMode.HTML);

            EditMessageMedia editMessageMedia = new EditMessageMedia(chatId, messageId, newPhoto);

            if (keyboard != null) {
                editMessageMedia.replyMarkup(keyboard);
            }

            bot.execute(editMessageMedia, new Callback() {
                @Override
                public void onResponse(BaseRequest request, BaseResponse response) {
                    if (!response.isOk()) {
                        Main.logger.error("Ошибка при редактировании медиа: " + response.description());
                    }
                }

                @Override
                public void onFailure(BaseRequest request, IOException e) {
                    Main.logger.error("Ошибка при выполнении запроса: ", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (newPhotoStream != null) {
                    newPhotoStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void editLore(long chatId, int messageId, String newCaption, InlineKeyboardMarkup keyboard) {
        EditMessageCaption editMessageCaption = new EditMessageCaption(chatId, messageId)
                .caption(newCaption) // Новая подпись
                .parseMode(ParseMode.HTML);
        if (keyboard != null) {
            editMessageCaption.replyMarkup(keyboard);
            }

        bot.execute(editMessageCaption, new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse response) {
                if (!response.isOk()) {
                    Main.logger.error("Ошибка при редактировании подписи: " + response.description());
                }
            }

            @Override
            public void onFailure(BaseRequest request, IOException e) {
                Main.logger.error("Ошибка при выполнении запроса: ", e);
            }
        });
    }
    public static void deleteMessage(long chatId,  int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId, messageId);
        bot.execute(deleteMessage, new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse response) {}
            @Override
            public void onFailure(BaseRequest request, IOException e) {}
        });
    }
}