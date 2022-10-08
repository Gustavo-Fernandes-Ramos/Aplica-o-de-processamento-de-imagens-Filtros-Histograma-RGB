package histograma;
import javax.swing.JPanel;
import java.awt.Graphics;

public class PainelHistogramaCinza extends JPanel{
    
    private int histogramaAtual[] = null;
    
    //construtor
    public PainelHistogramaCinza(int[] histograma){
        setHistogramaAtual(histograma);
    }
    
    //getters e setters
    public void setHistogramaAtual(int[] histograma){
        this.histogramaAtual = histograma;
    }
    
    public int[] getHistogramaAtual(){
        return this.histogramaAtual;
    }

    //metodos
    public void paint(Graphics g) {
        //recebe o array contendo 2^8 = 256 frequencias
        int histograma[] = getHistogramaAtual();
        
        //pontos que delimitam o plano cartesiano
        int xMin = 30;   int yMin = 30;
        int xMax = 286;  int yMax = 230;
        
        //frequencia percorre as frequencias
        int frequencia, maiorFrequencia = 0;
        
        if(histograma != null){
            
            //determina a maior de todas as frequencias para criar a representacao grafica
            for(int i = 0 ; i < 256 ; i++){ 
                if(histograma[i] > maiorFrequencia){ maiorFrequencia = histograma[i]; }
            }
            //desenha o plano cartesiano
            g.drawLine(xMin, yMin, xMin, yMax);
            g.drawLine(xMin, yMax, xMax, yMax);
            g.drawString("0", xMin-10, yMax+15);
            g.drawString("255", xMax-10, yMax+15);
            //desenha cada uma das retas que representam as frequencias
            for(int i = 0 ; i < 256 ; i++){ 
                frequencia = (histograma[i]*(yMax-yMin))/maiorFrequencia;
                g.drawLine(xMin+i, yMax-frequencia, xMin+i, yMax);
            }
            
        }
    }
}
