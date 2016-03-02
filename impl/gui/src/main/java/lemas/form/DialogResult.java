package lemas.form;

import javax.swing.JInternalFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DialogResult extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static DialogResult instance = new DialogResult();

	public static DialogResult getInstance() {
		return instance;
	}

	private ChartPanel cp = null;
	private XYSeriesCollection xySerie;
	private boolean enabled = true;

	public DialogResult() {
		setVisible(false);
		setResizable(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		xySerie = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("", "rounds", "metric value", xySerie, PlotOrientation.VERTICAL, true, true, false);
		cp = new ChartPanel(chart);
		getContentPane().add(cp);
		cp.repaint();
		pack();

	}

	/**
	 * Adicionar valor o grafico se a serie 0 estiver habilidade
	 * @param serie
	 * @param iteration
	 * @param value
	 */
	public void addResult(int serie, double iteration, double value) {
		if (enabled) {
			addResultForce(serie, iteration, value);
		}
	}

	public void addResultForce(int serie, double iteration, double value) {
		if (serie >= xySerie.getSeriesCount()) {
			XYSeries newSerie = new XYSeries(FrameProject.getInstance().getCurrentProject().getTrustmodel());
			xySerie.addSeries(newSerie);
		}
		xySerie.getSeries(serie).add(iteration, value);
	}

	public void disabledAddResult() {
		enabled = false;
	}

	public XYSeriesCollection getXSerie() {
		return xySerie;
	}

	public void clean() {
		getContentPane().removeAll();
		xySerie = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("", "rounds", "metric value", xySerie, PlotOrientation.VERTICAL, true, true, false);
		cp = new ChartPanel(chart);
		getContentPane().add(cp);
		cp.repaint();
		pack();
	}
}
