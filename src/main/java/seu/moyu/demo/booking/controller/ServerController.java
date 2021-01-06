package seu.moyu.demo.booking.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.sf.json.JSONObject;
import net.sf.json.groovy.GJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import seu.moyu.demo.booking.entity.Hotel;
import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.service.IHotelService;
import seu.moyu.demo.booking.service.IOrderService;
import seu.moyu.demo.booking.service.IRoomService;
import seu.moyu.demo.booking.service.IUserService;
import seu.moyu.demo.booking.service.impl.OrderServiceImpl;

import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@RestController
public class ServerController {

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
    public JSONObject Cancel(String token,int orderID){
        JSONObject jsonObject = new JSONObject();
        try{
            int res = orderService.CancelOrder(token,orderID);
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
    public JSONObject Search(String parameter, Date startTime, Date endTime, int customerNumber,String type, String location) {
        JSONObject jsonObject = new JSONObject();
        try{
            List<Hotel> res = hotelService.Search(parameter);
            jsonObject.put("status", 200);
            jsonObject.put("List<Hotel>", res);
        }
        catch(Exception e){
            e.printStackTrace();
            jsonObject.put("status", 500);
            jsonObject.put("msg", "登录失效，请刷新！");
        }
        return jsonObject;
    }

}
