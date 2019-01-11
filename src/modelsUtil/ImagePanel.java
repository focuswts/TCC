/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelsUtil;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author focuswts
 */
public class ImagePanel extends JPanel {

    private Image img;
    private ImageIcon imgIcon;

    
    public ImagePanel() {

    }

    public ImagePanel(String img) {
    this(new ImageIcon(img).getImage());
  }
    
    public ImagePanel(Image img) {
        browseImage();
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public ImageIcon getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(ImageIcon imgIcon) {
        this.imgIcon = imgIcon;
        this.img = imgIcon.getImage();
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void browseImage() {
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        JFileChooser fc = new JFileChooser();

        fc.setFileFilter(imageFilter);
        int res = fc.showOpenDialog(null);
        // We have an image!
        try {

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                Image image = ImageIO.read(file);
                this.img = image;
            } // Oops!
            else {
                JOptionPane.showMessageDialog(null,
                        "You must select one image to be the reference.", "Aborting...",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception iOException) {
        }
        this.revalidate();
        this.repaint();
    }

    public void reloadImage(Image img) {
        try {
            this.img = img;
            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            System.out.println("Erro Ao Recarregar JPanel: " + e);
        }
    }

}
