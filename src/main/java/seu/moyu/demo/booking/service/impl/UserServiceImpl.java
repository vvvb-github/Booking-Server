package seu.moyu.demo.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.mapper.UserMapper;
import seu.moyu.demo.booking.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    //过期时间
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "privateKey";

    @Override
    public User CheckUser(String email, String password) {
        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("email", email);
        wrapper.eq("password", password);
        List<User> users = list(wrapper);
        return users.get(0);
    }

    @Override
    public String GenerateToken(Integer userid) {
        // 设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
        // 返回token字符串
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userid)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    @Override
    public Integer AnalyzeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Integer userId = jwt.getClaim("userId").asInt();
        return userId;
    }

    @Override
    public Integer FindUser(String email){
        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("email", email);
        List<User> p = list(wrapper);
        if(p.isEmpty()) return 0;
        return 1;
    }

    @Override
    public Integer AddUser(String email, String userName, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setNickName(userName);
        user.setIconUrl("");
        user.setIdCardNumber("empty");
        user.setRealName("");
        user.setPhoneNumber("");
        save(user);
        return 1;
    }

    @Override
    public Integer ResetPassword(String email,String password){
        User user = new User();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",email);
        user.setPassword(password);
        update(user,wrapper);
        return 1;
    }

    @Override
    public void ChangeInformation(String token, String nickName, String phoneNumber, String realName, String idCardNumber) {
        User user = new User();
        Integer uuid = AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uuid",uuid);
        user.setNickName(nickName);
        user.setIdCardNumber(idCardNumber);
        user.setRealName(realName);
        user.setPhoneNumber(phoneNumber);
        update(user,wrapper);
    }
}
