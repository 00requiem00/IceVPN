package org.icevpn.Telegram;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import org.icevpn.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Buy {
    private Double PRICE_1m = 250.00;
    private Double PRICE_3m = 650.00;
    private Double PRICE_12m = 2500.00;
    private TelegramBot bot = Main.bot;
    private final ConcurrentHashMap<Long, Integer> cache = new ConcurrentHashMap<>();
    //---------------------------------------------------------------------------------------------------------- Smth
    public void buyMessage(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.data();
        long chatId = callbackQuery.message().chat().id();
        int messageId = callbackQuery.message().messageId();
        if ("buy".equals(callbackData)) {
//-----------------------------------------Buy-----------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Message
            String buyButtomAnswer = "\uD83C\uDFAB Выберите устройства, на которых будет использоваться VPN";
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("IOS").callbackData("selectinstruction:ios:notchecked")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("Android").callbackData("selectinstruction:android:notchecked")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("MacOS").callbackData("selectinstruction:macos:notchecked")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("Windows").callbackData("selectinstruction:windows:notchecked")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("back_to_main"),
            });
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("plans.png");
            Main.options.editPhotoAndLore(chatId, messageId, newPhotoStream, buyButtomAnswer, keyboardMarkup);
        }
        if ("select".equals(callbackData)) {
//-----------------------------------------Buy-----------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Message
            String buyButtomAnswer = "\uD83C\uDFAB Выберите тариф";
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("1 месяц").callbackData("buy:1m")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("3 месяца").callbackData("buy:3m")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("1 год").callbackData("buy:12m")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("◀\uFE0F Назад").callbackData("back_to_main"),
            });
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("plans.png");
            Main.options.editPhotoAndLore(chatId, messageId, newPhotoStream, buyButtomAnswer, keyboardMarkup);
        }
    }
    public void handleBuy(long chatId, String mounths, CallbackQuery callbackQuery) {
        int messageId = callbackQuery.message().messageId();
        String message = "\uD83D\uDCB3Для оплаты нажмите кнопку";
        List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
        if (mounths.equals("1m")) { //if (mounths.equals("1m")) {
            String url = Main.getPayment().createPaymentLink(PRICE_1m);
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("Оплатить (" + PRICE_1m + "₽)").url(url)
            });
        } else if (mounths.equals("3m")) {
            String url = Main.getPayment().createPaymentLink(PRICE_3m);
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("Оплатить (" + PRICE_3m + "₽)").url(url)
            });
        } else if (mounths.equals("12m")) {
            String url = Main.getPayment().createPaymentLink(PRICE_12m);
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("Оплатить (" + PRICE_12m + "₽)").url(url)
            });
        }
        keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("Тест").callbackData("bought"),
        });
        keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("❌Отменить").callbackData("buy"),
        });
        InlineKeyboardButton[][] keyboardArray = new InlineKeyboardButton[keyboardRows.size()][];
        keyboardArray = keyboardRows.toArray(keyboardArray);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardArray);

        InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("Payment.png");
        Main.options.editPhotoAndLore(chatId, messageId, newPhotoStream, message, keyboardMarkup);
    }
}
