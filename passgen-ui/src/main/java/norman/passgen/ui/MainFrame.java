package norman.passgen.ui;

import norman.passgen.core.Generator;
import norman.passgen.core.Generator.Optionality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static norman.passgen.core.Generator.Optionality.MANDATORY;
import static norman.passgen.core.Generator.Optionality.PROHIBITED;

public class MainFrame extends JFrame implements ActionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);
    private ResourceBundle bundle;
    private Properties appProps;
    private Container desktop;
    private JMenuItem exitFileItem;

    private JSpinner nbrOfPasswordsSpinner;
    private JSpinner lengthSpinner;
    private JCheckBox numberForFirstCheckBox;
    private JCheckBox lowerCaseCheckBox;
    private JCheckBox upperCaseCheckBox;
    private JCheckBox numericCheckBox;
    private JCheckBox specialCheckBox;
    private JButton generateButton;
    private JTextArea passwordsTextArea;
    private JButton clipboardButton;

    public MainFrame(Properties appProps) throws HeadlessException {
        super();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.appProps = appProps;

        initComponents();

        nbrOfPasswordsSpinner.setValue(Integer.parseInt(appProps.getProperty("nbr.of.passwords")));
        lengthSpinner.setValue(Integer.parseInt(appProps.getProperty("length")));
        numberForFirstCheckBox.setSelected(Boolean.parseBoolean(appProps.getProperty("number.for.first")));
        lowerCaseCheckBox.setSelected(Boolean.parseBoolean(appProps.getProperty("lower.case")));
        upperCaseCheckBox.setSelected(Boolean.parseBoolean(appProps.getProperty("upper.case")));
        numericCheckBox.setSelected(Boolean.parseBoolean(appProps.getProperty("numeric")));
        specialCheckBox.setSelected(Boolean.parseBoolean(appProps.getProperty("special")));

        pack();
        int x = Integer.parseInt(appProps.getProperty("main.frame.location.x"));
        int y = Integer.parseInt(appProps.getProperty("main.frame.location.y"));
        setLocation(x, y);
    }

    private void initComponents() {
        bundle = ResourceBundle.getBundle("norman.passgen.ui.MainFrame");
        setTitle(bundle.getString("title"));
        desktop = new JPanel(new GridBagLayout());
        setContentPane(desktop);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("nbr.of.passwords.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        nbrOfPasswordsSpinner = new JSpinner(new SpinnerNumberModel(88, 1, 99, 1));
        desktop.add(nbrOfPasswordsSpinner, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("length.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        lengthSpinner = new JSpinner(new SpinnerNumberModel(88, 1, 99, 1));
        desktop.add(lengthSpinner, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("number.for.first.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        numberForFirstCheckBox = new JCheckBox();
        desktop.add(numberForFirstCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("lower.case.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        lowerCaseCheckBox = new JCheckBox();
        desktop.add(lowerCaseCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("upper.case.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.LINE_START;
        upperCaseCheckBox = new JCheckBox();
        desktop.add(upperCaseCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("numeric.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        numericCheckBox = new JCheckBox();
        desktop.add(numericCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        desktop.add(new JLabel(bundle.getString("special.label")), c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 6;
        c.anchor = GridBagConstraints.LINE_START;
        specialCheckBox = new JCheckBox();
        desktop.add(specialCheckBox, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        generateButton = new JButton(bundle.getString("generate.button.label"));
        desktop.add(generateButton, c);
        generateButton.addActionListener(this);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 8;
        c.anchor = GridBagConstraints.LINE_START;
        passwordsTextArea = new JTextArea(3, 20);
        passwordsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(passwordsTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        desktop.add(scrollPane, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 8;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource("images/clipboard-regular.png");
            BufferedImage image = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(image);
            clipboardButton = new JButton(icon);
            clipboardButton.setToolTipText(bundle.getString("clipboard.button.tool.tip.text"));
        } catch (IOException e) {
            LOGGER.warn("Unable to create button icon", e);
            clipboardButton = new JButton(bundle.getString("clipboard.button.tool.tip.text"));
        }
        desktop.add(clipboardButton, c);
        clipboardButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(generateButton)) {
            generatePassword();
        } else if (actionEvent.getSource().equals(clipboardButton)) {
            copyPasswordsToClipboard();
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent windowEvent) {
        if (windowEvent.getSource() == this && windowEvent.getID() == WindowEvent.WINDOW_CLOSING) {

            // Get current size and position of UI and remember it.
            Point location = getLocation();
            appProps.setProperty("main.frame.location.x", Integer.toString(location.x));
            appProps.setProperty("main.frame.location.y", Integer.toString(location.y));

            appProps.setProperty("nbr.of.passwords", Integer.toString((Integer) nbrOfPasswordsSpinner.getValue()));
            appProps.setProperty("length", Integer.toString((Integer) lengthSpinner.getValue()));
            appProps.setProperty("number.for.first", Boolean.toString(numberForFirstCheckBox.isSelected()));
            appProps.setProperty("lower.case", Boolean.toString(lowerCaseCheckBox.isSelected()));
            appProps.setProperty("upper.case", Boolean.toString(upperCaseCheckBox.isSelected()));
            appProps.setProperty("numeric", Boolean.toString(numericCheckBox.isSelected()));
            appProps.setProperty("special", Boolean.toString(specialCheckBox.isSelected()));

            // Save the properties file to a local file.
            try {
                Application.storeProps(appProps);
            } catch (LoggingException e) {
                JOptionPane.showMessageDialog(this, bundle.getString("error.message.saving.window.size.and.location"),
                        bundle.getString("error.dialog.title"), JOptionPane.ERROR_MESSAGE);
            }
        }
        super.processWindowEvent(windowEvent);
    }

    private void generatePassword() {
        int nbrOfPasswords = (int) nbrOfPasswordsSpinner.getValue();
        int length = (int) lengthSpinner.getValue();
        boolean allowNumberForFirstChar = numberForFirstCheckBox.isSelected();
        Optionality includeLows = lowerCaseCheckBox.isSelected() ? MANDATORY : PROHIBITED;
        Optionality includeCaps = upperCaseCheckBox.isSelected() ? MANDATORY : PROHIBITED;
        Optionality includeNumbers = numericCheckBox.isSelected() ? MANDATORY : PROHIBITED;
        Optionality includeSpecials = specialCheckBox.isSelected() ? MANDATORY : PROHIBITED;
        if (includeLows == PROHIBITED && includeCaps == PROHIBITED && includeNumbers == PROHIBITED &&
                includeSpecials == PROHIBITED) {
            passwordsTextArea.setText(null);
            JOptionPane.showMessageDialog(this, bundle.getString("validate.message.generate"),
                    bundle.getString("error.dialog.title"), JOptionPane.ERROR_MESSAGE);
        } else {
            StringBuilder sb = null;
            for (int i = 0; i < nbrOfPasswords; i++) {

                String password = Generator
                        .generatePassword(length, allowNumberForFirstChar, includeLows, includeCaps, includeNumbers,
                                includeSpecials);
                if (sb == null) {
                    sb = new StringBuilder(password);
                } else {
                    sb.append(System.lineSeparator());
                    sb.append(password);
                }
            }
            passwordsTextArea.setText(sb.toString());
        }
    }

    private void copyPasswordsToClipboard() {
        StringSelection stringSelection = new StringSelection(passwordsTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
