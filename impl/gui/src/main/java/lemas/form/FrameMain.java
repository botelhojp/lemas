package lemas.form;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import lemas.model.Project;
import lemas.model.Workspace;
import lemas.util.Data;
import lemas.util.Message;

public class FrameMain extends JFrame {

	private static final long serialVersionUID = 630694363500290277L;

	JDesktopPane jdpDesktop;
	static int openFrameCount = 0;

	private int left;
	private int right;
	private int top;
	private int bottom;
	private int width;
	private int height;
	private Dimension screenSize;
	private JTextArea log = JConsole.getInstance().getLog();
	private static FrameMain instance = new FrameMain();

	private FrameMain() {
		JFrame frame = new JFrame("TSISMA - Trust Simulator for SMA");
		resize(frame);
		jdpDesktop = new JDesktopPane() {
			private static final long serialVersionUID = 1L;
			public Dimension getPreferredSize() {
				return screenSize;
			}
		};
		makeFrames();
		frame.setContentPane(jdpDesktop);
		frame.setJMenuBar(createMenuBar());
		// Make dragging faster by setting drag mode to Outline
		jdpDesktop.putClientProperty("JDesktopPane.dragMode", "outline");
		frame.pack();
		frame.setVisible(true);
	}

	private void resize(JFrame frame) {
		GraphicsConfiguration config = frame.getGraphicsConfiguration();
		left = Toolkit.getDefaultToolkit().getScreenInsets(config).left;
		right = Toolkit.getDefaultToolkit().getScreenInsets(config).right;
		top = Toolkit.getDefaultToolkit().getScreenInsets(config).top;
		bottom = Toolkit.getDefaultToolkit().getScreenInsets(config).bottom;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.width - left - right;
		height = screenSize.height - top - bottom;
		height = height - 50;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_N);

		JMenuItem menuProject = new JMenuItem("Novo Projeto");
		menuProject.setMnemonic(KeyEvent.VK_N);
		menuProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newProject();
			}
		});
		menu.add(menuProject);

		JMenuItem menuOpenProject = new JMenuItem("Abrir Projeto");
		menuOpenProject.setMnemonic(KeyEvent.VK_A);
		menuOpenProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openProject();
			}
		});
		menu.add(menuOpenProject);

		JMenuItem menuSaveAs = new JMenuItem("Salvar Como");
		menuSaveAs.setMnemonic(KeyEvent.VK_S);
		menuSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		menu.add(menuSaveAs);

		menu.add(new javax.swing.JPopupMenu.Separator());

		JMenuItem menuExit = new JMenuItem("Sair");
		menuExit.setMnemonic(KeyEvent.VK_R);
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(menuExit);

		menuBar.add(menu);
		return menuBar;
	}

	/**
	 * Reset do sistema
	 */
	public void clean() {
		DialogResult.getInstance().clean();
		displayDonw(FrameProject.getInstance(), DialogResult.getInstance(), 5);
	}

	/**
	 * Novo projeto
	 */
	public void newProject() {
		Message.message("New project...");
		FrameProject frame = FrameProject.getInstance();
		frame.load();
		frame.clean();
		frame.setVisible(true);
	}

	/**
	 * Abertura de Projeto
	 */
	public void openProject() {
		Message.message("Open project...");
		FrameProject frame = FrameProject.getInstance();
		frame.setTitle("Open project");
		FileNameExtensionFilter filterExt = new FileNameExtensionFilter("Project Lesma file (.lma)", "lma");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(filterExt);
		fileChooser.setCurrentDirectory(new File(Workspace.FOLDER_PROJECT));
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			Message.message("open file: " + file.getAbsolutePath());
			Project project = Data.fileToProject(file);
			project.setSaveIn(file.getAbsolutePath());
			frame.updateScreen(project);
			frame.setTitle("Open Project");
			visibleFrames(true);
		}
	}

	/**
	 * Salvar Como
	 */
	public void saveAs() {
		FrameProject fMain = FrameProject.getInstance();
		if (fMain.getCurrentProject() != null) {
			String ext = ".lma";
			fMain.updateProject();
			FileNameExtensionFilter filterExt = new FileNameExtensionFilter("Project Lesma file (.lma)", "lma");
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(filterExt);
			chooser.setCurrentDirectory(new File(Workspace.FOLDER_PROJECT));
			int retrival = chooser.showSaveDialog(this);
			if (retrival == JFileChooser.APPROVE_OPTION) {
				try {
					if (chooser.getSelectedFile().getName().endsWith(ext)) {
						Data.projectToFile(fMain.getCurrentProject(), chooser.getSelectedFile() + "");
					} else {
						Data.projectToFile(fMain.getCurrentProject(), chooser.getSelectedFile() + ext);
					}
				} catch (Exception ex) {
					Message.message("tentando salvar arquivo", ex);
				}
			}
			Message.message("Projeto Salvo");
		} else {
			JOptionPane.showMessageDialog(this, "Abra um projeto", "Nenhum projeto aberto", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Mensagens
	 * 
	 * @param message
	 */
	public void message(String message) {
		if (FrameProject.getInstance().getVerLog()) {
			log.append(message);
			log.append("\n");
			log.getCaret().setDot(log.getText().length());
		}
	}

	/**
	 * Layout dos frames
	 * 
	 */

	public static void visibleFrames(boolean value) {
		FrameProject.getInstance().setVisible(value);
		JConsole.getInstance().setVisible(value);
		DialogResult.getInstance().setVisible(value);
	}

	protected void makeFrames() {
		jdpDesktop.add(FrameProject.getInstance());
		try {
			FrameProject.getInstance().setSelected(false);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}

		jdpDesktop.add(JConsole.getInstance());
		try {
			JConsole.getInstance().setSelected(false);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}

		jdpDesktop.add(DialogResult.getInstance());
		try {
			DialogResult.getInstance().setSelected(false);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		displayDonw(FrameProject.getInstance(), DialogResult.getInstance(), 5);
		displayLeft(FrameProject.getInstance(), JConsole.getInstance(), 5);
	}

	private void displayDonw(JInternalFrame up, JInternalFrame dow, int delay) {
		dow.setLocation(up.getLocation().x, up.getHeight() + delay);
		dow.setSize(width, height - up.getHeight() - (delay * 3));
	}

	private void displayLeft(JInternalFrame r, JInternalFrame l, int i) {
		l.setLocation(r.getWidth(), r.getLocation().x);
		l.setSize(width - r.getWidth(), r.getHeight());

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FrameMain();
			}
		});
	}

	public static FrameMain getInstance() {
		return instance;
	}
}
