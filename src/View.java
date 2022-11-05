import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JFrame{
    private BufferedImage inputImage = null;
    private Image inputDisplayImage = null;
    private Image outputImage = null;
    private SpatialFilter spatialFilter = null;
    private JComboBox comboBox;
    private ImageIcon nullImageIcon = new ImageIcon();
    public View() {
        spatialFilter = new SpatialFilter();
        setTitle("22012074 마상균 CV 공간필터링");
        setSize(600, 450);



        Container container = getContentPane();
        container.setLayout(null);


        String Filter[] = {"Mean Filter(3x3)","Mean Filter(5x5)","Median Filter(3x3)","Median Filter(5x5)","LaplacianFilter(3x3)","LoGFilter(3x3)"};

        comboBox = new JComboBox(Filter);
        comboBox.setBounds(10, 320, 262, 41);
        container.add(comboBox);

        JLabel inputTextLabel = new JLabel("입력 이미지");
        inputTextLabel.setBounds(110, 10, 70, 25);
        container.add(inputTextLabel);

        ImageIcon inputImageIcon = new ImageIcon();
        JLabel inputImageLabel = new JLabel(inputImageIcon);
        inputImageLabel.setBounds(10, 50, 256, 256);
        container.add(inputImageLabel);

        JLabel outputTextLabel = new JLabel("필터링 결과 이미지");
        outputTextLabel.setBounds(375, 10, 120, 25);
        container.add(outputTextLabel);

        ImageIcon outputImageIcon = new ImageIcon();
        JLabel outputImageLabel = new JLabel(outputImageIcon);
        outputImageLabel.setBounds(300, 50, 256, 256);
        container.add(outputImageLabel);



        JButton fileChooserButton = new JButton("File choose");
        fileChooserButton.setBounds(300, 320, 125, 23);
        fileChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = fileOpen(fileChooserButton);
                if (filePath == null)
                    return;
                File inputImageFile = new File(filePath);
                try {
                    inputImage = ImageIO.read(inputImageFile);
                    inputDisplayImage = inputImage.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
                    inputImageIcon.setImage(inputDisplayImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                inputImageLabel.revalidate();
                inputImageLabel.repaint();
            }
        });
        container.add(fileChooserButton);

        JButton executeButton = new JButton("Run");
        executeButton.setBounds(440, 320, 97, 23);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String combo_String = comboBox.getSelectedItem().toString();
                switch (combo_String) {
                    case "Mean Filter(3x3)" -> outputImage = spatialFilter.meanFilter33(inputImage);
                    case "Mean Filter(5x5)" -> outputImage = spatialFilter.meanFilter55(inputImage);
                    case "Median Filter(3x3)" -> outputImage = spatialFilter.medianFilter33(inputImage);
                    case "Median Filter(5x5)" -> outputImage = spatialFilter.medianFilter55(inputImage);
                    case "LaplacianFilter(3x3)" -> outputImage = spatialFilter.laplacianFilter(inputImage);
                    case "LoGFilter(3x3)" -> outputImage = spatialFilter.laplacianGaussianFilter(inputImage);
                }
                outputImage = outputImage.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
                outputImageIcon.setImage(outputImage);
                outputImageLabel.revalidate();
                outputImageLabel.repaint();
            }
        });
        container.add(executeButton);
        setVisible(true);
    }

    public String fileOpen(Component parent) {
        String filePath = "";

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("파일 열기");
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(parent);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getPath();
            return filePath;
        } else {
            return null;
        }
    }
    public static void main(String[] args) {
        new View();
    }
}
