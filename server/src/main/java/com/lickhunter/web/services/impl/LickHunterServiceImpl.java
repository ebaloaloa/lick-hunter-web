package com.lickhunter.web.services.impl;

import com.lickhunter.web.configs.MessageConfig;
import com.lickhunter.web.configs.UserDefinedSettings;
import com.lickhunter.web.configs.WebSettings;
import com.lickhunter.web.constants.ApplicationConstants;
import com.lickhunter.web.services.FileService;
import com.lickhunter.web.services.LickHunterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LickHunterServiceImpl implements LickHunterService {

    private final MessageConfig messageConfig;
    private final FileService fileService;

    public void startProfit() {
        try {
            Runtime.getRuntime().exec("Start Profit.cmd").waitFor();
            log.info(messageConfig.getStartProfit());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void stopProfit() {
        try {
            Runtime.getRuntime().exec("Stop Profit.cmd").waitFor();
            log.info(messageConfig.getStopProfit());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void startWebsocket(){
        try {
            Runtime.getRuntime().exec("Start Websocket.cmd").waitFor();
            log.info(messageConfig.getStartWebsocket());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void stopWebsocket() {
        try {
            Runtime.getRuntime().exec("Stop Websocket.cmd").waitFor();
            log.info(messageConfig.getStopWebsocket());
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void restart() {
        log.info("Restarting Lickhunter.");
        stopProfit();
        stopWebsocket();
        startProfit();
        startWebsocket();
    }

    @Override
    public UserDefinedSettings getActiveSettings() {
        WebSettings webSettings = (WebSettings) fileService.readFromFile("./", ApplicationConstants.WEB_SETTINGS.getValue(), WebSettings.class);
        return webSettings.getUserDefinedSettings().get(webSettings.getActive());
    }
}
