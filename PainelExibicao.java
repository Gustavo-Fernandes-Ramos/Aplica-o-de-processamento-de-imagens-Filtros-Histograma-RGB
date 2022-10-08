import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

//painel onde as imagens serao exibidas
public class PainelExibicao extends JPanel{
    
    private Imagem imgAtual = null;
    private TipoProporcao proporcaoImgAtual = TipoProporcao.TELA;
    
    //construtor
    public PainelExibicao(){
        this.imgAtual = new Imagem();
    }
    
    //getters e setters
    public void setImgAtual(BufferedImage img){
        this.imgAtual = new Imagem(img);
    }
    
    public Imagem getImgAtual(){ 
        return this.imgAtual;
    }
    
    public void setProporcaoAtual(TipoProporcao proporcao){ 
        this.proporcaoImgAtual = proporcao; 
    }
    
    public TipoProporcao getProporcaoAtual(){ 
        return this.proporcaoImgAtual; 
    }
    
    //metodos
    public void exibirImagem(){
        Graphics g = getGraphics();
        paint(g);
    }
    
    //metodo que desenha as imagens na tela
    public void paint(Graphics g) { 
        
        BufferedImage imgGrafica = imgAtual.getImgCopia();
        if(imgGrafica != null){
            //exibe uma imagem que é esticada para preencher toda tela
            if(proporcaoImgAtual == TipoProporcao.TELA){
                g.drawImage(imgGrafica, 0, 0, getWidth(), getHeight(), this);
                //exibe a imagem em tamanho real
            } else if(proporcaoImgAtual == TipoProporcao.REAL){
                g.drawImage(imgGrafica, 0, 0, imgGrafica.getWidth(this), imgGrafica.getHeight(this) , this); 
                
            }
        }
    }
        
}  
