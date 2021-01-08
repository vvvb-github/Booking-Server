package seu.moyu.demo.booking.service;

import seu.moyu.demo.booking.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
public interface IUserService extends IService<User> {

    public User CheckUser(String email, String password);

    public String GenerateToken(Integer userid);

    public Integer AnalyzeToken(String token);

    public Integer FindUser(String email);

    public Integer AddUser(String email,String userName,String password);

    public Integer ResetPassword(String email,String password);

    public void ChangeInformation(String token,String nickName,String phoneNumber,String realName,String idCardNumber);

    public void sendSimpleMail(String to, String subject, String content);

    public void SetIcon(Integer uuid, String path);
}
