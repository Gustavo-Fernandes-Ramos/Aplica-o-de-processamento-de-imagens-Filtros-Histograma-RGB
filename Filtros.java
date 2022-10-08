import java.awt.image.BufferedImage;
import java.awt.Color;

public class Filtros {
    
    //tira a media dos valores de rgb te todos os pixels da imagem
    public static BufferedImage filtroCinza(BufferedImage img){
        int pixel, avg, largura, altura;
        int a, r, g, b;        
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for(int y = 0; y < altura; y++){
                for(int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    
                    /* separa os valores de argb, andando com o bit mais significativo 
                    para direita e operador logico & com o valor 0xff = 255 */
                    a = (pixel>>24) & 0xff;   
                    r = (pixel>>16) & 0xff;   
                    g = (pixel>>8) & 0xff;   
                    b = pixel & 0xff;
                    avg = (r+g+b)/3;  //obtem a media dos valores de rgb
                    
                    //torna todos os valores de rgb do pixel iguais
                    pixel = (a<<24) | (avg<<16) | (avg<<8) | avg; 
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }
    
    //aplica a logica 255 - valorRgb,  para todos os valores de rgb
    public static BufferedImage filtroNegativo(BufferedImage img){
        int pixel, largura, altura;
        int a, r, g, b;   
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    
                    a = (pixel>>24) & 0xff;
                    r = (pixel>>16) & 0xff;
                    g = (pixel>>8) & 0xff;
                    b = pixel & 0xff;
                    
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    
                    pixel = (a<<24) | (r<<16) | (g<<8) | b;
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }
    
    //checa se os valores de rgb estao mais proximos de 0 ou 255
    public static BufferedImage filtroPretoBranco(BufferedImage img){
        int pixel, m, largura, altura;
        int a, r, g, b;  
        BufferedImage novaImg = null;        
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            novaImg = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);            
            for(int y = 0 ; y < altura ; y++){
                for(int x = 0 ; x < largura ; x++){
                    pixel = img.getRGB(x,y);
                    r = (pixel>>16) & 0xff;
                    g = (pixel>>8) & 0xff;
                    b = pixel & 0xff;
                    m=(r+g+b);
                    //(255+255+255)/2 =383 meio do caminho entre preto e branco
                    if(m>=383){
                        novaImg.setRGB(x, y, Color.WHITE.getRGB()); // pixel branco
                    }else{  
                        novaImg.setRGB(x, y, 0); // pixel preto
                    }
                }  
            }
        }
        return novaImg;
    }
    
    //zera os valores de green e blue
    public static BufferedImage filtroVermelho(BufferedImage img){
        int pixel, largura, altura;
        int a, r;   
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    a = (pixel>>24) & 0xff;
                    r = (pixel>>16) & 0xff;
                    pixel = (a<<24) | (r<<16) | (0<<8) | 0;
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }
    
    //zera os valores de red e blue
    public static BufferedImage filtroVerde(BufferedImage img){
        int pixel, largura, altura;
        int a, g;   
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    a = (pixel>>24) & 0xff;
                    g = (pixel>>8) & 0xff;
                    pixel = (a<<24) | (0<<16) | (g<<8) | 0;
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }
    
    //zera os valores de green e red
    public static BufferedImage filtroAzul(BufferedImage img){
        int pixel, largura, altura;
        int a, b;   
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    a = (pixel>>24) & 0xff;
                    b = pixel & 0xff;
                    pixel = (a<<24) | (0<<16) | (0<<8) | b;
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }
    
    //aplica a matriz passa-baixa para suavizar a imagem
    public static BufferedImage filtroPassaBaixa(BufferedImage img){
        int largura, altura, a, r, g, b;
        int pixel, novoPixel, elemFiltro, xp, yp;
        
        double novoA, novoR, novoG, novoB; 
        int somaA, somaR, somaG, somaB;
        
        int filtro[][] = {{ 1, 1, 1},
                          { 1, 2, 1},
                          { 1, 1, 1}};                   
                          
        BufferedImage novaImg = null;      
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            
            novaImg = new BufferedImage(largura-2, altura-2, BufferedImage.TYPE_INT_ARGB);
            for(int y = 1 ; y < altura-1 ; y++){
                for(int x = 1 ; x < largura-1 ; x++){
                    somaA = 0;   somaR = 0;   somaG = 0;   somaB = 0;
                    
                    for(int i = 0 ; i < 3 ; i++){
                        for(int j = 0 ; j < 3 ; j++){
                            elemFiltro = filtro[i][j];
                            xp = x-(j-1);   yp = y-(i-1); 
                            
                            pixel = img.getRGB(xp,yp);
                            
                            a = (pixel>>24) & 0xff;
                            somaA = somaA + (a * elemFiltro);
                            r = (pixel>>16) & 0xff;
                            somaR = somaR + (r * elemFiltro);
                            g = (pixel>>8) & 0xff;
                            somaG = somaG + (g * elemFiltro);
                            b = pixel & 0xff;
                            somaB = somaB + (b * elemFiltro);
                            
                        }
                    }
                    novoA = somaA / 10;  novoR = somaR / 10;  novoG = somaG / 10;  novoB = somaB / 10;
                    
                    novoPixel = ((int)novoA<<24) | ((int)novoR<<16) | ((int)novoG<<8) | (int)novoB; 
                    novaImg.setRGB(x-1, y-1, novoPixel);
                }  
            }
        }
        return novaImg;
    }
    
    //aplica a matriz passa-alta (Laplace) para realce de bordas
    public static BufferedImage filtroPassaAlta(BufferedImage img){
        int largura, altura;
        int a, r, g, b, pixel, novoPixel, elemFiltro, xp, yp;
        int somaA, somaR, somaG, somaB;
        
        int filtro[][] = {{ 0, -1, 0},
                          { -1, 4, -1},
                          { 0, -1, 0}};
                          
        BufferedImage novaImg = null;      
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            
            novaImg = new BufferedImage(largura-2, altura-2, BufferedImage.TYPE_INT_ARGB);
            for(int y = 1 ; y < altura-1 ; y++){
                for(int x = 1 ; x < largura-1 ; x++){
                    somaA = 0;   somaR = 0;   somaG = 0;   somaB = 0;    a = 0;
                    for(int i = 0 ; i < 3 ; i++){
                        for(int j = 0 ; j < 3 ; j++){
                            elemFiltro = filtro[i][j];
                            xp = x-(j-1);   yp = y-(i-1); 
                            
                            pixel = img.getRGB(xp,yp);
                            
                            a = (pixel>>24) & 0xff;
                            //somaA = somaA + (a * elemFiltro);
                            r = (pixel>>16) & 0xff;
                            somaR = somaR + (r * elemFiltro);
                            g = (pixel>>8) & 0xff;
                            somaG = somaG + (g * elemFiltro);
                            b = pixel & 0xff;
                            somaB = somaB + (b * elemFiltro);
                        }
                    }
                    novoPixel = (a<<24) | (somaR<<16) | (somaG<<8) | somaB; 
                    novaImg.setRGB(x-1, y-1, novoPixel);
                }  
            }
        }
        return novaImg;
    }
    
    
}
