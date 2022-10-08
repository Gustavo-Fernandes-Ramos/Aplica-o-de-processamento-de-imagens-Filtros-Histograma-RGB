
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;

public class Imagem{
    
    private TipoImagem tipoImgCopia;
    private TipoImagem tipoImgOriginal;
    
    private BufferedImage imgOriginal;
    private BufferedImage imgCopia;   //é uma copia da imagem original, que pode ser modificada

    //construtor vazio
    public Imagem(){
        setTipoImgCopia(TipoImagem.COLORIDA);
        setTipoImgOriginal(TipoImagem.COLORIDA);
        setImgOriginal(null);
        setImgCopia(null);
    }

    //construtor que recebe uma BufferedImage como parametro
    public Imagem(BufferedImage img){
        setTipoImgCopia(TipoImagem.COLORIDA);
        setTipoImgOriginal(TipoImagem.COLORIDA);
        setImgOriginal(img);
        setImgCopia(clonarImagem(imgOriginal)); //cria uma copia modificavel da imagem original
    }

    //imagem original
    public void setImgOriginal(BufferedImage imgOriginal){ this.imgOriginal = imgOriginal; }

    public BufferedImage getImgOriginal(){ return this.imgOriginal; }

    //imagem copia
    public void setImgCopia(BufferedImage imgCopia){ this.imgCopia = imgCopia; }

    public BufferedImage getImgCopia(){ return this.imgCopia; }

    //tipo de imagem (colorida, cinza, binaria)
    public void setTipoImgOriginal(TipoImagem tipoImgOriginal){ this.tipoImgOriginal = tipoImgOriginal; }

    public TipoImagem getTipoImgOriginal(){ return this.tipoImgOriginal; }
    
    public void setTipoImgCopia(TipoImagem tipoImgCopia){ this.tipoImgCopia = tipoImgCopia; }

    public TipoImagem getTipoImgCopia(){ return this.tipoImgCopia; }

    //metodos
    //copia a imagem original para a imagem copia
    public void restaurarImagemOriginal(){
        if(getImgOriginal() != null){ 
            setTipoImgCopia(getTipoImgOriginal());
            setImgCopia(clonarImagem(getImgOriginal())); 
        }
    }

    //copia a imagem copia para a imagem original
    public void salvarImagemCopia(){
        if(getImgCopia() != null){ 
            setTipoImgOriginal(getTipoImgCopia());
            setImgOriginal(clonarImagem(getImgCopia())); 
        }
    }

    //metodo que clona uma BufferedImage
    static BufferedImage clonarImagem(BufferedImage original) {
        BufferedImage copia = null;
        if(original != null){
            ColorModel cm = original.getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = original.copyData(null);
            copia = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        }
        return copia;
    }

    
    public void aplicarFiltroCinza(){
        BufferedImage img = clonarImagem(getImgCopia()); //gera uma copia da imagem modificavel
        if(img != null){
            img = Filtros.filtroCinza(img);
            setTipoImgCopia(TipoImagem.CINZA);
            setImgCopia(img);
        }
    }

    public void aplicarFiltroNegativo(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroNegativo(img);
            setTipoImgCopia(TipoImagem.COLORIDA);
            setImgCopia(img);
        }
    }

    public void aplicarFiltroVermelho(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroVermelho(img);
            setTipoImgCopia(TipoImagem.COLORIDA);
            setImgCopia(img);
        }
    }

    public void aplicarFiltroVerde(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroVerde(img);
            setTipoImgCopia(TipoImagem.COLORIDA);
            setImgCopia(img);
        }
    }

    public void aplicarFiltroAzul(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroAzul(img);
            setTipoImgCopia(TipoImagem.COLORIDA);
            setImgCopia(img);
        }
    }

    public void aplicarFiltroPretoBranco(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroPretoBranco(img);
            setTipoImgCopia(TipoImagem.BINARIA);
            setImgCopia(img);
        }
    }
    
    public void aplicarFiltroPassaBaixa(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroPassaBaixa(img);
            setImgCopia(img);
        }
    }
    
    public void aplicarFiltroPassaAlta(){
        BufferedImage img = clonarImagem(getImgCopia());
        if(img != null){
            img = Filtros.filtroPassaAlta(img);
            setImgCopia(img);
        }
    }
    
    //retorna um array de inteiros com 256 indices contendo as quantidades de cada tom de cinza
    public int[] obterHistogramaCinza(){
        int pixel, largura, altura;
        int cinza; 
        int histograma[] = null;

        BufferedImage img = getImgCopia();
        if(img != null){
            histograma = new int[256];
            for(int i = 0 ; i < 256 ; i++ ){ histograma[i] = 0; }
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    cinza = pixel & 0xff;
                    histograma[cinza] += 1;
                }
            }
        }
        return histograma;
    }

    //retorna uma matriz de inteiros com 256 indices contendo as quantidades de cada tom RGB
    public int[][] obterHistogramaColorido(){
        int pixel, largura, altura;
        int r, g, b; 
        int histograma[][] = null;

        BufferedImage img = getImgCopia();
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            histograma = new int[3][256];
            for(int i = 0 ; i < 3 ; i++){
                for(int j = 0 ; j < 256 ; j++ ){ 
                    histograma[i][j] = 0; 
                }
            }

            for (int y = 0 ; y < altura ; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    r = (pixel>>16) & 0xff;
                    g = (pixel>>8) & 0xff;
                    b = pixel & 0xff;

                    histograma[0][r] += 1;
                    histograma[1][g] += 1;
                    histograma[2][b] += 1;
                }
            }
        }
        return histograma;
    }
    
}
