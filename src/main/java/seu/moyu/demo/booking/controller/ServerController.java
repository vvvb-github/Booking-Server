package seu.moyu.demo.booking.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.sf.json.JSONObject;
import net.sf.json.groovy.GJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import seu.moyu.demo.booking.entity.Hotel;
import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.entity.Room;
import seu.moyu.demo.booking.entity.Order;
import seu.moyu.demo.booking.service.IHotelService;
import seu.moyu.demo.booking.service.IOrderService;
import seu.moyu.demo.booking.service.IRoomService;
import seu.moyu.demo.booking.service.IUserService;

import java.io.File;
import java.util.Collections;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;
import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.sql.Wrapper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@RestController
@CrossOrigin(origins = "*",allowCredentials = "true")
public class ServerController {

    private String IP = "http://64.64.228.191";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public JSONObject Login(String email, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            User user = userService.CheckUser(email, password);
            String token = userService.GenerateToken(user.getUuid());
            jsonObject.put("status", 200);
            jsonObject.put("token", token);
            jsonObject.put("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "用户名或密码错误!");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public JSONObject Register(String userName,String password,String email){
        JSONObject jsonObject = new JSONObject();
        try {
            if (userService.FindUser(email) == 0) {
                userService.AddUser(email,userName,password);
                jsonObject.put("status", 200);
            }
            else {
                jsonObject.put("status", 404);
                jsonObject.put("msg", "用户已存在!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg","未知错误!");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public JSONObject Reset(String email,String password){
        JSONObject jsonObject = new JSONObject();
        try{
            userService.ResetPassword(email,password);
            jsonObject.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "修改失败！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public JSONObject Edit(String token,String nickName,String phoneNumber,String realName,String idCardNumber){
        JSONObject jsonObject = new JSONObject();
        try{
            userService.ChangeInformation(token,nickName,phoneNumber,realName,idCardNumber);
            jsonObject.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "修改失败！");
        }
        return jsonObject;
    }
    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    public JSONObject Rate(String token,int star,int orderID){
        JSONObject jsonObject = new JSONObject();
        try{
            int res = orderService.RateOrder(token,star,orderID);
            if(res == -1){
                jsonObject.put("status", 501);
                jsonObject.put("msg", "订单信息错误，请刷新！");
            }
            else if(res==-2){
                jsonObject.put("status", 502);
                jsonObject.put("msg", "该订单不能评价！");
            }
            else{
                jsonObject.put("status", 200);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "登录失效，请刷新！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public JSONObject Cancel(String token,int orderId){
        JSONObject jsonObject = new JSONObject();
        try{
            int res = orderService.CancelOrder(token,orderId);
            if(res == -1){
                jsonObject.put("status", 501);
                jsonObject.put("msg", "订单信息错误，请刷新！");
            }
            else if(res==-2){
                jsonObject.put("status", 502);
                jsonObject.put("msg", "该订单不能退订！");
            }
            else{
                jsonObject.put("status", 200);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "登录失效，请刷新！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public JSONObject Search(String parameter, String startTime, String endTime, int customerNumber,String type, String location) {
        JSONObject jsonObject = new JSONObject();
        try{
            System.out.println("yes");
            List<Hotel> res = hotelService.Search(parameter,location);
            Collections.shuffle(res);
            jsonObject.put("status", 200);
            jsonObject.put("hotelList", res);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "登录失效，请刷新！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public JSONObject Hotel(Integer hotelId) {
        JSONObject jsonObject = new JSONObject();
        try{
            Hotel hotel = hotelService.FindHotel(hotelId);
            String[] pic = hotel.getPictureUrl().split(",");
            List<String> pictureList = new ArrayList<>();
            for(int i = 0; i < pic.length; ++i){
                pictureList.add(pic[i]);
            }
            String[] rooms = hotel.getRoom().split(",");
            List<Integer> roomList = new ArrayList<>();
            for(int i = 0; i < rooms.length; ++i){
                roomList.add(Integer.parseInt(rooms[i]));
            }
            jsonObject.put("hotelName",hotel.getHotelName());
            jsonObject.put("telNumber",hotel.getPhone());
            jsonObject.put("hotelIntro",hotel.getIntroduction());
            jsonObject.put("location",hotel.getLocation());
            jsonObject.put("locationDetail","");
            jsonObject.put("star",hotel.getStars());
            jsonObject.put("pictureList",pictureList);
            jsonObject.put("roomList",roomList);
            jsonObject.put("features","");
            for(int i = 0; i < roomList.size(); ++i) {
                Room room = roomService.FindRoom(roomList.get(i));
                jsonObject.put("roomId", room.getUuid());
                jsonObject.put("roomPic", room.getPictureUrl());
                jsonObject.put("roomName", room.getTitle());
                jsonObject.put("roomIntro", room.getIntroduction());
                jsonObject.put("roomPrice", room.getPrice());
                jsonObject.put("roomCapacity", room.getPeopleNumber());
            }
            jsonObject.put("status",200);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "获取失败！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/reserve",method = RequestMethod.POST)
    public JSONObject Reserve(
            @RequestBody JSONObject params) {
        System.out.println(params);
        Date startTime = (Date) params.get("startTime");
        System.out.println(startTime);
        JSONObject jsonObject = new JSONObject();
//        try{
//            int res = orderService.ReserveHotel(token,hotelId,roomId,price,startTime,endTime,customerNumber);
//            jsonObject.put("status",200);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            jsonObject.put("status", 500);
//            jsonObject.put("msg", "登录失效，请刷新！");
//        }
        return jsonObject;
    }

    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public JSONObject Token(String token){
        JSONObject jsonObject = new JSONObject();
        try{
            if(token.equals("")){
                jsonObject.put("status", 500);
                jsonObject.put("state",false);
                jsonObject.put("msg","未登录");
            }
            else{
                jsonObject.put("state",true);
                jsonObject.put("status", 200);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            jsonObject.put("status", 502);
            jsonObject.put("msg","未知错误");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public JSONObject Orders(String  token) {
        JSONObject jsonObject = new JSONObject();
        try{
            List<Order> order = orderService.FindOrder(token);
            for(int i = 0;i < order.size();++i){
                Order newOrder = order.get(i);
                jsonObject.put("orderId", newOrder.getUuid());
                int roomId = newOrder.getRoomId();
                int hotelId = newOrder.getHotelId();
                Room room = roomService.FindRoom(roomId);
                Hotel hotel = hotelService.FindHotel(hotelId);
                jsonObject.put("pictureUrl", room.getPictureUrl());
                jsonObject.put("hotelName", hotel.getHotelName());
                jsonObject.put("startTime", newOrder.getStartDate());
                jsonObject.put("endTime", newOrder.getEndDate());
                jsonObject.put("customerNumber", newOrder.getCustomerNumber());
                jsonObject.put("price", newOrder.getPrice());
                jsonObject.put("title", room.getTitle());
                jsonObject.put("state", newOrder.getState());
                jsonObject.put("star", newOrder.getStar());
                jsonObject.put("hotelId", hotelId);
                jsonObject.put("roomId",roomId);
            }
            jsonObject.put("status",200);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "订单获取失败，请刷新！");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/iconEdit",method = RequestMethod.POST)
    public JSONObject IconEdit(String token,@RequestParam("file") MultipartFile multipartFile){
        JSONObject jsonObject = new JSONObject();
        try {
            if(token.equals("")){
                jsonObject.put("status",500);
                jsonObject.put("msg","上传失败");
                return jsonObject;
            }
            String serverPath = System.getProperty("user.dir") + "/images/";
            String uuid = UUID.randomUUID()	.toString();
            String suffixName = multipartFile.getOriginalFilename().
                    substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String path = serverPath + uuid + suffixName;
            File newFile = new File(path);
            multipartFile.transferTo(newFile);
            path = IP + "/images/" + uuid + suffixName;
            jsonObject.put("status",1);
            jsonObject.put("url",path);
        }catch (Exception e){
            jsonObject.put("status",0);
            jsonObject.put("msg","上传失败");
        }
        return jsonObject;
    }

    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public JSONObject Code(String email, String code) {
        JSONObject jsonObject = new JSONObject();
        try{
            System.out.println("yes");
            userService.sendSimpleMail(email,"摸鱼大队验证码",code);
            jsonObject.put("status",200);
            System.out.println("shizheli");
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "发送失败");
        }
        return jsonObject;
    }
}
