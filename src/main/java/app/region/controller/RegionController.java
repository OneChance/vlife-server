package app.region.controller;

import app.account.entity.Account;
import app.account.service.AccountService;
import app.base.JsonTool;
import app.base.NetMessage;
import app.region.entity.Region;
import app.region.entity.RegionTree;
import app.region.service.RegionService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.json.JsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class RegionController {

    @RequestMapping("/region")
    public NetMessage getLoginAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account account = accountService.getLoginAccount(request, response);
        NetMessage netMessage = new NetMessage();
        RegionTree rTree = regionService.getRegionTree(account);
        Region root = rTree.getRoot();
        root.setCurrentRegion(account.getRegion());
        String regionData = JsonTool.toString(root);
        regionData = "[" + regionData.replaceAll("name", "text").replaceAll("subRegions", "nodes").replaceAll(",\"nodes\":\\[\\]", "") + "]";
        netMessage.setContent(regionData);

        return netMessage;
    }


    @Resource
    AccountService accountService;
    @Resource
    RegionService regionService;
}
