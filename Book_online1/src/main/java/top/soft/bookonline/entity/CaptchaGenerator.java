package top.soft.bookonline.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGenerator {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 50;
    private static final int CHAR_COUNT = 6;
    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateCaptcha() {
        StringBuilder captcha = new StringBuilder(CHAR_COUNT);
        Random random = new Random();
        for (int i = 0; i < CHAR_COUNT; i++) {
            int index = random.nextInt(CHAR_SET.length());
            captcha.append(CHAR_SET.charAt(index));
        }
        return captcha.toString();
    }

    public BufferedImage createImage(String captchaText) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 24));

        // Draw the captcha text
        for (int i = 0; i < captchaText.length(); i++) {
            graphics.drawString(String.valueOf(captchaText.charAt(i)), 30 * i + 10, 30);
        }

        // Add some noise
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            graphics.drawOval(x, y, 1, 1);
        }

        graphics.dispose();
        return image;
    }
}
