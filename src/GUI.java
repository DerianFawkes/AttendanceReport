import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by sobol on 05.09.2014.
 */
public class GUI {

    public GUI() {
        JFrame.setDefaultLookAndFeelDecorated(false);
        final JFrame frame = new JFrame("Отчетность по посещаемости");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Левая панель
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));

        JLabel openPath = new JLabel("Путь к файлу мониторинга:");
        final JTextField openPathText = new JTextField(30);
        JButton openButton = new JButton("Открыть файл");

        northPanel.add(openPath);
        northPanel.add(openPathText);
        northPanel.add(openButton);
        panel.add(northPanel, BorderLayout.NORTH);



        //Южная панель
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));

        JButton saveButton = new JButton("Сохранить отчеты");
        southPanel.add(saveButton);

        //Восточная панель
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));


        JLabel enterTime = new JLabel("Время прихода");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

        JTextField enterHours = new JTextField(5);
        JLabel dots1 = new JLabel(":");
        JTextField enterMinutes = new JTextField(5);
        panel1.add(enterHours);
        panel1.add(dots1);
        panel1.add(enterMinutes);

        eastPanel.add(enterTime, c);

        JTextField enterHours = new JTextField(5);

        panel.add(enterHours, c);

        JLabel dots1 = new JLabel(":");

        panel.add(dots1, c);

        JTextField enterMinutes = new JTextField(5);

        panel.add(enterMinutes, c);

        JLabel exitTime = new JLabel("Время ухода");

        panel.add(exitTime, c);

        JTextField exitHours = new JTextField(2);

        panel.add(exitHours, c);

        JLabel dots2 = new JLabel(":");

        panel.add(dots2, c);

        JTextField exitMinutes = new JTextField(2);

        panel.add(exitMinutes, c);

        //Обработчики
        //Кнопка обзора при открытии файла
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    String path = file.getAbsolutePath();
                    openPathText.setText(path);
                    try {
                        AttendanceReport.dataBase = new DataBase(FileReadWrite.read(file));
                        AttendanceReport.dataBase.fillDataBase();
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка чтения файла",
                                "Произошла ошибка во время чтения файла",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidFormatException ife) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка формата файла",
                                "Неверный формат файла. Выберите другой файл.",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //Кнопка обзора для сохранения отчетов
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser filesave = new JFileChooser();
                filesave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = filesave.showSaveDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filesave.getSelectedFile();
                    String destinationFolder = file.getAbsolutePath()+"\\";
                    //savePathText.setText(destinationFolder);
                    try {
                        AttendanceReport.dataBase.exportReports(destinationFolder);
                    } catch (IOException ioe) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка записи файлов",
                                "Произошла ошибка во время записи файлов. Проверьте путь сохранения.",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidFormatException ife) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка",
                                ife.getMessage(),
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        frame.getContentPane().add(panel);
        frame.setPreferredSize(new Dimension(650, 300));

        frame.pack();
        frame.setVisible(true);
    }

    private void setConstraints (GridBagConstraints c, int x, int y, int width) {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = width;
        c.gridx = x;
        c.gridy = y;
    }
}

