package cc.bitky.clustermanage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import cc.bitky.clustermanage.db.bean.Device;
import cc.bitky.clustermanage.server.bean.ServerWebMessageHandler;
import cc.bitky.clustermanage.server.message.CardType;
import cc.bitky.clustermanage.web.bean.QueueInfo;

/**
 * 设备信息获取及处理控制器
 */
@RestController
@RequestMapping(value = "/info")
public class InfoRestController {

    private final ServerWebMessageHandler serverWebMessageHandler;

    @Autowired
    public InfoRestController(ServerWebMessageHandler serverWebMessageHandler) {
        this.serverWebMessageHandler = serverWebMessageHandler;
    }

    /**
     * 从数据库中获取设备的集合
     *
     * @param groupId  设备组 ID
     * @param deviceId 设备 ID
     * @return 设备集合
     */
    @RequestMapping(value = "/devices/{groupId}/{deviceId}", method = RequestMethod.GET)
    public List<Device> getDevices(@PathVariable int groupId, @PathVariable int deviceId) {
        if (groupId > 120) return new ArrayList<>();
        return serverWebMessageHandler.queryDeviceInfo(groupId, deviceId);
    }

    /**
     * 从数据库中获取正在活动的设备组
     *
     * @return 设备集合
     */
    @RequestMapping(value = "/devicegroup/activated", method = RequestMethod.GET)
    public List<Integer> getDeviceGroupActivated() {
        return serverWebMessageHandler.getDeviceGroupActivated();
    }


    /**
     * 从数据库中获取万能卡号的集合
     *
     * @return 万能卡号的集合
     */
    @RequestMapping(value = "/freecard", method = RequestMethod.GET)
    public String[] obtainFreeCards() {
        return serverWebMessageHandler.obtainFreeCards();
    }

    /**
     * 从数据库中获取确认卡号的集合
     *
     * @return 确认卡号的集合
     */
    @RequestMapping(value = "/confirmcard", method = RequestMethod.GET)
    public String[] obtainConfirmCard() {
        return serverWebMessageHandler.obtainConfirmCards();
    }

    /**
     * 保存确认卡号到数据库
     *
     * @param confirmCards 确认卡号数组
     * @return @return "保存确认卡号成功"消息
     */
    @RequestMapping(value = "/confirmcard", method = RequestMethod.POST, consumes = "application/json")
    public String saveConfirmCard(@RequestBody String[] confirmCards) {
        if (serverWebMessageHandler.saveCardNumber(confirmCards, CardType.CONFIRM))
            return "success";
        return "error";
    }

    /**
     * 获取服务器状态
     *
     * @return 服务器状态
     */
    @RequestMapping(value = "/server/status", method = RequestMethod.GET)
    public String obtainServerStatus() {
        return "success";
    }

    @RequestMapping(value = "/queueframe", method = RequestMethod.GET)
    public QueueInfo obtainQueueFrame() {
        return serverWebMessageHandler.obtainQueueFrame();
    }
}
