package lemas.agent;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

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
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

public class AgentLoader extends OpenAgent {

	private static final long serialVersionUID = 1L;
	private Set<AID> wait = new HashSet<AID>();
	private double countTrue = 0;
	private double countFalse = 0;
	private DefaultXYDataset ds = null;
	private JFrame frame = null;
	private ChartPanel cp = null;

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
		//
		// frame.setSize(600, 400);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setVisible(true);

		if (ds == null) {
			//JInternalFrame frame = FrameMain.getInstance().getFrameResult();
			frame = new JFrame("Charts");
			frame.setSize(600, 400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			ds = createDataset();
			JFreeChart chart = ChartFactory.createXYLineChart("Test Chart", "x", "y", ds, PlotOrientation.VERTICAL, true, true, false);
			cp = new ChartPanel(chart);
			frame.getContentPane().add(cp);
			cp.repaint();
		}else{
			cp.repaint();
		}

		updateScree();
	}

	private static DefaultXYDataset createDataset() {

		DefaultXYDataset ds = new DefaultXYDataset();

		double[][] data = { { 0.1, 0.2, 0.3 }, { 1, 2, 3 } };

		ds.addSeries("series1", data);

		return ds;
	}

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
