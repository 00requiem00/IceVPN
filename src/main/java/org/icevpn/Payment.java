package org.icevpn;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputPollOption;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendPoll;
import com.pengrad.telegrambot.response.BaseResponse;
import okhttp3.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.icevpn.Main.bot;

public class Payment {

    public String createPaymentLink(Double price) {
        OkHttpClient client = new OkHttpClient();

        String shopId = "1037672";//"<Идентификатор магазина>"; //1015692
        String secretKey = "test_DB0aFGWMo0mEhqsUI8xxmpYlyD-ex_oAQcHiguKJE0g";//"<Секретный ключ>"; //live_21X2yeOf9T4QpLCMCPHo20nCCdlI1q4hE6UGLUz_lUo
        //String idempotenceKey = "1234";//"<Ключ идемпотентности>"; //390540012:LIVE:63816

        JsonObject amount = new JsonObject();
        String value = String.valueOf(price);
        System.out.println(value);
        amount.addProperty("value", value);
        amount.addProperty("currency", "RUB");

        JsonObject confirmation = new JsonObject();
        confirmation.addProperty("type", "redirect");
        confirmation.addProperty("return_url", "https://www.example.com/return_url");

        JsonObject payload = new JsonObject();
        payload.add("amount", amount);
        payload.addProperty("capture", true);
        payload.add("confirmation", confirmation);
        payload.addProperty("description", "Заказ №2");

        String json = payload.toString();

        Request request = new Request.Builder()
                .url("https://api.yookassa.ru/v3/payments")
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .addHeader("Idempotence-Key", UUID.randomUUID().toString())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(shopId, secretKey))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                //System.out.println("Response: " + response.body().string());
                String jsonResponse = response.body().string();
                JsonObject responseObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject confirmationObject = responseObject.getAsJsonObject("confirmation");
                return confirmationObject.get("confirmation_url").getAsString();
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public void paymentConfirmation(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.data();
        long chatId = callbackQuery.message().chat().id();
        int messageId = callbackQuery.message().messageId();
        SendPoll sendPoll = new SendPoll(chatId, "Выберите несколько вариантов:",
                new InputPollOption("Вариант 1"),
                new InputPollOption("Вариант 2"),
                new InputPollOption("Вариант 3"),
                new InputPollOption("Вариант 4"))
                .isAnonymous(false)
                .allowsMultipleAnswers(true);
        InputStream newPhotoStream = Main.class.getClassLoader().getResourceAsStream("info.png");

        if (newPhotoStream == null) {
            System.err.println("Error: Failed to load 'info.png'. Ensure it exists in the classpath.");
            return;
        }

        InlineKeyboardMarkup noButtons = null;
    }
}

