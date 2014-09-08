package lemas.form;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DialogResult extends JDialog {

	private static final long serialVersionUID = 1L;
	private ChartPanel cp = null;
	private XYSeriesCollection xySerie;

	public DialogResult() {
		setVisible(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setSize(300, 200);
		xySerie = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("Result", "round", "hits", xySerie, PlotOrientation.VERTICAL, true, true, false);
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

	public void addResult(int serie, double iteration, double value) {
		if (serie >= xySerie.getSeriesCount()) {
			XYSeries newSerie = new XYSeries("rs " + (serie + 1));
			xySerie.addSeries(newSerie);
		}
		xySerie.getSeries(serie).add(iteration, value);			
	}
}
