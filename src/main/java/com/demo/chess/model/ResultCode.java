package com.demo.chess.model;

public enum ResultCode {
    SUCCESS,            // 成功
    FAILED,             // 失败
    FLIPED,             // 已翻子(正面朝上)
    NOTFLIPED,          // 未翻子(背面朝上)
    INVALIDPOS,         // 无效坐标
    NOTADJACENT,        // 两棋子不相邻
    HAVEPIECE,          // 目标坐标已有棋子
    HAVENOPIECE,        // 目标坐标没有有棋子
    SAMECOLOR,          // 两棋子颜色相同
    DIFFRENTCOLOR,      // 两棋子颜色不同
    SAMEVALUE,          // 两棋子大小相同
    DIFFRENTVALUE,      // 两棋子大小不同
    NOMOREVALUE,        // 主动子的大小不大于被动子
}
