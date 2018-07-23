package com.demo.chess.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    public Board() {
        hori = 4;
        vert = 8;

        pieceList = new ArrayList<>();

        int index;
        for (int i=0; i<hori; i++) {
            for (int j=0; j<vert; j++) {
                index = i * vert + j;
                Piece piece = new Piece();
                if (index < pieceRedName.length) {
                    piece.setName(pieceRedName[index]);
                    piece.setValue(pieceValue[index]);
                    piece.setColor("red");
                } else {
                    int blackIndex = index - pieceRedName.length;
                    piece.setName(pieceBlackName[blackIndex]);
                    piece.setValue(pieceValue[blackIndex]);
                    piece.setColor("black");
                }
                piece.setNegative(true);
                //logger.debug("piece: {}-{}-{}-{}", piece.getName(), piece.getValue(), piece.getColor(), index);

                pieceList.add(piece);
            }
        }

        Collections.shuffle(pieceList);

        for (int i=0; i<hori*vert; i++) {
            Piece piece = pieceList.get(i);
            logger.debug("pieceList, index: {}, value: {}, name: {}, color: {}, negative: {}",
                    i, piece.getValue(), piece.getName(), piece.getColor(), piece.getNegative());
        }
    }

    public static int getHori() {
        return hori;
    }

    public static void setHori(int hori) {
        Board.hori = hori;
    }

    public static int getVert() {
        return vert;
    }

    public static void setVert(int vert) {
        Board.vert = vert;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    private static String[] pieceRedName = {"兵", "兵", "兵", "兵", "兵", "炮", "炮", "车", "车", "马", "马", "相", "相", "仕", "仕", "帅"};
    private static String[] pieceBlackName = {"卒", "卒", "卒", "卒", "卒", "炮", "炮", "车", "车", "马", "马", "象", "象", "士", "士", "将"};
    private static int[] pieceValue = {1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7};
    private static int hori;   //horizontal
    private static int vert;   //vertical
    private static List<Piece> pieceList;

    private static final Logger logger = LoggerFactory.getLogger(Board.class);
}
