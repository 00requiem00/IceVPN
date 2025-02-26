package org.icevpn.Telegram;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendPhoto;
import org.icevpn.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Menu {
//---------------------------------------------------------------------------------------------------------- Message
    public void menuMessage(long chatId, String name) {
        String main = "\uD83D\uDC4B Здравствуйте, <b>" + name + "</b>, представляем вам один из самых быстрых" +
                " и дешевых приватных VPN\n \n<b>Цена:</b>" +
                "\n<blockquote><s><b>300</b></s> <b>250 руб/месяц</b></blockquote>" +
                "\n<blockquote><s><b>900</b></s> <b>650 руб/3 месяца</b></blockquote>" +
                "\n<blockquote><s><b>3600</b></s> <b>2500 руб/год</b></blockquote>\n" +
                "\nскорость: 300-10.000 Mbp/s \uD83D\uDE85 | приватный |" +
                "количество устройств: ♾\uFE0F\n";
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
        List<InlineKeyboardButton[]> menuRows = new ArrayList<>();
        menuRows.add(new InlineKeyboardButton[]{
            new InlineKeyboardButton("\uD83D\uDCB5 Купить VPN").callbackData("buy")
        });
        menuRows.add(new InlineKeyboardButton[]{
            new InlineKeyboardButton("\uD83D\uDD14 Наш канал").url("https://t.me/iceeevpn"),
                new InlineKeyboardButton("\uD83D\uDCAC Поддержка").callbackData("support")
        });
        menuRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("\uD83D\uDCD6 Информация").callbackData("info")
        });
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Settings
        InlineKeyboardMarkup menuSection = new InlineKeyboardMarkup();
        for (InlineKeyboardButton[] row : menuRows) {
            menuSection.addRow(row);
        }
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("logo.png");
            BufferedImage image = ImageIO.read(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            SendPhoto sendPhoto = new SendPhoto(chatId, imageBytes);
            sendPhoto.caption(main).parseMode(ParseMode.HTML).replyMarkup(menuSection);
            Main.bot.execute(sendPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Smth
    public void backToMainMessage(CallbackQuery callbackQuery, String name) {
        String callbackData = callbackQuery.data();
        long chatId = callbackQuery.message().chat().id();
        int messageId = callbackQuery.message().messageId();
        if ("back_to_main".equals(callbackData)) {
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Message
            String back = "\uD83D\uDC4B Здравствуйте, <b>" + name + "</b>, представляем вам один из самых быстрых" +
                    " и дешевых приватных VPN\n \n<b>Цена:</b>" +
                    "\n<blockquote><s><b>300</b></s> <b>250 руб/месяц</b></blockquote>" +
                    "\n<blockquote><s><b>900</b></s> <b>650 руб/месяц</b></blockquote>" +
                    "\n<blockquote><s><b>3600</b></s> <b>2500 руб/месяц</b></blockquote>\n" +
                    "\nскорость: 300-10.000 Mbp/s \uD83D\uDE85 | приватный |" +
                    "количество устройств: ♾\uFE0F\n";
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> menuRows = new ArrayList<>();
            menuRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83D\uDCB5 Купить VPN").callbackData("buy")
            });
            menuRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83D\uDD14 Наш канал").url("https://t.me/iceeevpn"),
                    new InlineKeyboardButton("\uD83D\uDCAC Поддержка").url("https://t.me/IceeVpnSupport")
            });
            menuRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83D\uDCD6 Информация").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : menuRows) {
                keyboardMarkup.addRow(row);
            }
            InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("logo.png");
            Main.options.editPhotoAndLore(chatId, messageId, newPhotoStream, back, keyboardMarkup);
        }
    }
}
