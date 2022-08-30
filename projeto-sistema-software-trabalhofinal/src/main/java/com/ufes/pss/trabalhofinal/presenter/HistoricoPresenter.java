package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.Imagem;
import com.ufes.pss.trabalhofinal.dao.FiltroDAO;
import com.ufes.pss.trabalhofinal.dao.ImagemDAO;
import com.ufes.pss.trabalhofinal.dao.UsuarioDAO;
import com.ufes.pss.trabalhofinal.model.FiltroModel;
import com.ufes.pss.trabalhofinal.view.HistoricoView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class HistoricoPresenter {
    
    private HistoricoView view;
    private ArrayList<FiltroModel> filtros;
    private Imagem imagem;
    private FiltroDAO filtroDAO;
    private ImagemDAO imagemDAO;
    private UsuarioDAO usuarioDAO;
    
    public HistoricoPresenter(JDesktopPane desktop, Imagem imagem) {
        this.view = new HistoricoView();
        this.filtros = new ArrayList<>();
        //this.observers = new ArrayList<>();
        this.filtroDAO = new FiltroDAO();
        this.imagemDAO = new ImagemDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.imagem = imagem;

        this.view.getBtnFechar().addActionListener((e) -> {            
            this.view.dispose();
        });

        carregarFiltros();

        desktop.add(this.view);

        this.view.setVisible(true);
    }
    
    private void carregarFiltros() {
        try {
            this.filtros = filtroDAO.getFiltros(this.imagemDAO.getImagemByString(imagem.getImagem().toString()).getIdImagem());
            setTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.view, e.getMessage());
        }
    }
    
    private void setTable() {
        this.view.getTblHistorico().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel tblModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Nome Usuário", "Data"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblModel.setNumRows(0);

        for (FiltroModel filtro : this.filtros) {
            tblModel.addRow(
                    new Object[]{
                        usuarioDAO.getUsuarioById(filtro.getIdUsuario()),
                        filtro.getData()
                    }
            );
        }

        this.view.getTblHistorico().setModel(tblModel);
    }
}
