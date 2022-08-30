package com.ufes.pss.trabalhofinal.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileView;

public class MiniaturaFileChooser extends JFileChooser{
    
    private int tamanho;
    private Image imagem;
    private Map<Object, ImageIcon> cache = new HashMap<>();
    
    public MiniaturaFileChooser(File file) {
        super(file);
        tamanho = 20;
        imagem = new BufferedImage(tamanho, tamanho, BufferedImage.TYPE_INT_ARGB);
        setFileView(new MiniaturaView());
    }
    
    private class MiniaturaView extends FileView {
        
        private ExecutorService executorService = Executors.newCachedThreadPool();
        
        @Override
        public Icon getIcon(File file) {
            synchronized (cache) {
                ImageIcon icon = cache.get(file);
                
                if(icon == null) {
                    icon = new ImageIcon(imagem);
                    cache.put(file, icon);
                    executorService.submit(new MiniaturaIcon(icon, file));
                }
                return icon;
            }
        }
    }
    
    private class MiniaturaIcon implements Runnable {
        
        private ImageIcon icon;
        private File file;
                
        public MiniaturaIcon(ImageIcon icon, File file) {
            this.icon = icon;
            this.file = file;
        }
        
        @Override
        public void run() {
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
            Image image = imageIcon.getImage().getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);
            icon.setImage(image);
            
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }
    } 
}
