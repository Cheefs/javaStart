import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.text.*;

public class DayOfTheWeekService implements Service {
    JLabel outputLabel;
    JComboBox<String> month;
    JTextField day;
    JTextField year;

    public JPanel getGuiPanel() {
        setUpDateStuff();
        JButton button = new JButton("Do it!");
        button.addActionListener(this::doItListener);
        outputLabel = new JLabel("date appears here");
        JComponent[] items = { setUpInputPanel(), button, outputLabel };

        return setUpPanel( items );
    }

    private JPanel setUpPanel(JComponent[] items) {
        JPanel panel = new JPanel();
        for( JComponent item: items ) {
            panel.add( item );
        }
        return panel;
    }

    private void setUpDateStuff() {
        month = new JComboBox<>(new DateFormatSymbols().getMonths());
        day = new JTextField(8);
        year = new JTextField(8);
    }

    private JPanel setUpInputPanel() {
        JPanel inputPanel = new JPanel( new GridLayout(3,2));
        inputPanel.add(new JLabel("Month"));
        inputPanel.add(month);

        inputPanel.add(new JLabel("Day"));
        inputPanel.add(day);

        inputPanel.add(new JLabel("Year"));
        inputPanel.add(year);
        return inputPanel;
    }

    private void doItListener(ActionEvent event) {
        int montNum = month.getSelectedIndex();
        int dayNum = Integer.parseInt(day.getText());
        int yearNum = Integer.parseInt(year.getText());

        Calendar c = Calendar.getInstance();
        c.set( Calendar.MONTH, montNum );
        c.set( Calendar.DAY_OF_MONTH, dayNum );
        c.set( Calendar.YEAR, yearNum );

        Date date = c.getTime();
        String dayOfWeek = (new SimpleDateFormat( "EEEE")).format(date);
        outputLabel.setText( dayOfWeek );
    }
}
