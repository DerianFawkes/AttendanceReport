import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by sobol on 05.09.2014.
 */
public class GUI {
    JProgressBar progressBar;
    public GUI() {
        final GUI referenceToGUI = this;
        JFrame.setDefaultLookAndFeelDecorated(false);
        final JFrame frame = new JFrame("Отчетность по посещаемости");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //Северная панель
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

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setVisible(false);
        southPanel.add(progressBar);
        panel.add(southPanel, BorderLayout.SOUTH);



        //Восточная панель
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JLabel enterTime = new JLabel("Время прихода");
        eastPanel.add(enterTime);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));

        JTextField enterHours = new JTextField(5);
        ((AbstractDocument)enterHours.getDocument()).setDocumentFilter(new SizeFilter(2));
        enterHours.setMaximumSize(new Dimension(100,20));

        JLabel dots1 = new JLabel(":");

        JTextField enterMinutes = new JTextField(5);
        ((AbstractDocument)enterMinutes.getDocument()).setDocumentFilter(new SizeFilter(2));
        enterMinutes.setMaximumSize(new Dimension(100,20));

        panel1.add(enterHours);
        panel1.add(dots1);
        panel1.add(enterMinutes);
        eastPanel.add(panel1);

        JLabel exitTime = new JLabel("Время ухода");
        eastPanel.add(exitTime);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        JTextField exitHours = new JTextField(5);
        ((AbstractDocument)exitHours.getDocument()).setDocumentFilter(new SizeFilter(2));
        exitHours.setMaximumSize(new Dimension(100,20));

        JLabel dots2 = new JLabel(":");

        JTextField exitMinutes = new JTextField(5);
        ((AbstractDocument)exitMinutes.getDocument()).setDocumentFilter(new SizeFilter(2));
        exitMinutes.setMaximumSize(new Dimension(100,20));

        panel2.add(exitHours);
        panel2.add(dots2);
        panel2.add(exitMinutes);
        eastPanel.add(panel2);

        panel.add(eastPanel, BorderLayout.EAST);


        //Обработчики
        //Кнопка обзора при открытии файла
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                fileopen.setCurrentDirectory(new File(".\\"));
                int ret = fileopen.showOpenDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    String path = file.getAbsolutePath();
                    openPathText.setText(path);
                    try {
                        AttendanceReport.dataBase = new DataBase(FileReadWrite.read(file), referenceToGUI);
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
                filesave.setCurrentDirectory(new File(".\\"));
                filesave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int ret = filesave.showSaveDialog(frame);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = filesave.getSelectedFile();
                    String destinationFolder = file.getAbsolutePath()+"\\";
                    //savePathText.setText(destinationFolder);
                    progressBar.setVisible(true);
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
        frame.setPreferredSize(new Dimension(650, 200));

        frame.pack();
        frame.setVisible(true);
    }

    public class SizeFilter extends DocumentFilter {

        private int maxCharacters;

        public SizeFilter(int maxChars) {
            maxCharacters = maxChars;
        }

        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
                throws BadLocationException {

            if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
                super.insertString(fb, offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
                throws BadLocationException {

            if ((fb.getDocument().getLength() + str.length()
                    - length) <= maxCharacters) {
                super.replace(fb, offs, length, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    private void setConstraints (GridBagConstraints c, int x, int y, int width) {
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = width;
        c.gridx = x;
        c.gridy = y;
    }
}

