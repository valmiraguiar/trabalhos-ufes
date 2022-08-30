package com.ufes.pss.trabalhofinal.presenter;

import com.pss.imagem.processamento.decorator.AzulDecorator;
import com.pss.imagem.processamento.decorator.BrilhoDecorator;
import com.pss.imagem.processamento.decorator.EspelhadaDecorator;
import com.pss.imagem.processamento.decorator.ImagemComponente;
import com.pss.imagem.processamento.decorator.NegativaDecorator;
import com.pss.imagem.processamento.decorator.PixeladaDecorator;
import com.pss.imagem.processamento.decorator.RotacionaDecorator;
import com.pss.imagem.processamento.decorator.SepiaDecorator;
import com.pss.imagem.processamento.decorator.TomDeCinzaDecorator;
import com.pss.imagem.processamento.decorator.VerdeDecorator;
import com.pss.imagem.processamento.decorator.VermelhoDecorator;
import com.ufes.pss.trabalhofinal.builder.AzulEspelhadaBuilder;
import com.ufes.pss.trabalhofinal.builder.Builder;
import com.ufes.pss.trabalhofinal.builder.Diretor;
import com.ufes.pss.trabalhofinal.builder.VemelhoRotacaoBuilder;
import com.ufes.pss.trabalhofinal.builder.VerdeEspelhadaBuilder;
import com.ufes.pss.trabalhofinal.dao.FiltroDAO;
import com.ufes.pss.trabalhofinal.dao.ImagemDAO;
import com.ufes.pss.trabalhofinal.model.UsuarioModel;
import com.ufes.pss.trabalhofinal.model.Zelador;
import com.ufes.pss.trabalhofinal.observer.Observable;
import com.ufes.pss.trabalhofinal.observer.Observer;
import com.ufes.pss.trabalhofinal.view.AplicarFiltroView;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class AplicarFiltroPresenter implements Observable {
    
    private AplicarFiltroView view;
    private ImagemComponente imagem;
    private ArrayList<Observer> observers;
    private FiltroDAO filtroDAO;
    private ImagemDAO imagemDAO;
    private Diretor diretor;
    private ArrayList<Builder> builders;
    
    public AplicarFiltroPresenter(JDesktopPane desktop, ImagemComponente imagem, UsuarioModel usuarioLogado) throws Exception {
        view = new AplicarFiltroView();
        this.imagem = imagem;
        imagemDAO = new ImagemDAO();
        filtroDAO = new FiltroDAO();
        filtroDAO.criarTabelaFiltro();
        Zelador.getInstancia().add(this.criarMemento(this.imagem));
        diretor = new Diretor();
        builders = new ArrayList<>();
        setBuilders();
        
        this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
        
        this.view.getBtnFechar().addActionListener((e) -> {
            view.dispose();
        });
        
        this.view.getBtnRestaurarPadrao().addActionListener((e) -> {
            
            try {
                this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this.view, ex.getMessage());
            }
        });
        
        this.view.getBtnDesfazer().addActionListener((e) -> {
            try {
                desfazer();
                this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.view, ex.getMessage());
            }
        });
        
        for(Builder builder : builders) {
            this.view.getComboFiltros().addItem(builder.toString());
        }
        
        this.view.getComboFiltros().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    for(Builder builder : builders) {
                        if(view.getComboFiltros().getSelectedItem().toString().equals(builder.toString())) {
                            if(builder.toString().equals("Verde + Espelhada")) {
                                diretor.fazVerdeEspelhada(builder, this.imagem);
                                this.imagem = builder.getResult();
                            }
                            if(builder.toString().equals("Vermelho + Rotacao")) {
                                diretor.fazVermelhoRotacao(builder, this.imagem);
                                this.imagem = builder.getResult();
                            }
                            if(builder.toString().equals("Azul + Espelhada")) {
                                diretor.fazAzulEspelhada(builder, this.imagem);
                                this.imagem = builder.getResult();
                            }
                        }
                    }
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                    Zelador.getInstancia().add(this.criarMemento(this.imagem));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxAzul().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    /*limparCampos();
                    this.view.getCbxAzul().setSelected(true);*/
                    this.imagem = new AzulDecorator(this.imagem); // decorar imagem
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem())); // adicionar imagem com filtro na view
                    //this.view.getLblImagem().setIcon(new ImageIcon(new SalvarImagemDecorator(imagemDecorator, "ImagemDecorada").getImagem()));
                    //this.filtroDAO.insertFiltro(usuarioLogado, this.imagemDAO.getImagemByString(imagem.getImagem().toString()).getIdImagem()); // salvar historico
                    Zelador.getInstancia().add(this.criarMemento(this.imagem)); // adicionar memento
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxVerde().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new VerdeDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                    Zelador.getInstancia().add(this.criarMemento(this.imagem));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxVermelha().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new VermelhoDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxEspelhada().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new EspelhadaDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxRotacao().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    int angulo = Integer.parseInt(JOptionPane.showInputDialog("Insira o ângulo da rotação!"));
                    this.imagem = new RotacionaDecorator(this.imagem, angulo);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
                
        this.view.getCbxNegativo().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new NegativaDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        this.view.getCbxSepia().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new SepiaDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
                
        this.view.getCbxPixelada().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    int tamanhoPixel = Integer.parseInt(JOptionPane.showInputDialog("Insira o tamanho dos pixels!"));
                    this.imagem = new PixeladaDecorator(this.imagem, tamanhoPixel);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
                
        this.view.getCbxTomDeCinza().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    this.imagem = new TomDeCinzaDecorator(this.imagem);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
                
        this.view.getCbxBrilho().addItemListener((e) -> {
            if(e.getStateChange() == ItemEvent.SELECTED){
                try {
                    int escala = Integer.parseInt(JOptionPane.showInputDialog("Insira a escala do brilho!"));
                    this.imagem = new BrilhoDecorator(this.imagem, escala);
                    this.view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this.view, ex.getMessage());
                }
            }
        });
        
        desktop.add(this.view);
        this.view.setVisible(true);
    }
    
    public void limparSelecao() {
        
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
    
    public Memento criarMemento(ImagemComponente imagem) {
        return new Memento(imagem);
    }
    
    public void desfazer() throws Exception {
        this.imagem = Zelador.getInstancia().getUltimo().getEstado();
    }
    
    public void setBuilders() {
        builders.add(new VerdeEspelhadaBuilder());
        builders.add(new VemelhoRotacaoBuilder());
        builders.add(new AzulEspelhadaBuilder());
    }
}
