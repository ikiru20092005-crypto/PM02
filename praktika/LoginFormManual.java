package praktika;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class LoginFormManual extends JFrame {
    
    // Компоненты формы
    private JButton buttonEnter;
    private JButton buttonClose;
    private JPasswordField passwordField;
    private JTextField textFieldLogin;
    private JLabel titleLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    
    // Компоненты для изображений и анимации
    private JLabel galochkaLabel;
    private JLabel ghost1Label;
    private JLabel messageLabel;
    private JLabel flagLabel;
    private JLabel courtIconLabel;
    private JLabel ghostLabel;
    private JLabel pumpkinLabel;
    private JLabel pumpkin1Label;
    private Timer animationTimer;
    private int animationY = 0;

    public LoginFormManual() {
        initComponents();
    }
    
    private void initComponents() {
        // Настройка главного окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Верховный суд Москвы");
        setSize(500, 400);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);

        // Создаем и настраиваем компоненты
        initMainComponents();
        initImageComponents();
        setupEventHandlers();
    }
    
    private void initMainComponents() {
        titleLabel = new JLabel("Верховный суд Москвы");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(150, 20, 200, 25);
        add(titleLabel);

        buttonClose = new JButton("X");
        buttonClose.setBounds(450, 10, 40, 25);
        buttonClose.setBackground(Color.RED);
        buttonClose.setForeground(Color.WHITE);
        add(buttonClose);
        loginLabel = new JLabel("Введите логин:");
        loginLabel.setBounds(150, 70, 100, 20);
        add(loginLabel);

        textFieldLogin = new JTextField();
        textFieldLogin.setBounds(150, 95, 200, 25);
        add(textFieldLogin);

        passwordLabel = new JLabel("Введите пароль:");
        passwordLabel.setBounds(150, 130, 100, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 155, 200, 25);
        add(passwordField);
        buttonEnter = new JButton("ВХОД");
        buttonEnter.setBounds(150, 200, 200, 40);
        buttonEnter.setBackground(new Color(0, 100, 0));
        buttonEnter.setForeground(Color.WHITE);
        buttonEnter.setFont(new Font("Arial", Font.BOLD, 14));
        add(buttonEnter);
    }
    
    private void initImageComponents() {

        flagLabel = createImageLabel("flag.png", "Флаг", 10, 10, 40, 40);
        add(flagLabel);
        
        courtIconLabel = createImageLabel("court_icon.png", "Суд", 15, 60, 30, 30);
        add(courtIconLabel);
        
        ghostLabel = createImageLabel("ghost.png", "Призрак", 60, 10, 40, 40);
        add(ghostLabel);
        
        pumpkinLabel = createImageLabel("pumpkin.png", "Тыква", 10, 300, 40, 40);
        add(pumpkinLabel);
        
        pumpkin1Label = createImageLabel("pumpkin1.png", "Тыква", 430, 300, 40, 40);
        add(pumpkin1Label);
       
        galochkaLabel = createImageLabel("galochka.png", "✓", 200, 250, 50, 50);
        galochkaLabel.setVisible(false);
        add(galochkaLabel);
       
        ghost1Label = createImageLabel("ghost1.png", "👻", 200, 250, 50, 50);
        ghost1Label.setVisible(false);
        add(ghost1Label);
        
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setBounds(100, 310, 300, 30);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 12));
        messageLabel.setVisible(false);
        add(messageLabel);
    }
    
    private JLabel createImageLabel(String imageName, String fallbackText, int x, int y, int width, int height) {
        JLabel label = new JLabel();
        label.setBounds(x, y, width, height);
        
        // Пробуем разные пути для загрузки изображения
        ImageIcon icon = loadImage(imageName);
        
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
            System.out.println("Успешно загружено изображение: " + imageName);
        } else {
            // Если изображение не найдено, используем текст
            label.setText(fallbackText);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            System.out.println("Не удалось загрузить изображение: " + imageName);
        }
        
        return label;
    }
    
    private ImageIcon loadImage(String imageName) {
        // Пробуем разные пути для загрузки изображения
        
        // 1. Из папки resources в classpath
        try {
            java.net.URL imageURL = getClass().getResource("/images/" + imageName);
            if (imageURL != null) {
                return new ImageIcon(imageURL);
            }
        } catch (Exception e) {
            System.out.println("Не удалось загрузить из classpath: " + imageName);
        }
        
        // 2. Из папки images в корне проекта
        try {
            File imageFile = new File("images/" + imageName);
            if (imageFile.exists()) {
                return new ImageIcon(imageFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Не удалось загрузить из папки images: " + imageName);
        }
        try {
            File imageFile = new File(imageName);
            if (imageFile.exists()) {
                return new ImageIcon(imageFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Не удалось загрузить из корня: " + imageName);
        }
        
        // 4. Из папки src/resources/images
        try {
            File imageFile = new File("src/resources/images/" + imageName);
            if (imageFile.exists()) {
                return new ImageIcon(imageFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Не удалось загрузить из src/resources/images: " + imageName);
        }
        
        return null;
    }
    
    private void setupEventHandlers() {
        // Обработчик кнопки входа
        buttonEnter.addActionListener(e -> authenticateUser());
        
        // Обработчик кнопки закрытия
        buttonClose.addActionListener(e -> System.exit(0));
        
        // Обработчик клика мыши для анимации
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    startAnimation();
                }
            }
        });
    }
    
    private void authenticateUser() {
        String login = textFieldLogin.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (login.isEmpty() || password.isEmpty()) {
            showErrorMessage("Поля не могут быть пустыми!");
            return;
        }
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:curt.db");
            String sql = "SELECT * FROM court WHERE login = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                showSuccessMessage();
            } else {
                showErrorMessage("Вас нет в базе данных суда!");
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showErrorMessage("Ошибка подключения к базе данных");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void showSuccessMessage() {
        galochkaLabel.setVisible(true);
        ghost1Label.setVisible(false);
        messageLabel.setText("Поздравляем! Добро пожаловать в приложение Верховного суда Москвы");
        messageLabel.setForeground(Color.GREEN);
        messageLabel.setVisible(true);
        
        // Сбрасываем позиции для анимации
        galochkaLabel.setLocation(200, 250);
        messageLabel.setLocation(100, 310);
        animationY = 0;
    }
    
    private void showErrorMessage(String message) {
        ghost1Label.setVisible(true);
        galochkaLabel.setVisible(false);
        messageLabel.setText(message);
        messageLabel.setForeground(Color.RED);
        messageLabel.setVisible(true);
        
        // Сбрасываем позиции для анимации
        ghost1Label.setLocation(200, 250);
        messageLabel.setLocation(100, 310);
        animationY = 0;
    }
    
    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            return;
        }
        
        animationTimer = new Timer(20, e -> {
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
                animationTimer.stop();
            }
        });
        
        animationTimer.start();
    }

    public static void main(String args[]) {
        // Запускаем приложение
        SwingUtilities.invokeLater(() -> {
            new LoginFormManual().setVisible(true);
        });
    }
}