/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mt.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 *
 * @author ale
 */
public class Dialog extends JOptionPane {

	public Dialog(Object message, int messageType, int optionType) {
		super(message, messageType, optionType);
	}

	public static String getString(Frame parent, Object message) throws HeadlessException {
		Dialog pane = new Dialog(message, PLAIN_MESSAGE, OK_CANCEL_OPTION);
		pane.setWantsInput(true);
		pane.setComponentOrientation(((parent == null)
				? getRootFrame() : parent).getComponentOrientation());

		JDialog dialog = pane.createDialog(parent, "", JRootPane.PLAIN_DIALOG);
		dialog.setVisible(true);
		dialog.dispose();

		Object value = pane.getInputValue();
		if (value == UNINITIALIZED_VALUE) {
			return null;
		}
		return (String) value;
	}

	public static boolean getConfirmation(Frame parent, Object message) throws HeadlessException {
		Dialog pane = new Dialog(message, PLAIN_MESSAGE, YES_NO_OPTION);
		pane.setComponentOrientation(((parent == null)
				? getRootFrame() : parent).getComponentOrientation());

		JDialog dialog = pane.createDialog(parent, "", JRootPane.PLAIN_DIALOG);
		dialog.setVisible(true);
		dialog.dispose();

		Object selectedValue = pane.getValue();
		if (selectedValue != null
				&& selectedValue instanceof Integer
				&& (Integer) selectedValue == YES_OPTION) {
			return true;
		}
		return false;
	}

	private JDialog createDialog(Frame parent, String title, int style)
			throws HeadlessException {

		final JDialog dialog;
		dialog = new JDialog(parent, title, true);
		initDialog(dialog, style, parent);
		return dialog;
	}

	private void initDialog(final JDialog dialog, int style, Frame parent) {
		dialog.setComponentOrientation(this.getComponentOrientation());
		Container contentPane = dialog.getContentPane();

		contentPane.setLayout(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);
		dialog.setResizable(false);
//		if (JDialog.isDefaultLookAndFeelDecorated()) {
//			boolean supportsWindowDecorations =
//					UIManager.getLookAndFeel().getSupportsWindowDecorations();
//			if (supportsWindowDecorations) {
		dialog.setUndecorated(true);
//				getRootPane().setWindowDecorationStyle(style);
//			}
//		}
		dialog.pack();
		dialog.setLocationRelativeTo(parent);

		final PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				// Let the defaultCloseOperation handle the closing
				// if the user closed the window without selecting a button
				// (newValue = null in that case).  Otherwise, close the dialog.
				if (dialog.isVisible() && event.getSource() == Dialog.this
						&& (event.getPropertyName().equals(VALUE_PROPERTY))
						&& event.getNewValue() != null
						&& event.getNewValue() != JOptionPane.UNINITIALIZED_VALUE) {
					dialog.setVisible(false);
				}
			}
		};

		WindowAdapter adapter = new WindowAdapter() {

			private boolean gotFocus = false;

			public void windowClosing(WindowEvent we) {
				setValue(null);
			}

			public void windowClosed(WindowEvent e) {
				removePropertyChangeListener(listener);
				dialog.getContentPane().removeAll();
			}

			public void windowGainedFocus(WindowEvent we) {
				// Once window gets focus, set initial focus
				if (!gotFocus) {
					selectInitialValue();
					gotFocus = true;
				}
			}
		};
		dialog.addWindowListener(adapter);
		dialog.addWindowFocusListener(adapter);
		dialog.addComponentListener(new ComponentAdapter() {

			public void componentShown(ComponentEvent ce) {
				// reset value to ensure closing works properly
				setValue(JOptionPane.UNINITIALIZED_VALUE);
			}
		});

		addPropertyChangeListener(listener);
	}
}
