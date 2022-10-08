package histograma;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JDialog;

public class HistogramaGUI extends JDialog{
    
    private PainelHistogramaCinza painelCinza = null;
    private PainelHistogramaColorido painelColorido = null;
    
    //cria um histograma de tons de cinza
    public HistogramaGUI(int[] histograma){
        this.setSize(340, 300);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        painelCinza = new PainelHistogramaCinza(histograma);
        
        this.add(painelCinza, BorderLayout.CENTER);
        
        setVisible(true);
        
        painelCinza.repaint();
    }

    //cria um tres histogramas, um para cada cor rgb
    public HistogramaGUI(int[][] histogramaRgb){
        this.setSize(930, 300);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        painelColorido = new PainelHistogramaColorido(histogramaRgb);
        painelColorido.setBackground(Color.BLACK);
        
        this.add(painelColorido, BorderLayout.CENTER);
        
        setVisible(true);
        
        painelColorido.repaint();
    }
    
}
