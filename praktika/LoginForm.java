/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package praktika;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author user
 */
public class LoginForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginForm.class.getName());
    
    // Компоненты для изображений
    private JLabel flagLabel;
    private JLabel courtIconLabel;
    private JLabel ghostLabel;
    private JLabel pumpkinLabel;
    private JLabel pumpkin1Label;
    private JLabel galochkaLabel;
    private JLabel ghost1Label;
    private JLabel messageLabel;
    private JLabel mosgorsudLabel; // Новый компонент для мосгорсуд.jpg
    
    // Анимационные переменные
    private Timer animationTimer;
    private Timer autoAnimationTimer;
    private int animationY = 0;
    private boolean animationActive = false;

    public LoginForm() {
        initComponents();
        initCustomComponents();
    }
    
    private void initCustomComponents() {
        // Устанавливаем абсолютное позиционирование
        getContentPane().setLayout(null);
        
        // Перемещаем существующие компоненты на правильные позиции
        jLabel1.setBounds(150, 20, 200, 25);
        buttonClose.setBounds(450, 10, 40, 25);
        jLabel10.setBounds(150, 70, 100, 20);
        textFieldLogin.setBounds(150, 95, 200, 25);
        jLabel9.setBounds(150, 130, 100, 20);
        passwordField.setBounds(150, 155, 200, 25);
        buttonEnter.setBounds(150, 200, 200, 40);
        
        // Инициализируем изображения
        initImageComponents();
        
        // Добавляем обработчики событий
        setupEventHandlers();
        
        // Устанавливаем размер окна
        setSize(500, 400);
        setLocationRelativeTo(null);
    }
    
    private void initImageComponents() {
        // Флаг в левом верхнем углу
        flagLabel = createImageLabel("flag.png", "Флаг", 10, 10, 40, 40);
        getContentPane().add(flagLabel);
        
        // Иконка суда под флагом
        courtIconLabel = createImageLabel("court_icon.png", "Суд", 15, 60, 30, 30);
        getContentPane().add(courtIconLabel);
        
        // Призрак справа от флага
        ghostLabel = createImageLabel("ghost.png", "Призрак", 60, 10, 40, 40);
        getContentPane().add(ghostLabel);
        
        // Тыква в левом нижнем углу
        pumpkinLabel = createImageLabel("pumpkin.png", "Тыква", 10, 300, 40, 40);
        getContentPane().add(pumpkinLabel);
        
        // Тыква в правом нижнем углу
        pumpkin1Label = createImageLabel("pumpkin1.png", "Тыква", 430, 300, 40, 40);
        getContentPane().add(pumpkin1Label);
        
        // Галочка (скрыта изначально)
        galochkaLabel = createImageLabel("galochka.png", "✓", 200, 250, 50, 50);
        galochkaLabel.setVisible(false);
        getContentPane().add(galochkaLabel);
        
        // Призрак1 (скрыт изначально)
        ghost1Label = createImageLabel("ghost1.png", "👻", 200, 250, 50, 50);
        ghost1Label.setVisible(false);
        getContentPane().add(ghost1Label);
        
        // Метка для сообщения (скрыта изначально)
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setBounds(50, 310, 400, 30);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setVisible(false);
        getContentPane().add(messageLabel);
        
        // Новое изображение мосгорсуд (скрыто изначально)
        mosgorsudLabel = createImageLabel("мосгорсуд.jpg", "Мосгорсуд", 180, 150, 120, 80);
        mosgorsudLabel.setVisible(false);
        getContentPane().add(mosgorsudLabel);
    }
    
    private JLabel createImageLabel(String imageName, String fallbackText, int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        
        try {
            // Пытаемся загрузить изображение разными способами
            Image image = loadImage(imageName);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                System.out.println("Успешно загружено: " + imageName);
            } else {
                throw new Exception("Image not found");
            }
        } catch (Exception e) {
            // Если изображение не найдено, создаем цветной квадрат с текстом
            label.setText(fallbackText);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(getColorForImage(imageName));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            System.out.println("Создана замена для: " + imageName);
        }
        
        return label;
    }
    
    private Color getColorForImage(String imageName) {
        switch (imageName) {
            case "flag.png": return Color.RED;
            case "court_icon.png": return Color.BLUE;
            case "ghost.png": return Color.WHITE;
            case "pumpkin.png": 
            case "pumpkin1.png": return Color.ORANGE;
            case "galochka.png": return Color.GREEN;
            case "ghost1.png": return Color.LIGHT_GRAY;
            case "мосгорсуд.jpg": return Color.YELLOW; // Цвет для мосгорсуда
            default: return Color.GRAY;
        }
    }
    
    private Image loadImage(String imageName) {
        try {
            // Пробуем загрузить из classpath
            java.net.URL imageURL = getClass().getResource("/images/" + imageName);
            if (imageURL != null) {
                return ImageIO.read(imageURL);
            }
            
            // Пробуем загрузить из папки images
            java.io.File imageFile = new java.io.File("images/" + imageName);
            if (imageFile.exists()) {
                return ImageIO.read(imageFile);
            }
            
            // Пробуем загрузить из корня проекта
            imageFile = new java.io.File(imageName);
            if (imageFile.exists()) {
                return ImageIO.read(imageFile);
            }
            
        } catch (IOException e) {
            System.out.println("Ошибка загрузки " + imageName + ": " + e.getMessage());
        }
        return null;
    }
    
    private void setupEventHandlers() {
        // Обработчик кнопки входа
        buttonEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authenticateUser();
            }
        });
        
        // Обработчик кнопки закрытия
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        
        // Убрали обработчик мыши для автоматической анимации
    }
    
    private void authenticateUser() {
        String login = textFieldLogin.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        // Проверяем на текст-подсказки
        if ("Введите логин".equals(login)) login = "";
        if ("jPasswordField1".equals(password)) password = "";
        
        if (login.isEmpty() || password.isEmpty()) {
            showMessage(false, "Поля не могут быть пустыми!");
            return;
        }
        
        // Временная заглушка для тестирования
        if ("admin".equals(login) && "12345".equals(password)) {
            showMessage(true, "Поздравляем! Добро пожаловать в приложение Верховного суда Москвы");
        } else if ("user".equals(login) && "user".equals(password)) {
            showMessage(true, "Поздравляем! Добро пожаловать в приложение Верховного суда Москвы");
        } else {
            showMessage(false, "Вас нет в базе данных суда!");
        }
        
        // Раскомментируйте для работы с реальной базой данных
        /*
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/curt";
            String user = "root";
            String pass = "";
            
            conn = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM court WHERE login = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                showMessage(true, "Поздравляем! Добро пожаловать в приложение Верховного суда Москвы");
            } else {
                showMessage(false, "Вас нет в базе данных суда!");
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            showMessage(false, "Ошибка подключения к базе данных: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        */
    }
    
    private void showMessage(boolean success, String message) {
        // Скрываем предыдущие сообщения и изображение мосгорсуд
        galochkaLabel.setVisible(false);
        ghost1Label.setVisible(false);
        messageLabel.setVisible(false);
        mosgorsudLabel.setVisible(false); // Скрываем мосгорсуд при новом сообщении
        
        // Останавливаем предыдущие таймеры
        if (autoAnimationTimer != null && autoAnimationTimer.isRunning()) {
            autoAnimationTimer.stop();
        }
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        // Показываем соответствующие элементы
        if (success) {
            galochkaLabel.setVisible(true);
            messageLabel.setForeground(Color.GREEN);
        } else {
            ghost1Label.setVisible(true);
            messageLabel.setForeground(Color.RED);
        }
        
        messageLabel.setText(message);
        messageLabel.setVisible(true);
        
        // Сбрасываем позиции для анимации
        galochkaLabel.setLocation(200, 250);
        ghost1Label.setLocation(200, 250);
        messageLabel.setLocation(50, 310);
        
        animationY = 0;
        animationActive = true;
        
        // Запускаем автоматическую анимацию через 5 секунд
        startAutoAnimationTimer();
    }
    
    private void startAutoAnimationTimer() {
        autoAnimationTimer = new Timer(5000, new ActionListener() { // 5000 ms = 5 секунд
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Автоматическая анимация запущена через 5 секунд");
                startAnimation();
                autoAnimationTimer.stop();
            }
        });
        autoAnimationTimer.setRepeats(false); // Только один раз
        autoAnimationTimer.start();
    }
    
    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        animationTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationY += 5;
                
                if (galochkaLabel.isVisible()) {
                    galochkaLabel.setLocation(galochkaLabel.getX(), galochkaLabel.getY() + 5);
                }
                if (ghost1Label.isVisible()) {
                    ghost1Label.setLocation(ghost1Label.getX(), ghost1Label.getY() + 5);
                }
                if (messageLabel.isVisible()) {
                    messageLabel.setLocation(messageLabel.getX(), messageLabel.getY() + 5);
                }
                
                // Если элементы ушли за пределы экрана, скрываем их
                if (animationY > 300) {
                    galochkaLabel.setVisible(false);
                    ghost1Label.setVisible(false);
                    messageLabel.setVisible(false);
                    animationActive = false;
                    animationTimer.stop();
                    System.out.println("Анимация завершена");
                    
                    // ПОСЛЕ ЗАВЕРШЕНИЯ АНИМАЦИИ С ПРИЗРАКОМ - ПОКАЗЫВАЕМ МОСГОРСУД
                    if (!ghost1Label.isVisible() && messageLabel.getForeground() == Color.RED) {
                        showMosgorsudImage();
                    }
                }
            }
        });
        
        animationTimer.start();
    }
    
    // НОВЫЙ МЕТОД ДЛЯ ПОКАЗА ИЗОБРАЖЕНИЯ МОСГОРСУД
    private void showMosgorsudImage() {
        System.out.println("Показываем изображение мосгорсуд.jpg");
        
        // Показываем изображение мосгорсуд
        mosgorsudLabel.setVisible(true);
        mosgorsudLabel.setLocation(180, 150); // Центрируем
        
        // Автоматически скрываем изображение через 3 секунды
        Timer hideTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mosgorsudLabel.setVisible(false);
                System.out.println("Изображение мосгорсуд скрыто");
            }
        });
        hideTimer.setRepeats(false);
        hideTimer.start();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        textFieldLogin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        buttonEnter = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        buttonClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel11.setText("Введите пароль");
        jLabel11.setToolTipText("");

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(java.awt.Color.orange);

        jLabel9.setText("Введите пароль");
        jLabel9.setToolTipText("");

        textFieldLogin.setText("Введите логин");

        jLabel10.setText("Введите логин");
        jLabel10.setToolTipText("");

        buttonEnter.setText("ВХОД");

        passwordField.setText("jPasswordField1");

        buttonClose.setText("X");
        buttonClose.setToolTipText("");

        jLabel1.setText("Верховный суд Москвы");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(142, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(123, 123, 123)
                        .addComponent(buttonClose)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(textFieldLogin, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(passwordField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(119, 119, 119))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonClose)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(buttonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// </editor-fold>
        public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonEnter;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField textFieldLogin;
    // End of variables declaration//GEN-END:variables
}