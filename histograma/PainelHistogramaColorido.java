package histograma;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class PainelHistogramaColorido extends JPanel{
    
    private int histogramaRgb[][] = null;
    
    //construtor
    public PainelHistogramaColorido(int[][] histograma){
        setHistogramaRgb(histograma);
    }
    
    //getters e setters
    public void setHistogramaRgb(int[][] histograma){ 
        this.histogramaRgb = histograma; 
    }
    
    public int[][] getHistogramaRgb(){ 
        return this.histogramaRgb; 
    }
    
    public void paint(Graphics g) {
        Color cores[] = {Color.RED, Color.GREEN, Color.YELLOW};
        //recebe o array contendo 2^8 = 256 frequencias
        int histograma[][] = getHistogramaRgb();
        
        //pontos que delimitam o plano cartesiano
        int xMin = 30;   int yMin = 30;
        int xMax = 286;  int yMax = 230;
        
        //frequencia percorre as frequencias
        int frequencia, maiorFrequencia;
        
        if(histograma != null){
            
            for(int i = 0 ; i < 3 ; i++){
                //determina a maior de todas as frequencias para criar a representacao grafica
                maiorFrequencia = 0;
                for(int j = 0 ; j < 256 ; j++){ 
                    if(histograma[i][j] > maiorFrequencia){ maiorFrequencia = histograma[i][j]; }
                }
                
                g.setColor(cores[i]);
                //desenha cada uma das retas que representam as frequencias
                for(int j = 0 ; j < 256 ; j++){ 
                    frequencia = (histograma[i][j]*(yMax-yMin))/maiorFrequencia;
                    g.drawLine(xMin+j, yMax-frequencia, xMin+j, yMax);
                }
                
                //desenha o plano cartesiano
                g.setColor(Color.BLACK);
                g.drawLine(xMin, yMin, xMin, yMax);
                g.drawLine(xMin, yMax, xMax, yMax);
                g.drawString("0", xMin-10, yMax+15);
                g.drawString("255", xMax-10, yMax+15);
                
                xMin += 300;
                xMax += 300;
            }
        }
    }
}
