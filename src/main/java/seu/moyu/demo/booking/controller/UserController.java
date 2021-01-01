package seu.moyu.demo.booking.controller;


import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import seu.moyu.demo.booking.service.IHotelService;
import seu.moyu.demo.booking.service.IOrderService;
import seu.moyu.demo.booking.service.IRoomService;
import seu.moyu.demo.booking.service.IUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@RestController
@RequestMapping("/booking/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IHotelService hotelService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject Login(JSONObject jsonObject){
        return jsonObject;
    }

}
