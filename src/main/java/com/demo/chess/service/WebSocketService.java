package com.demo.chess.service;

import com.demo.chess.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket")
public class WebSocketService {
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        logger.debug("New connection, client number: {}", getOnlineCount());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        logger.debug("Connection closed, client number: {}", getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.debug("Message from client: {}", message);
        if ((message == null) || (message.length() == 0)) {
            return;
        }

        String result;
        String msg;
        String[] posArray, pos;
        Position posFrom = new Position(), posTo = new Position();
        int index;
        Piece piece;

        String[] str = message.split("@");
        switch (str[0]) {
            case "ready":
                logger.debug("player: {}-{}-{}-{}", player1, player2, player1.getReady(), player2.getReady());
                if (player1 == null) {
                    player1 = new Player();
                }
                if (player2 == null) {
                    player2 = new Player();
                }

                if (!player1.getReady()) {    // one player ready
                    player1.setReady(true);
                    player1.setSession(session);
                    msg = "ready@player1 is ready";
                    sendOtherMessage(msg, session);
                } else if (!player2.getReady()) {     // start chess
                    player2.setReady(true);
                    player2.setSession(session);
                    msg = "ready@player2 is ready";
                    sendOtherMessage(msg, session);
                    board = new Board();
                    logger.debug("board: {}", board.getPieceList().size());
                    msg = "start@";
                    sendAllMessage(msg);
                } else {    // chess already started

                }
                break;
            case "move":
                logger.debug("move: {}", str[1]);
                posArray = str[1].split(":");
                pos = posArray[0].split(",");
                posFrom.setX(Integer.parseInt(pos[0]));
                posFrom.setY(Integer.parseInt(pos[1]));
                pos = posArray[1].split(",");
                posTo.setX(Integer.parseInt(pos[0]));
                posTo.setY(Integer.parseInt(pos[1]));

                result = Role.move(board, posFrom, posTo);
                msg = "move@" + result;
                sendSelfMessage(msg, session);
                break;
            case "eat":
                posArray = str[1].split(":");
                pos = posArray[0].split(",");
                posFrom.setX(Integer.parseInt(pos[0]));
                posFrom.setY(Integer.parseInt(pos[1]));
                pos = posArray[1].split(",");
                posTo.setX(Integer.parseInt(pos[0]));
                posTo.setY(Integer.parseInt(pos[1]));

                result = Role.eat(board, posFrom, posTo);
                msg = "move@" + result;
                sendSelfMessage(msg, session);
                break;
            case "exchange":
                posArray = str[1].split(":");
                pos = posArray[0].split(",");
                posFrom.setX(Integer.parseInt(pos[0]));
                posFrom.setY(Integer.parseInt(pos[1]));
                pos = posArray[1].split(",");
                posTo.setX(Integer.parseInt(pos[0]));
                posTo.setY(Integer.parseInt(pos[1]));

                result = Role.exchange(board, posFrom, posTo);
                msg = "move@" + result;
                sendSelfMessage(msg, session);
                break;
            case "flip":
                pos = str[1].split(",");
                index = Integer.parseInt(pos[0]) * Board.getVert() + Integer.parseInt(pos[1]);
                logger.debug("index: {}", index);
                piece = board.getPieceList().get(index);
                result = Role.flip(piece);
                msg = "flip@" + result;
                sendSelfMessage(msg, session);

                logger.debug("index: {}, name: {}, color: {}", index, piece.getName(), piece.getColor());
                if ((player1.getSession() == session) && (player1.getColor().length() == 0)) {
                    if (piece.getColor().equals(pieceColor[0])) {
                        player1.setColor(pieceColor[0]);
                        msg = "color@" + pieceColor[0];
                        sendSelfMessage(msg, session);

                        player2.setColor(pieceColor[1]);
                        msg = "color@" + pieceColor[1];
                        sendOtherMessage(msg, session);
                    } else {
                        player1.setColor(pieceColor[1]);
                        msg = "color@" + pieceColor[1];
                        sendSelfMessage(msg, session);

                        player2.setColor(pieceColor[0]);
                        msg = "color@" + pieceColor[0];
                        sendOtherMessage(msg, session);
                    }
                } else if ((player2.getSession() == session) && (player2.getColor().length() == 0)) {
                    if (piece.getColor().equals(pieceColor[0])) {
                        player1.setColor(pieceColor[1]);
                        msg = "color@" + pieceColor[1];
                        sendOtherMessage(msg, session);

                        player2.setColor(pieceColor[0]);
                        msg = "color@" + pieceColor[0];
                        sendSelfMessage(msg, session);
                    } else {
                        player1.setColor(pieceColor[0]);
                        msg = "color@" + pieceColor[0];
                        sendOtherMessage(msg, session);

                        player2.setColor(pieceColor[1]);
                        msg = "color@" + pieceColor[1];
                        sendSelfMessage(msg, session);
                    }
                }
                break;
            case "lose":
                String color;
                if (player1.getSession() == session) {
                    color = player2.getColor();
                } else {
                    color = player1.getColor();
                }
                msg = "over@" + color + " win";
                sendAllMessage(msg);
                break;
        }
    }

    @OnError
    public void onError (Session session, Throwable error) {
        logger.debug("Error occur");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendSelfMessage(String message, Session session) {
        for (WebSocketService item: webSocketSet) {
            try {
                if (item.session == session) {
                    item.sendMessage(message);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendOtherMessage(String message, Session session) {
        for (WebSocketService item: webSocketSet) {
            try {
                if (item.session == session) {
                    continue;
                }
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendAllMessage(String message) {
        for (WebSocketService item: webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static CopyOnWriteArraySet<WebSocketService> getWebSocketSet() {
        return webSocketSet;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount ++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount --;
    }

    private static String[] pieceColor = {"red", "black"};
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    private Session session;
    private static Board board;
//    private static Piece piece;
    private static Player player1 = new Player();
    private static Player player2 = new Player();

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
}
