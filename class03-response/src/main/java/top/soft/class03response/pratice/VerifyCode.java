package top.soft.class03response.pratice;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.soft.class03response.HelloServlet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/9/28 16:07
 */
@WebServlet("/verifyCode")
public class VerifyCode extends HelloServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int width = 260;
        int height = 145;

        //创建验证码图片对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //2
        Graphics g = image.getGraphics();

        g.setColor(Color.blue);
        g.fillRect(0, 0, width, height);
        String str="1234567809ADSGKHvbyfz";
//生成随机角标
        Random random = new Random();
        for (int i = 0; i <= 5; i++) {
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g.setColor(color);
            Font font = new Font("Dialog", Font.BOLD, 20);
            g.drawString(String.valueOf(c), width/5*i, height/2);
        }
//        设置噪点
        Random random2 = new Random();
        for (int i = 0; i <= 35; i++) {
            int x = random2.nextInt(width);
            int y = random2.nextInt(height);
            g.setColor(Color.black);
            g.drawRect(x, y, 5,5 );
        }
//        设置干扰线
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            g.setColor(color.BLUE);
            g.drawLine(x1, y1, x2, y2);
        }

        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}