import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.sound.midi.*;
import java.util.*;
import java.net.*;
import javax.swing.event.*;

public class BeatBoxFinal {
    JFrame theFrame;
    JPanel mainPanel;
    JList<String> incomingList;
    JTextField userMessage;
    ArrayList<JCheckBox> checkboxList;
    int nextNum;
    Vector<String> listVector = new Vector<>();
    String userName;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap = new HashMap<>();

    Sequencer sequencer;
    Sequence sequence;
    Sequence mySequence;
    Track track;

    String[] instrumentNames = {"Bass Drum", "Closed Hit-Hat", "Open Hit-Hat", "Acoustic Snare",
        "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
        "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };
    int[] instruments = { 35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63 };

    public static void main(String[] args) {
        new BeatBoxFinal().startUp( args[0] );
    }

    public void startUp(String name) {
        userName = name;

        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            Thread remote = new Thread(this::remoteReader);
            remote.start();
        } catch ( Exception ex ) {
            System.out.println("couldn`t connect - you`ll have to play alone.");
        }
        setUpMidi();
        buildGui();
    }
    public void buildGui() {
        theFrame = new JFrame("Cyber BeatBox");
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkboxList = new ArrayList<>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton tempoUp = new JButton("Tempo Up");
        JButton tempoDown = new JButton("Tempo Down");
        JButton sendIt = new JButton("Send It");

        start.addActionListener( this::startButtonListener);
        stop.addActionListener( this::stopButtonListener );
        tempoUp.addActionListener( this::tempoUpButtonListener );
        tempoDown.addActionListener( this::tempoDownButtonListener );
        sendIt.addActionListener( this::sendItButtonListener );

        buttonBox.add(start);
        buttonBox.add(stop);
        buttonBox.add(tempoUp);
        buttonBox.add(tempoDown);
        buttonBox.add(sendIt);

        userMessage = new JTextField();
        buttonBox.add(userMessage);

        incomingList = new JList<>();
        incomingList.addListSelectionListener(this::listSelectionListener);
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector);

        Box nameBox = new Box(BoxLayout.Y_AXIS);

        for (String name: instrumentNames ) {
            nameBox.add( new Label( name ) );
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add( background );
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);

        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++ ) {
            JCheckBox c = new JCheckBox();
            c.setSelected( false );
            checkboxList.add(c);
            mainPanel.add(c);
        }
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    public void buildTrackAndStart() {
        ArrayList<Integer> trackList;

        int length = instruments.length;
        for (int i = 0; i < length; i++ ) {
            trackList = new ArrayList<>();
            for ( int j = 0; j < length; j++ ) {
                JCheckBox jc = checkboxList.get( j + (length * i ) );
                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add( key );
                } else {
                    trackList.add( null );
                }
            }
            makeTracks( trackList );
        }
        track.add(makeEvent(192,9,1,0,15));

        try {
            sequencer.setSequence( sequence );
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void changeSequence(boolean[] checkboxState) {
        for ( int i = 0; i < 256; i++ ) {
            JCheckBox check = checkboxList.get(i);
            check.setSelected( checkboxState[i] );
        }
    }

    public void makeTracks(ArrayList<Integer> list) {
        Iterator<Integer> it = list.iterator();
        for( int i = 0; i < instruments.length; i++ ) {
            Integer num = it.next();
            if ( num != null ) {
//                int numKey = num.intValue();
                track.add(makeEvent(144, 9, num, 100, i));
                track.add(makeEvent(128, 9, num, 100, i + 1 ));
            }
        }
    }

    public MidiEvent makeEvent(int cmd, int ch, int one, int two, int tick ) {
        MidiEvent event = null;
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(cmd, ch, one, two);
            event = new MidiEvent(msg, tick);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return event;
    }

    private void startButtonListener(ActionEvent event ) {
        buildTrackAndStart();
    }

    private void stopButtonListener(ActionEvent event ) {
        sequencer.stop();
    }

    private void tempoUpButtonListener(ActionEvent event ) {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor( tempoFactor * 1.03f );
    }

    private void tempoDownButtonListener(ActionEvent event ) {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor( tempoFactor * .03f );
    }

    private void sendItButtonListener( ActionEvent event ) {
        boolean[] checkBoxState = new boolean[256];

        for ( int i = 0; i < 256; i++ ) {
            checkBoxState[i] = checkboxList.get(i).isSelected();
        }
        try {
            String messageToSend = userName + nextNum++ + ": " + userMessage.getText();
            out.writeObject( messageToSend );
            out.writeObject(checkBoxState);
        } catch (Exception ex) {
            System.out.println("sorry can`t send!");
        }
        userMessage.setText("");
    }

    private void listSelectionListener( ListSelectionEvent event ) {
        if (!event.getValueIsAdjusting()) {
            String selected = incomingList.getSelectedValue();

            if (selected != null) {
                boolean[] selectedState = otherSeqsMap.get(selected);
                changeSequence( selectedState );
                sequencer.stop();
                buildTrackAndStart();
            }
        }
    }

    private void playMintListener( ListSelectionEvent event ) {
        if ( mySequence != null ) {
            sequence = mySequence;
        }
    }

    private void remoteReader() {
        Object obj;
        try {
            while (( obj = in.readObject()) != null) {
                System.out.println("got an object from server");
                System.out.println(obj.getClass());
                String nameToShow = (String) obj;

                boolean[] checkboxState = (boolean[]) in.readObject();
                otherSeqsMap.put( nameToShow, checkboxState );
                listVector.add(nameToShow);
                incomingList.setListData(listVector);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
