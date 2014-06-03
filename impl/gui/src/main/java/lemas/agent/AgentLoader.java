package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import lemas.agent.behaviour.LoadeBehaviour;
import lemas.form.FrameMain;
import lemas.model.LemasLog;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;
import openjade.core.annotation.ReceiveSimpleMessage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class AgentLoader extends OpenAgent {

	private static final long serialVersionUID = 1L;
	private Set<AID> wait = new HashSet<AID>();
	private double countTrue = 0;
	private double countFalse = 0;
	private JFrame frame = null;
	private ChartPanel cp = null;
	private XYSeriesCollection xyDataset;
	private long count = 0;

	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new LoadeBehaviour(this));
	}

	@ReceiveSimpleMessage(conversationId = ConversationId.LOADER)
	public void getMessage(ACLMessage msg) {
		if (wait.contains(msg.getSender())) {
			wait.remove(msg.getSender());
		} else {
			throw new OpenJadeException("Agente não autorizado [" + msg.getSender().getLocalName() + "]");
		}

	}

	@ReceiveSimpleMessage(conversationId = ConversationId.TEST)
	public void getTestMessage(ACLMessage msg) {
		String[] tokens = msg.getContent().split(":");
		String r1 = tokens[0];
		String r2 = tokens[1];
		if (r1.equals(r2)) {
			countTrue = countTrue + 1;
		} else {
			countFalse = countFalse + 1;
		}
		// JFrame frame = new JFrame("Charts");
		// frame.setSize(600, 400);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setVisible(true);

		if (cp == null) {
			// JInternalFrame frame = FrameMain.getInstance().getFrameResult();
			frame = new JFrame("Charts");
			frame.setSize(600, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			// ds = createDataset();
			xyDataset = getAllSeries();
			JFreeChart chart = ChartFactory.createXYLineChart("Test Chart", "x", "y", xyDataset, PlotOrientation.VERTICAL, true, true, false);
			cp = new ChartPanel(chart);
			JInternalFrame wd = FrameMain.getInstance().getWindow();
			wd.setSize(600, 400);
			wd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wd.setVisible(true);
			wd.add(cp);
			
			frame.getContentPane().add(cp);
			cp.repaint();
			wd.setSize(frame.getSize().width + 1, frame.getSize().height + 1);
			frame.setSize(frame.getSize().width + 1, frame.getSize().height + 1);
			
		} else {

			if (count < 200) {
				count++;
				xyDataset.getSeries(0).add(count, Math.pow(2, count) * 10);
				xyDataset.getSeries(1).add(count, Math.pow(2, count) * 5);
//				cp.repaint();
//				frame.repaint();
				
			}
		}

		updateScree();
	}

	protected XYSeriesCollection getAllSeries() {
		XYSeriesCollection col = new XYSeriesCollection();
		Collection<XYSeries> values = createSeries();
		for (XYSeries series : values) {
			col.addSeries(series);
		}
		return col;
	}

	private Collection<XYSeries> createSeries() {
		Collection<XYSeries> series = new ArrayList<XYSeries>();
		series.add(new XYSeries("Erro"));
		series.add(new XYSeries("Acerto"));
		return series;
	}

	// private static DefaultXYDataset createDataset() {
	// DefaultXYDataset ds = new DefaultXYDataset();
	// double[][] data = { { 0.1, 0.2, 0.3 }, { 1, 2, 3 } };
	// ds.addSeries("series1", data);
	// return ds;
	// }

	private void updateScree() {
		double total = (countFalse + countTrue);
		LemasLog.info("total test:" + total + " ok " + countTrue + "  nok " + countFalse + " % " + (countTrue / (countFalse + countTrue)));
	}

	public void waiting(AID aid) {
		wait.add(aid);
	}

	public boolean nowait() {
		return wait.isEmpty();
	}

}
