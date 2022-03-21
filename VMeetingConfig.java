package net.by0119;

public interface VMeetingConfig {
    int S_WIDTH = 550;
    int S_HEIGHT = 600;
    int C_Dia_WEIGHT = 380;
    int C_Dia_HEIGHT = 600;
    int C_Dia_HEIGHT_Above = 355;
    int C_Dia_HEIGHT_Bottom = 175;

    int LeftBar_WEIGHT = 150;
    int LeftBar_HEIGHT = 600;

    int btnWidth = 50;
    int btnHeight = 5;

    int paintBoardWidth = 100;
    int paintBoardHeight = 100;
/**
 * 消息协议：
 * 自己的ID,
 * (name,)
 * 目标ID,
 * 日期,
 * 消息类型,    1:文字             2.涂鸦  随机跟随      10.    11.    12.        13.
 * 消息内容     文字包{长度，内容}   画板包               图片    视频    视频通话    文件
 */

}
