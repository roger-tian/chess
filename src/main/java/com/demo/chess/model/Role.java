package com.demo.chess.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Role {
    public static ResultCode flip(Piece piece) {
        if (!piece.getNegative()) {
            return ResultCode.FLIPED;
        }

        piece.setNegative(false);

        return ResultCode.SUCCESS;
    }

    public static ResultCode move(Board board, Position from, Position to) {
        if ((from.getX() < 0) || (from.getX() > Board.getVert()) || (from.getY() < 0) || (from.getY() > Board.getHori())) {
            return ResultCode.INVALIDPOS;
        }

        if ((to.getX() < 0) || (to.getX() > Board.getVert()) || (to.getY() < 0) || (to.getY() > Board.getHori())) {
            return ResultCode.INVALIDPOS;
        }

        if ((from.getX() != to.getX()) && (from.getY() != to.getY())) {
            return ResultCode.NOTADJACENT;
        }

        int indexFrom = from.getX() * Board.getVert() + from.getY();
        Piece pieceFrom = board.getPieceList().get(indexFrom);
        if (pieceFrom.getNegative()) {
            return ResultCode.NOTFLIPED;
        }

        int indexTo = to.getX() * Board.getVert() + to.getY();
        Piece pieceTo = board.getPieceList().get(indexTo);
        if (pieceTo.getValue() != 0) {      // this position have a piece
            return ResultCode.HAVEPIECE;
        }

        pieceTo.setValue(pieceFrom.getValue());
        pieceTo.setName(pieceFrom.getName());
        pieceTo.setColor(pieceFrom.getColor());
        pieceTo.setNegative(pieceFrom.getNegative());

        pieceFrom.setValue(0);
        pieceFrom.setName("");
        pieceFrom.setColor("");
        pieceFrom.setNegative(false);

        return ResultCode.SUCCESS;
    }

    public static ResultCode exchange(Board board, Position pos1, Position pos2) {
        if ((pos1.getX() != pos2.getX()) && (pos1.getY() != pos2.getY())) {
            return ResultCode.NOTADJACENT;
        }

        int index1 = pos1.getX() * Board.getVert() + pos1.getY();
        Piece piece1 = board.getPieceList().get(index1);
        int index2 = pos2.getX() * Board.getVert() + pos2.getY();
        Piece piece2 = board.getPieceList().get(index2);

        if (piece1.getNegative() || piece2.getNegative()) {
            return ResultCode.NOTFLIPED;
        }

        if (piece1.getColor() == piece2.getColor()) {
            return ResultCode.SAMECOLOR;
        }

        if (piece1.getValue() != piece2.getValue()) {
            return ResultCode.DIFFRENTVALUE;
        }

        piece1.setValue(0);
        piece1.setName("");
        piece1.setColor("");
        piece1.setNegative(false);

        piece2.setValue(0);
        piece2.setName("");
        piece2.setColor("");
        piece2.setNegative(false);

        return ResultCode.SUCCESS;
    }

    public static ResultCode eat(Board board, Position pos1, Position pos2) {
        if ((pos1.getX() != pos2.getX()) && (pos1.getY() != pos2.getY())) {
            return ResultCode.NOTADJACENT;
        }

        int index1 = pos1.getX() * Board.getVert() + pos1.getY();
        Piece piece1 = board.getPieceList().get(index1);
        int index2 = pos2.getX() * Board.getVert() + pos2.getY();
        Piece piece2 = board.getPieceList().get(index2);

        if (piece1.getNegative()) {
            return ResultCode.NOTFLIPED;
        }

        if (!piece2.getNegative() && (piece1.getColor() == piece2.getColor())) {
            return ResultCode.SAMECOLOR;
        }

        if ((piece1.getValue() != 1) && (piece1.getValue() != 2)) {
            if (piece1.getValue() <= piece2.getValue()) {
                return ResultCode.NOMOREVALUE;
            }
        }

        switch (piece1.getValue()) {
            case 1:     // 兵, 卒
                break;
            case 2:     // 炮
                break;
            case 3:     // 车
                break;
            case 4:     // 马
                break;
            case 5:     // 相, 象
                break;
            case 6:     // 仕, 士
                break;
            case 7:     // 帅, 将
                break;
            default:
                break;
        }

        piece2.setValue(piece1.getValue());
        piece2.setName(piece1.getName());
        piece2.setColor(piece1.getColor());
        piece2.setNegative(piece1.getNegative());

        piece1.setValue(0);
        piece1.setName("");
        piece1.setColor("");
        piece1.setNegative(false);

        return ResultCode.SUCCESS;
    }

    private static final Logger logger = LoggerFactory.getLogger(Role.class);
}
