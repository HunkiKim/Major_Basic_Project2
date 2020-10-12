package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.asset.Langs;

public class MainController implements IController {
    @Override
    public void start() {
        UI.print(Langs.MAIN);
    }
}
