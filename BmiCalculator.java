import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BmiCalculator extends JFrame {

    private final JTextField heightField = new JTextField(12);
    private final JTextField weightField = new JTextField(12);
    private final JLabel resultLabel = new JLabel("請輸入身高與體重後按下「計算 BMI」");

    public BmiCalculator() {
        super("BMI 計算機");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        inputPanel.add(new JLabel("身高（公分）："));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("體重（公斤）："));
        inputPanel.add(weightField);

        JButton calculateButton = new JButton("計算 BMI");
        calculateButton.addActionListener(e -> calculateBmi());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);

        resultLabel.setFont(resultLabel.getFont().deriveFont(Font.BOLD, 14f));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private void calculateBmi() {
        String heightText = heightField.getText().trim();
        String weightText = weightField.getText().trim();

        if (heightText.isEmpty() || weightText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "請輸入身高與體重的數字。",
                    "輸入不完整",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double heightCm;
        double weightKg;

        try {
            heightCm = Double.parseDouble(heightText);
            weightKg = Double.parseDouble(weightText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "身高與體重必須為有效數字。",
                    "格式錯誤",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (heightCm <= 0 || weightKg <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "身高與體重必須大於 0。",
                    "數值無效",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);

        DecimalFormat format = new DecimalFormat("0.00");
        String category = getBmiCategory(bmi);

        resultLabel.setText(
                "BMI：" + format.format(bmi) + "　分類：" + category);
    }

    private String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "體重過輕";
        }
        if (bmi < 24) {
            return "正常範圍";
        }
        if (bmi < 27) {
            return "過重";
        }
        if (bmi < 30) {
            return "輕度肥胖";
        }
        if (bmi < 35) {
            return "中度肥胖";
        }
        return "重度肥胖";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BmiCalculator app = new BmiCalculator();
            app.setVisible(true);
        });
    }
}
