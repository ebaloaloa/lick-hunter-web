package com.lickhunter.web.controllers;

import com.binance.client.SyncRequestClient;
import com.binance.client.model.enums.CandlestickInterval;
import com.lickhunter.web.configs.Settings;
import com.lickhunter.web.constants.ApplicationConstants;
import com.lickhunter.web.services.AccountService;
import com.lickhunter.web.services.FileService;
import com.lickhunter.web.services.MarketService;
import com.lickhunter.web.websockets.BinanceSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/application")
@RestController
@RequiredArgsConstructor
public class ApplicationController extends BaseController {

    private final BinanceSubscription binanceSubscription;
    private final MarketService marketService;
    private final AccountService accountService;
    private final FileService fileService;

    @GetMapping("/candlestick/subscribe")
    public ResponseEntity<?> subscribeCandleStickData() throws Exception {
        binanceSubscription.subscribeCandleStickData();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/candlestick")
    public ResponseEntity<?> getCandleStickData(@RequestParam String interval, @RequestParam String limit) throws Exception {
        marketService.getCandleStickData(CandlestickInterval.valueOf(interval), Integer.valueOf(limit));
        return ResponseEntity.ok(null);
    }

    @GetMapping("/userdata")
    public ResponseEntity<?> subscribeUserData() throws Exception {
        binanceSubscription.subscribeUserData();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/markprice")
    public ResponseEntity<?> getMarkPriceData() throws Exception {
        marketService.getMarkPriceData();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/subscribe/markprice")
    public ResponseEntity<?> subscribeMarkPrice() throws Exception {
        binanceSubscription.subscribeMarkPrice();
        return ResponseEntity.ok(null);
    }

    @GetMapping("/account_information")
    public ResponseEntity<?> getAccountInformation() throws Exception {
        Settings settings = (Settings) fileService.readFromFile("./", ApplicationConstants.SETTINGS.getValue(), Settings.class);
        SyncRequestClient syncRequestClient = SyncRequestClient.create(settings.getKey(), settings.getSecret());
        return ResponseEntity.ok(syncRequestClient.getAccountInformation());
    }
}
