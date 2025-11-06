package praktika;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Praktika extends JFrame {
    
    private static Praktika judge;
    private static Image ghost;
    private static Image ghost1;
    private static Image pumkin;
    private static Image pumkin1;
    private static Image flag;
    private static Image court_icon;
    private static Image galochka;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static long last_frame_time;
    private static float drop_v = 200;
    private static int end = 10;
    private static int score = 10;
         

    public static void main(String args[]) {
        ghost1 = ImageIO.read(Praktika.class.getResourceAsStream(ghost1));
        judge = new Praktika();
        judge.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        judge.setLocation(200,50);
        judge.setSize(900, 600);
        judge.setResizable(false);
        JudgeField judge_field = new JudgeField();
        judge.add(judge_field);
        judge.setVisible(true);
        last_frame_time = System.nanoTime();
        
    }

private static void onRepaint(Graphics g) {
    long current_time = System.nanoTime();
    float delta_time = (current_time - last_frame_time) * 0.000000001f;
    last_frame_time = current_time;
    
    g.drawImage(judge, 0, 0, null);
    g.drawImage(ghost1, (int) drop_left, (int) drop_top, null);
    
    if(drop_top > flag.getHeight()) {
        g.drawImage(end, 210, 150, null);
    }
}
    public static class JudgeField extends JPanel {
    @Override
    public void mousePressed(MouseEvent e) {
        judge.addMouseListener(new MouseAdapter(){
        int x = e.getX();
        int y = e.getY();
        float drop_right = drop_left + ghost1.getWidth(null);
        float drop_bottom = drop_top + ghost1.getHeight(null);
        boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
        if (is_drop) {
            drop_top = -100;
            drop_left = (int) (Math.random() * ( judge.getWidth() - ghost1.getWidth(null)));
            drop_v = drop_v + 10;
            score++; 
            judge.setTitle("Score: " + score);
        if (is_drop) {
        drop_top = -100;
        drop_left = (int) (Math.random() * (judge_field.getWidth() - ghost1.getWidth(null)));
        drop_v = drop_v + 10;
        score++;
        judge.setTitle("Score: " + score);
}
});
}