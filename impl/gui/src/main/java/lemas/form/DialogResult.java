package lemas.form;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DialogResult extends JDialog{

	private static final long serialVersionUID = 1L;
	private ChartPanel cp = null;
	private XYSeriesCollection xySerie;

	public DialogResult() {
		setVisible(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
		setSize(300, 200);
		xySerie = createXYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("Test Chart", "x", "y", xySerie, PlotOrientation.VERTICAL, true, true, false);
		cp = new ChartPanel(chart);
		JInternalFrame wd = FrameMain.getInstance().getWindow();
		wd.setSize(300, 200);
		wd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wd.setVisible(true);
		wd.add(cp);
		getContentPane().add(cp);
		cp.repaint();
		wd.setSize(getSize().width + 1, getSize().height + 1);
		setSize(getSize().width + 1, getSize().height + 1);
		
		pack();
		
	}

	private XYSeriesCollection createXYSeriesCollection() {
		XYSeriesCollection series = new XYSeriesCollection();
		Collection<XYSeries> collection = new ArrayList<XYSeries>();
		collection.add(new XYSeries("Acerto"));
		collection.add(new XYSeries("Erro"));
		for (XYSeries serie : collection) {
			series.addSeries(serie);
		}
		return series;
	}

	public void addResult(int serie, double iteration, double value) {
		xySerie.getSeries(serie).add(iteration, value);
	}

}
