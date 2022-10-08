import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;

import java.io.File;
import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import histograma.HistogramaGUI;

public class Gui extends JFrame {
    
    //dados do arquivo 
    private String nomeImgAtual = null;
    private String caminhoImgAtual = null;
    private String formatoImgAtual = null;
    
    //menu
    private JMenuBar menu = new JMenuBar();
    
    //menu arquivo
    private JMenu menuArq = new JMenu("Arquivo");  
    private JMenuItem btArqAbrir = new JMenuItem("  abrir  ");
    private JMenuItem btArqSalvar = new JMenuItem("  salvar  ");
    private JMenuItem btArqSalvarComo = new JMenuItem("  salvar como  ");
    private JMenuItem btArqFechar = new JMenuItem("  fechar  ");
    
    //menu ferramentas
    private JMenu menuFerramentas = new JMenu("Ferramentas");
    private JMenuItem btGerarHistograma = new JMenuItem("  Gerar histograma  ");
    private JMenuItem btRestaurar = new JMenuItem("  Restaurar  ");
    
    //submenu filtros
    private JMenu menuFiltros = new JMenu("  Filtros  "); 
    private JMenuItem btFiltroCinza = new JMenuItem("  Tons de cinza  ");
    private JMenuItem btFiltroNegativo = new JMenuItem("  Negativo  ");
    private JMenu menuFiltrosColoridos = new JMenu("  Coloridos  "); 
    private JMenuItem btFiltroVermelho = new JMenuItem("  Vermelho  ");
    private JMenuItem btFiltroVerde = new JMenuItem("  Verde  ");
    private JMenuItem btFiltroAzul = new JMenuItem("  Azul  ");
    private JMenuItem btFiltroPretoBranco = new JMenuItem("  Preto e Branco  ");
    private JMenuItem btFiltroPassaBaixa = new JMenuItem("  Passa-baixa (Média ponderada)  ");
    private JMenuItem btFiltroPassaAlta = new JMenuItem("  Passa-alta (Laplace)  ");
    private JMenuItem btFiltroNenhum = new JMenuItem("  Nenhum  ");
    
    //menu configuracoes
    private JMenu menuConfig = new JMenu("Configurações");
    //submenu tamanho
    private JMenu menuTamanho = new JMenu("  Tamanho da imagem  ");
    private JMenuItem btTamanhoTela = new JMenuItem("  preencher tela  ");
    private JMenuItem btTamanhoReal = new JMenuItem("  tamanho real da imagem  ");
    
    //painel principal onde a imagem sera exibida
    private PainelExibicao painelImg = new PainelExibicao();
    
    private HistogramaGUI janelaHistograma;

    //painel de mensagens na parte inferior da tela
    private JPanel painelInfo = new JPanel();
    private JLabel msg = new JLabel("imagem : NENHUM");
    
    public Gui(int largura, int altura) {
        //config. de janela
        super("processamento de imagens");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(largura, altura);
        
        //menu arquivo
        menuArq.add(btArqAbrir);
        menuArq.add(btArqSalvar);
        menuArq.add(btArqSalvarComo);
        menuArq.add(btArqFechar);
        
        //menu filtros
        menuFerramentas.add(btGerarHistograma);
        menuFerramentas.add(btRestaurar);
        menuFiltros.add(btFiltroCinza);
        menuFiltros.add(btFiltroNegativo);
        menuFiltros.add(btFiltroPretoBranco);
        menuFiltros.add(btFiltroPassaBaixa);
        menuFiltros.add(btFiltroPassaAlta);
        menuFiltrosColoridos.add(btFiltroVermelho);
        menuFiltrosColoridos.add(btFiltroVerde);
        menuFiltrosColoridos.add(btFiltroAzul);
        menuFiltros.add(menuFiltrosColoridos);
        menuFiltros.add(btFiltroNenhum);
        menuFerramentas.add(menuFiltros);
        
        //menu configuracoes
        menuTamanho.add(btTamanhoTela);
        menuTamanho.add(btTamanhoReal);
        menuConfig.add(menuTamanho);
               
        //barra menu
        menu.add(menuArq);
        menu.add(menuFerramentas);
        menu.add(menuConfig);
        
        //painel inferior de mensagens ao usuário
        painelInfo.setLayout(new FlowLayout());
        painelInfo.add(msg);
        
        add(menu);  
        setJMenuBar(menu); 
        add(painelImg, BorderLayout.CENTER);
        add(painelInfo, BorderLayout.SOUTH);
        
        setVisible(true);
    
        btArqAbrir.addActionListener(e -> {
            
            if(nomeImgAtual == null){
    
                try{
                    //instancia um JFileChooser, componente de alto nível para selecao de um arquivo dentre os diretorios do windows
                    JFileChooser seletorArquivo = new JFileChooser();
                    //adiciona um filtro para os tipos de arquivo que podem ser selecionados
                    seletorArquivo.addChoosableFileFilter(new FiltroImagem()); //aceita apenas arquivos de imagem
                    seletorArquivo.setAcceptAllFileFilterUsed(false);
                    seletorArquivo.setCurrentDirectory(new File(System.getProperty("user.home")));
                    
                    int op = seletorArquivo.showOpenDialog(this);
                    //caso o arquivo seja aceito(imagem)
                    if(op == JFileChooser.APPROVE_OPTION){
                        File f = seletorArquivo.getSelectedFile();   //obtem arquivo selecionado
                        nomeImgAtual = f.getName(); // obtem o nome do arquivo ex: "imagem.jpg"
                        caminhoImgAtual = f.getAbsolutePath();  //caminho do diretorio da imagem atual
                        int i = nomeImgAtual.lastIndexOf('.');
                        if (i > 0 &&  i < nomeImgAtual.length() - 1) {
                            formatoImgAtual = nomeImgAtual.substring(i+1).toLowerCase(); //obtem o formato da imagem
                        }
                        
                        painelImg.setImgAtual(ImageIO.read(f));
                        msg.setText("Imagem: " + nomeImgAtual);      
                        painelImg.exibirImagem();
                    }
                    
                }catch (java.io.IOException ex){
                    ex.printStackTrace();
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "para abrir uma nova imagem, é necessário\n fechar a imagem aberta atualmente.");
            }
        }); 
        //salva a imagem atual no diretorio ja selecionado
        btArqSalvar.addActionListener(e -> {
            
            if(nomeImgAtual != null){
                
                try{
                    painelImg.getImgAtual().salvarImagemCopia();
                    //escreve a imagem em um arquivo
                    ImageIO.write((BufferedImage) painelImg.getImgAtual().getImgOriginal(), formatoImgAtual, new File(caminhoImgAtual));
                    msg.setText("Imagem: NENHUM");
                } catch (java.io.IOException ex){ 
                    ex.printStackTrace(); 
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "não há nenhum arquivo aberto");
            }
                
        });  
        //seleciona o diretorio em que se deseja salvar a imagem atual
        btArqSalvarComo.addActionListener(e -> {
            
            if(nomeImgAtual != null){
                
                try{
                    JFileChooser seletorArquivo = new JFileChooser();
        
                    seletorArquivo.setCurrentDirectory(new File(System.getProperty("user.home")));
                    seletorArquivo.setDialogTitle("Escolha uma pasta: ");
                    seletorArquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //aceita apenas diretorios
        
                    int op = seletorArquivo.showOpenDialog(this);
                    if(op == JFileChooser.APPROVE_OPTION){
                        File arq = seletorArquivo.getSelectedFile();
                        caminhoImgAtual = arq.getAbsolutePath()  + "/" + nomeImgAtual; //obtem o caminho para o dirretorio em que se deseja salvar a imagem
                        painelImg.getImgAtual().salvarImagemCopia();
                        ImageIO.write((BufferedImage) painelImg.getImgAtual().getImgOriginal(), formatoImgAtual, new File(caminhoImgAtual));
                        msg.setText("Imagem: NENHUM");
                    }
                    
                }catch (java.io.IOException ex){ 
                    ex.printStackTrace();
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "não há nenhum arquivo aberto");
            }
        });
        
        //botao para gerar histogramas
        btGerarHistograma.addActionListener(e -> {
            Imagem img = painelImg.getImgAtual();
            if(img.getImgCopia() != null){
                TipoImagem tipo = img.getTipoImgCopia();
                //caso a imagem seja tons de cinza ou binaria
                if(tipo == TipoImagem.CINZA || tipo == TipoImagem.BINARIA){
                    int histograma[]; //gera um histograma unico
                    histograma = painelImg.getImgAtual().obterHistogramaCinza();
                    janelaHistograma = new HistogramaGUI(histograma);
                }else if(tipo == TipoImagem.COLORIDA){
                    int histogramaRgb[][]; //gera tres histogramas para os valores de rgb
                    histogramaRgb = painelImg.getImgAtual().obterHistogramaColorido();
                    janelaHistograma = new HistogramaGUI(histogramaRgb);
                }
            }else{
                JOptionPane.showMessageDialog(null, "não há nenhuma imagem aberta");
            }
        });
        
        btFiltroCinza.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroCinza();
            painelImg.removeAll();
            painelImg.repaint();
        }); 

        btFiltroNegativo.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroNegativo();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroPretoBranco.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroPretoBranco();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroVermelho.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroVermelho();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroVerde.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroVerde();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroAzul.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroAzul();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroPassaBaixa.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroPassaBaixa();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroPassaAlta.addActionListener(e -> {
            painelImg.getImgAtual().aplicarFiltroPassaAlta();
            painelImg.removeAll();
            painelImg.repaint();
        }); 
        
        btFiltroNenhum.addActionListener(e -> {
            painelImg.getImgAtual().restaurarImagemOriginal();
            painelImg.removeAll();
            painelImg.repaint();
        });
        
        btRestaurar.addActionListener(e -> {
            painelImg.getImgAtual().restaurarImagemOriginal();
            painelImg.removeAll();
            painelImg.repaint();
        });
        //fecha a imagem e joga fora seus rastros como o diretorio em que esta localizada
        btArqFechar.addActionListener(e -> {
            nomeImgAtual = null;  caminhoImgAtual = null;  formatoImgAtual = null;
            painelImg.setImgAtual(null);
            msg.setText("Imagem: NENHUM");
            painelImg.removeAll();
            painelImg.repaint();
        });  
        
        btTamanhoTela.addActionListener(e -> {
            painelImg.setProporcaoAtual(TipoProporcao.TELA);
            painelImg.removeAll();
            painelImg.repaint();
        });
        
        btTamanhoReal.addActionListener(e -> {
            painelImg.setProporcaoAtual(TipoProporcao.REAL);
            painelImg.removeAll();
            painelImg.repaint();
        });
          
    }
    
}

 //classe que garante que arquivos de imagem sejam aceitas
class FiltroImagem extends FileFilter {
   public final static String JPEG = "jpeg";
   public final static String JPG = "jpg";
   public final static String GIF = "gif";
   public final static String TIFF = "tiff";
   public final static String TIF = "tif";
   public final static String PNG = "png";
   
   @Override
   public boolean accept(File f) {
      if (f.isDirectory()) {
         return true;
      }

      String extension = getExtension(f);
      if (extension != null) {
         if (extension.equals(TIFF) || extension.equals(TIF) || extension.equals(GIF) ||
            extension.equals(JPEG) || extension.equals(JPG) || extension.equals(PNG)) {
            return true;
         } else return false;
      }
      return false;
   }

   @Override
   public String getDescription() {
      return "Apenas Imagens";
   }

   String getExtension(File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');
   
      if (i > 0 &&  i < s.length() - 1) {
         ext = s.substring(i+1).toLowerCase();
      }
      return ext;
   } 
}