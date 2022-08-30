package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.Imagem;
import com.ufes.pss.trabalhofinal.dao.FiltroDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.observer.Observable;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.VisualizarImagemView;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.codehaus.plexus.util.FileUtils;

public class VisualizarImagemPresenter implements Observable {

    private VisualizarImagemView view;
    private ArrayList<Observer> observers;
    private Imagem imagem;
    private File srcImagem;

    private FiltroDAO filtroDAO;

    private UsuarioModel usuarioLogado;
    private JDesktopPane mainDesktop;

    public VisualizarImagemPresenter(JDesktopPane desktop, Imagem imagem, File srcImagem, UsuarioModel usuarioLogado) {
        view = new VisualizarImagemView();
        this.imagem = imagem;
        this.srcImagem = srcImagem;
        filtroDAO = new FiltroDAO();
        this.usuarioLogado = usuarioLogado;
        this.mainDesktop = desktop;

        this.view.getBtnFechar().addActionListener((e) -> {
            view.dispose();
        });

        this.view.getBtnAplicarFiltro().addActionListener((e) -> {
            try {
                new AplicarFiltroPresenter(desktop, imagem, usuarioLogado);
            } catch (Exception ex) {
                Logger.getLogger(VisualizarImagemPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.view.getBtnHistorico().addActionListener((e) -> {
            new HistoricoPresenter(desktop, imagem);
        });

        this.view.getBtnExcluir().addActionListener((e) -> {
            excluirImg();
            //deve ter a opção de desfazer a exclusão após excluir
        });

        this.view.getBtnExportar().addActionListener((e) -> {
            exportarImg();
        });

        this.view.getBtnCompartilhar().addActionListener((e) -> {
            compartilharImg(this.srcImagem.getAbsolutePath());
        });

        this.view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));

        this.view.getBtnHistorico().setEnabled(false); // TO DO!

        desktop.add(this.view);
        this.view.setVisible(true);
    }

    private void exportarImg() {
        try {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(this.view);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                JOptionPane.showMessageDialog(this.view, "PASTA ->" + file.getName());

                FileUtils.copyFileToDirectory(this.srcImagem, file);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao exportar imagem: " + e.getMessage());
        }
    }

    private void excluirImg() {
        try {
            FileUtils.copyFileToDirectory(this.srcImagem, new File("images/.imgs-excluidas"));
            this.srcImagem.delete();
            this.view.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao excluir imagem: " + e.getMessage());
        }
    }

    private void compartilharImg(String pathImg) {
        try {
            new CompartilharImagemPresenter(this.mainDesktop, this.usuarioLogado, pathImg);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Ocorreu algum erro!\n " + e.getMessage());
        }
    }

    @Override
    public void registrarObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificar(Object object) {
        for (Observer observer : observers) {
            observer.update(object);
        }
    }
}
