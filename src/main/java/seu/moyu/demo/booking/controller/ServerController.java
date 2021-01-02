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
import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.service.IHotelService;
import seu.moyu.demo.booking.service.IOrderService;
import seu.moyu.demo.booking.service.IRoomService;
import seu.moyu.demo.booking.service.IUserService;

import javax.jws.soap.SOAPBinding;
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
            jsonObject.put("msg", "用户名或密码错误！");
        }
        return jsonObject;
    }

}
