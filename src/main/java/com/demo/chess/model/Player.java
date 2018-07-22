package com.demo.chess.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

public class Player {
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public Boolean getTurn() {
        return isTurn;
    }

    public void setTurn(Boolean turn) {
        isTurn = turn;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private String color = "";
    private Boolean isReady = false;    // whether is ready or not
    private Boolean isTurn = false;     // whether turn or not
    private Session session;

    private static final Logger logger = LoggerFactory.getLogger(Player.class);
}
