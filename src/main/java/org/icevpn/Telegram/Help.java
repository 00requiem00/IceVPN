package org.icevpn.Telegram;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.icevpn.Main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Help {
//---------------------------------------------------------------------------------------------------------- Smth
    public void helpMessages(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.data();
        long chatId = callbackQuery.message().chat().id();
        int messageId = callbackQuery.message().messageId();
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- INFO MESSAGE
        if ("info".equals(callbackData)) {
            String info = "ℹ\uFE0F Выберите, что вас интересует";
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83E\uDD14 Почему мы").callbackData("us")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83D\uDCF1 На какие устройства").callbackData("devices")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83C\uDF0D Где находится VPN").callbackData("location")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83C\uDF10 Сколько устройств").callbackData("count")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("\uD83D\uDEE1 Конфиденциальность").callbackData("security")
            });
            keyboardRows.add(new InlineKeyboardButton[]{
                new InlineKeyboardButton("◀\uFE0F Назад").callbackData("back_to_main")
            });
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("info.png");
            Main.options.editPhotoAndLore(chatId, messageId, newPhotoStream, info, keyboardMarkup);
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- US MESSAGE
        } else if ("us".equals(callbackData)) {
            String us_info = ("\uD83E\uDD14 <b>Почему мы?</b> \nПо данным расценкам не существует (как нам известно) VPN," +
                    " которые могут предложить ту же скорость и цену, как мы!" +
                    "Наш VPN подключается моментально без какой либо рекламы!" +
                    "Мы лишь предлагаем вам один из лучших методов продолжить" +
                    "использовать интернет, как это было раньше...");
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------  Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            Main.options.editLore(chatId, messageId, us_info, keyboardMarkup);

//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- DEVICES MESSAGE
        } else if ("devices".equals(callbackData)) {
            String devices_info = ("\uD83D\uDCF1 <b>На какие устройства?</b> \nНаш VPN доступен на всех " +
                    "операционных системах (IPhone, Android, MacOS, Windows, Linux).");
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------  Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            Main.options.editLore(chatId, messageId, devices_info, keyboardMarkup);
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- LOCATION MESSAGE
        } else if ("location".equals(callbackData)) {
            String location_info = ("\uD83C\uDF0D <b>Где находится VPN?</b> \nСервера VPN находятся в Германия. " +
                    "Это самый быстрый и оптимальный вариант, из возможных (Пинг около 30-40).");
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------  Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            Main.options.editLore(chatId, messageId, location_info, keyboardMarkup);
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- COUNT MESSAGE
        } else if ("count".equals(callbackData)) {
            String count_info = ("\uD83C\uDF10 <b>Сколько устройств</b> \nВы можете подключить к вашему " +
                    "ключу любое количество устройств, но они не могут быть подключены одновременно." +
                    "Сделано это для того, чтобы ваш ключ не использовался третьими лицами.");
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------  Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            Main.options.editLore(chatId, messageId, count_info, keyboardMarkup);
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- SECURITY MESSAGE
        } else if ("security".equals(callbackData)) {
            String security_info = ("\uD83D\uDEE1 <b>Конфиденциальность</b> \nКлюч подключения даётся " +
                    "каждому человеку лично и у каждого он уникален, перехват данных невозможен из-за 5-ти" +
                    "кратной шифрации. Используемый протокол нашего VPN отличается от традиционных VPN тем," +
                    "что он труднее для обнаружения и блокировки, поскольку его трафик не похож на типичный VPN - трафик!");
//----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------- Buttons
            List<InlineKeyboardButton[]> keyboardRows = new ArrayList<>();
            keyboardRows.add(new InlineKeyboardButton[]{
                    new InlineKeyboardButton("◀\uFE0F Назад").callbackData("info")
            });
//----------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------  Settings
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (InlineKeyboardButton[] row : keyboardRows) {
                keyboardMarkup.addRow(row);
            }
            Main.options.editLore(chatId, messageId, security_info, keyboardMarkup);
        }
    }
}
