package com.wzf.slgtest.netty;


import android.util.Log;
import android.widget.Toast;

import com.douqu.game.core.protobuf.SGAreanProto;
import com.douqu.game.core.protobuf.SGBagProto;
import com.douqu.game.core.protobuf.SGCommonProto;
import com.douqu.game.core.protobuf.SGInstanceProto;
import com.douqu.game.core.protobuf.SGMainProto;
import com.douqu.game.core.protobuf.SGOfficialWarProto;
import com.douqu.game.core.protobuf.SGPlayerProto;
import com.douqu.game.core.protobuf.SGSystemProto;
import com.wzf.slgtest.MyApplication;
import com.wzf.slgtest.utils.BufferUtils;
import com.wzf.slgtest.utils.SendUtils;

import org.greenrobot.eventbus.EventBus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Administrator
 * @date 2016/11/9
 */
public class NettyTCPClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
       Log.e("NettyTCPClientHandler", cause.getLocalizedMessage());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int code = BufferUtils.readShort(byteBuf);

        Log.i("NettyTCPClientHandler","data length:" + byteBuf.readableBytes());
        Log.i("NettyTCPClientHandler","code:"+code);
        byte[] data = BufferUtils.byteBufToBytes(byteBuf);
        switch (code){
            /*************************系统****************************************/
            case SGMainProto.E_MSG_ID.MsgID_System_NotifyAlert_VALUE:
                EventBus.getDefault().post("警告:\n" + SGSystemProto.S2C_NotifyAlert.parseFrom(data).getContent());
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_FlushData_VALUE:
                SGPlayerProto.S2C_FlushData s2C_flushData = SGPlayerProto.S2C_FlushData.parseFrom(data);
                Log.i("flushData-->>",s2C_flushData.toString());
                EventBus.getDefault().post("刷新数据：\n" + s2C_flushData.toString());
                break;

            case SGMainProto.E_MSG_ID.MsgID_Player_FlushGoodsGet_VALUE:
//                SGPlayerProto.S2C_FlushGoodsGet s2C_flushGoodsGet = SGPlayerProto.S2C_FlushGoodsGet.parseFrom(data);
//                Log.i("sendGoodsGet-->>",s2C_flushGoodsGet.toString());
//                EventBus.getDefault().post("获得物品：\n" + s2C_flushGoodsGet.toString());
                break;
            case SGMainProto.E_MSG_ID.MsgID_System_Login_VALUE:
                SGSystemProto.S2C_Login s2C_login = SGSystemProto.S2C_Login.parseFrom(data);
                Log.i("NettyTCPClientHandler",s2C_login.toString());
                MyApplication.getAppInstance().objecIndex = s2C_login.getPlayerInfo().getPlayerIndex();
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_RedPointRemind_VALUE:
                SGPlayerProto.S2C_RedPointRemind response = SGPlayerProto.S2C_RedPointRemind.parseFrom(data);
                Log.i("redPoint-->>",response.toString());
                EventBus.getDefault().post("红点提示：\n" + response.toString());
                break;
            case SGMainProto.E_MSG_ID.MsgID_System_Regist_VALUE:
                break;
            /*************************个人信息****************************************/
            case SGMainProto.E_MSG_ID.MsgID_Player_EditPlayerName_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_EditPlayerName.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_EditPlayerAvatar_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_EditPlayerAvatar.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_ChangeConsumeRemindStatus_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ChangeConsumeRemindStatus.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_ChangeEquippedSkill_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ChangeEquippedSkill.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_SynBaseData_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_SynBaseData.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Player_SettingBoardInit_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_SettingBoardInit.parseFrom(data));
                break;
            /****************************************vip和福利***************************************/
            case SGMainProto.E_MSG_ID.MsgID_Bonus_RechargeInit_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_RechargeInit.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_Recharge_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_Recharge.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_BuyVipGiftBag_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_BuyVipGiftBag.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_ReceiveMouthCardReward_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ReceiveMouthCardReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_DailySignReward_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_DailySignReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_BuyFund_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_BuyFund.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_ReceiveFundReward_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ReceiveFundReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_BonusBoardInit_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_BonusBoardInit.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_ReceiveFirstRechargeReward_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ReceiveFirstRechargeReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_LoginTimesRewardInit_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_LoginTimesRewardInit.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bonus_ReceiveLoginTimesReward_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_ReceiveLoginTimesReward.parseFrom(data));
                break;
            /*************************副本****************************************/
            case  SGMainProto.E_MSG_ID.MsgID_Instance_GetInstanceInfo_VALUE:
                EventBus.getDefault().post(SGInstanceProto.S2C_GetInstanceInfo.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Instance_PassLevel_VALUE:
                EventBus.getDefault().post(SGInstanceProto.S2C_PassLevel.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Instance_ReceiveAward_VALUE:
                EventBus.getDefault().post(SGInstanceProto.S2C_ReceiveInstanceAward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Instance_ReceiveLevelBoxReward_VALUE:
                EventBus.getDefault().post(SGInstanceProto.S2C_ReceiveLevelBoxReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Instance_GetLastPassLevel_VALUE:
                EventBus.getDefault().post(SGInstanceProto.S2C_GetLastPassLevel.parseFrom(data));
                break;
            /*************************竞技场****************************************/
            case SGMainProto.E_MSG_ID.MsgID_Arena_GetArenaInfo_VALUE:
                 EventBus.getDefault().post(SGAreanProto.S2C_ArenaGetArenaInfo.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Arena_Challenge_VALUE:
                 EventBus.getDefault().post(SGAreanProto.S2C_ArenaChallenge.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Arena_GetDailyReward_VALUE:
                EventBus.getDefault().post(SGAreanProto.S2C_ArenaGetDailyReward.parseFrom(data));
                break;

            case SGMainProto.E_MSG_ID.MsgID_Arena_PreviewRank_VALUE:
                EventBus.getDefault().post(SGAreanProto.S2C_ArenaPreviewRank.parseFrom(data));
                break;

            case SGMainProto.E_MSG_ID.MsgID_Arena_RewardRecord_VALUE:
                EventBus.getDefault().post(SGAreanProto.S2C_ArenaRewardRecord.parseFrom(data));
                break;

            case SGMainProto.E_MSG_ID.MsgID_Arena_ExchangeReward_VALUE:
                EventBus.getDefault().post(SGAreanProto.S2C_ArenaExchangeReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Arena_Sweep_VALUE:
                EventBus.getDefault().post(SGAreanProto.S2C_ArenaSweep.parseFrom(data));
                break;
            /*************************官阶战****************************************/
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_InitInfo_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_InitInfo.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_PreviewRank_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_PreviewRank.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_ChallengeRank_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_ChallengeRank.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_SweepRank_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_SweepRank.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_ExchangeReward_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_ExchangeReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_RewardRecord_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_RewardRecord.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_GetDailyReward_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_GetDailyReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_IntegralReward_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_IntegralReward.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_OfficialWar_IntegralRewardRecord_VALUE:
                EventBus.getDefault().post(SGOfficialWarProto.S2C_IntegralRewardRecord.parseFrom(data));
                break;
            /*************************地精商店****************************************/
            case SGMainProto.E_MSG_ID.MsgID_Store_InitInfo_VALUE:
                EventBus.getDefault().post( SGPlayerProto.S2C_StoreInitInfo.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Store_BuyGoods_VALUE:
                EventBus.getDefault().post( SGPlayerProto.S2C_StoreBuyGoods.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_System_GMCmd_VALUE:
//                EventBus.getDefault().post( SGPlayerProto.S2C_StoreBuyGoods.parseFrom(data));
                break;

            case SGMainProto.E_MSG_ID.MsgID_Store_BuyTimes_VALUE:
                EventBus.getDefault().post( SGPlayerProto.S2C_StoreBuyTimes.parseFrom(data));
                break;
            /*************************背包****************************************/
            case SGMainProto.E_MSG_ID.MsgID_Bag_CardDetail_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_CardDetail.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bag_CardUpLv_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_CardUpLv.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bag_UseProp_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_UseProp.parseFrom(data));
                break;
            /*************************重生分解****************************************/
            case SGMainProto.E_MSG_ID.MsgID_Bag_RebirthCard_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_RebirthCard.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bag_ResolveCardPreview_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_ResolveCardPreview.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bag_SoulResolve_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_SoulResolve.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Bag_EquipResolve_VALUE:
                EventBus.getDefault().post(SGBagProto.S2C_EquipResolve.parseFrom(data));
                break;
            /*************************英雄圣殿****************************************/
            case SGMainProto.E_MSG_ID.MsgID_HeroTemple_InitInfo_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_HeroTempleInitInfo.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_HeroTemple_Challenge_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_HeroTempleChallenge.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_HeroTemple_Sweep_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_HeroTempleSweep.parseFrom(data));
                break;
            /*********************************祭祀***********************************************/
            case SGMainProto.E_MSG_ID.MsgID_Altar_Init_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_AltarInit.parseFrom(data));
                break;
            case SGMainProto.E_MSG_ID.MsgID_Altar_Sacrifice_VALUE:
                EventBus.getDefault().post(SGPlayerProto.S2C_Sacrifice.parseFrom(data));
                break;
            default:
                break;
        }


    }

    private void getArenaDailyReward(ChannelHandlerContext ctx) {
//        SendUtils.sendMsg(ctx.channel(), SGMainProto.E_MSG_ID.MsgID_Arena_GetDailyReward_VALUE, null);
    }

    private void arenaChallenge(ChannelHandlerContext ctx) {
//        SGInstanceProto.C2S_ArenaChallenge.Builder request = SGInstanceProto.C2S_ArenaChallenge.newBuilder();
//        request.setTargetRank(1);
//        SendUtils.sendMsg(ctx.channel(), SGMainProto.E_MSG_ID.MsgID_Arena_Challenge_VALUE, request.build().toByteArray());
    }


    private void passLevel(ChannelHandlerContext ctx) {
        byte []  data =  SGInstanceProto.C2S_PassLevel.newBuilder().
                        setChapterId(1)
                        .setLevelId(2)
                        .setStarts(4)
                        .build().toByteArray();
        SendUtils.sendMsg(SGMainProto.E_MSG_ID.MsgID_Instance_PassLevel_VALUE, data);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();// 将消息发送队列中的消息写入到SocketChannel中发送给对方。
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        MyApplication.getAppInstance().setCtx(ctx);
        login(ctx);
//        register(ctx);
//        login(ctx);
//        register(ctx);
//
//        SendUtils.sendMsg(ctx.channel(), SGMainProto.E_MSG_ID.MsgID_System_SuperLogin_VALUE, null);
//
//        ctx.close();
    }

    private void login(ChannelHandlerContext ctx) {
        SGSystemProto.C2S_Login.Builder request = SGSystemProto.C2S_Login.newBuilder();
        request.setChannel(SGCommonProto.E_CHANNEL_TYPE.CHANNEL_TYPE_QUICK);
        request.setNormal(true);
        request.setAccount(Config.ACCOUNT);

        SendUtils.sendMsg(SGMainProto.E_MSG_ID.MsgID_System_Login_VALUE, request.build().toByteArray());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Log.e("NettyTCPClientHandler", "服务器已关闭，我也要关服了！");
        ctx.close();
    }


}