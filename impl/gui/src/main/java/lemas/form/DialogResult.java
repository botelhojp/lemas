package lemas.form;

import javax.swing.JDialog;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DialogResult extends JDialog {

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
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setSize(300, 200);
		xySerie = new XYSeriesCollection();
		JFreeChart chart = ChartFactory.createXYLineChart("", "rounds", "metric value", xySerie, PlotOrientation.VERTICAL, true, true, false);
		cp = new ChartPanel(chart);
		getContentPane().add(cp);
		cp.repaint();
		setSize(getSize().width + 1, getSize().height + 1);

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
}
