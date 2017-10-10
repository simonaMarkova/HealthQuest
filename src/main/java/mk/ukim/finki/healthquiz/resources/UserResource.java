package mk.ukim.finki.healthquiz.resources;

import mk.ukim.finki.healthquiz.helper.FacebookLogin;
import mk.ukim.finki.healthquiz.helper.FacebookService;
import mk.ukim.finki.healthquiz.models.Level;
import mk.ukim.finki.healthquiz.helper.LoginInfo;
import mk.ukim.finki.healthquiz.models.User;
import mk.ukim.finki.healthquiz.persistance.UserQuestionRepository;
import mk.ukim.finki.healthquiz.service.LevelService;
import mk.ukim.finki.healthquiz.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Created by Simona on 7/18/2017.
 */
@RestController
@RequestMapping(value = "/user", produces = "application/json")
public class UserResource implements ApplicationContextAware {

    private final String fileUploadResource = "C:/Users/Simona/Documents/healthQuestPictures/profileImages/";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserService bean = applicationContext.getBean(UserService.class);
        System.out.println(bean);
    }

    private UserService userService;
    private LevelService levelService;
    private final UserQuestionRepository userQuestionRepository;

    @Autowired
    public UserResource(UserService userService, LevelService levelService, UserQuestionRepository userQuestionRepository) {
        this.userService = userService;
        this.levelService = levelService;
        this.userQuestionRepository = userQuestionRepository;
    }

    @Autowired
    FacebookService facebookService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user, HttpServletResponse response) {
        if (user != null) {
            User myUser = userService.findByEmail(user.getEmail());
            if (myUser != null) {
                response.setStatus(HttpStatus.CONFLICT.value());
                return null;
            } else {
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                Level l = levelService.findByLevel(1);
                user.setLevel(l);
                if(user.getProfileImage()!= null && !user.getProfileImage().equals("")){
                    byte[] imgbytes = Base64.getMimeDecoder().decode(user.getProfileImage());
                    try {
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imgbytes));
                        String destLocation;
                        if (!new File(fileUploadResource).exists()) {
                            new File(fileUploadResource).mkdirs();
                        }

                        String newNamePart1 = UUID.randomUUID().toString();
                        String newNamePart2 = UUID.randomUUID().toString();
                        String newName = String.format("%s-%s.jpg", newNamePart1, newNamePart2);
                        destLocation = String.format(fileUploadResource + newName);

                        File dest = new File(destLocation);
                        ImageIO.write(image, "jpg", dest);

                        user.setProfileImage(destLocation);

                    } catch (Exception exc) {

                    }

                }else{
                    user.setProfileImage(null);
                }

                return userService.save(user);
            }
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LoginInfo loginInfo, HttpServletResponse response) {
        User user = userService.findByEmail(loginInfo.getEmail());
        if (user != null) {
            if (user.getPassword() == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }
            if (BCrypt.checkpw(loginInfo.getPassword(), user.getPassword())) {
                return user;
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return null;
            }

        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<User> getAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(value = "/updateLevel/{userId}/{levelId}", method = RequestMethod.POST)
    public User updateLevelForUser(@PathVariable Long userId, @PathVariable Long levelId) {
        User user = userService.findById(userId);
        Level level = levelService.findById(levelId);
        int nextLevel = level.getLevel() + 1;
        Level l = levelService.findByLevel(nextLevel);
        user.setLevel(l);
        userService.save(user);
        return user;
    }

    @RequestMapping(value = "/facebook/login", method = RequestMethod.POST)
    public User facebookLogin(@RequestBody FacebookLogin facebookLogin, HttpServletResponse response) {

        if (facebookLogin.getAccessToken() != null) {
            org.springframework.social.facebook.api.User facebookUser = facebookService.getFacebookUser(facebookLogin.getAccessToken());
            User user = userService.findByEmail(facebookLogin.getUser().getEmail());
            if (user != null) {
                if (facebookLogin.getUser().getEmail().equals(facebookUser.getEmail())) {
                    if (user.getFacebookAccount() == null || !user.getFacebookAccount()) {
                        user.setFacebookAccount(true);
                    }
                    user = userService.save(user);
                    return user;
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return null;
                }
            } else {
                if (facebookLogin.getUser().getEmail().equals(facebookUser.getEmail())) {
                    User signUpUser = facebookLogin.getUser();
                    signUpUser.setFacebookAccount(true);
                    Level level = levelService.findByLevel(1);
                    signUpUser.setLevel(level);
                    if(signUpUser.getProfileImage()!=null){
                        byte[] imgbytes = Base64.getMimeDecoder().decode(signUpUser.getProfileImage());
                        try {
                            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imgbytes));
                            String destLocation;
                            if (!new File(fileUploadResource).exists()) {
                                new File(fileUploadResource).mkdirs();
                            }

                            String newNamePart1 = UUID.randomUUID().toString();
                            String newNamePart2 = UUID.randomUUID().toString();
                            String newName = String.format("%s-%s.jpg", newNamePart1, newNamePart2);
                            destLocation = String.format(fileUploadResource + newName);

                            File dest = new File(destLocation);
                            ImageIO.write(image, "jpg", dest);

                            signUpUser.setProfileImage(destLocation);

                        } catch (Exception exc) {

                        }
                    }
                    signUpUser = userService.save(signUpUser);
                    return signUpUser;
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return null;
                }
            }
        }else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageByQuestionId(@PathVariable Long id, HttpServletResponse response) throws IOException, SQLException {

        User user = userService.findById(id);
        if(user!=null && user.getProfileImage()!=null)
        {
            InputStream in = null;
            OutputStream out = null;

            File file = new File(user.getProfileImage());
                String contentDisposition = String.format("inline;filename=\"%s\"",
                        file.getName() + "?userId=" + id);
                byte fileContent[] = new byte[(int)file.length()];

                try {

                in = new FileInputStream(file);
                out = response.getOutputStream();
                in.read(fileContent);

                ByteArrayInputStream bin = new ByteArrayInputStream(fileContent);
                response.setHeader("Content-Disposition", contentDisposition);
                response.setContentType(Files.probeContentType(file.toPath()));
                response.setContentLength((int) file.length());

                IOUtils.copy(bin, out);

            }
            catch (IOException e) {

            }
            finally {
                if(in!=null) {
                    in.close();
                }
                if(out!=null)
                {
                    out.flush();
                    out.close();
                }
            }
        }


    }

    @RequestMapping(value = "/update-photo", method = RequestMethod.POST)
    public void updatePhoto(@RequestBody User user, HttpServletResponse response) throws IOException, SQLException {

        User myUser = userService.findById(user.id);
        if(myUser!=null && user.getProfileImage() != null && !user.getProfileImage().equals("")) {
            if(myUser.getProfileImage()!=null ) {
                File prev = new File(myUser.getProfileImage());
                prev.delete();
            }

            byte[] imgbytes = Base64.getMimeDecoder().decode(user.getProfileImage());
            try {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imgbytes));
                String destLocation;
                if (!new File(fileUploadResource).exists()) {
                    new File(fileUploadResource).mkdirs();
                }

                String newNamePart1 = UUID.randomUUID().toString();
                String newNamePart2 = UUID.randomUUID().toString();
                String newName = String.format("%s-%s.jpg", newNamePart1, newNamePart2);
                destLocation = String.format(fileUploadResource + newName);

                File dest = new File(destLocation);
                ImageIO.write(bufferedImage, "jpg", dest);

                myUser.setProfileImage(destLocation);
                userService.save(myUser);

            } catch (Exception exc) {

            }

        }


    }


    @RequestMapping(value = "/points/{id}", method = RequestMethod.GET)
    public Integer getPoints(@PathVariable Long id) {
        User user = userService.findById(id);
        return user.getPoints();
    }


}
